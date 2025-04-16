package automation;

import automation.Automation.*;
import automation.AutomationServiceGrpc;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the Automation gRPC service.
 * Handles smart device operations: toggle, scheduling, streaming status,
 * receiving commands and real-time messaging.
 * Now includes proper error handling and cancellation support.
 *
 * @author dcmed
 */
public class AutomationServiceImpl extends AutomationServiceGrpc.AutomationServiceImplBase {

    // Simulated device state storage
    private final Map<String, Boolean> devices = new HashMap<>();
    private final Map<String, Integer> blindsPosition = new HashMap<>();
    private final Map<String, Integer> airConditionerTemperature = new HashMap<>();

    // Unary Method - Turn a device ON or OFF - with error handling
    @Override
    public void toggleDevice(ToggleDeviceRequest request, StreamObserver<ToggleDeviceResponse> responseObserver) {
        if (request.getDeviceId() == null || request.getDeviceId().isEmpty()) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Device ID cannot be empty.")
                    .asRuntimeException());
            return;
        }

        try {
            boolean success = controlDevice(request.getDeviceId(), request.getTurnOn());

            ToggleDeviceResponse response = ToggleDeviceResponse.newBuilder()
                    .setSuccess(success)
                    .setMessage(success ? (request.getTurnOn() ? "Device turned ON" : "Device turned OFF") : "Failed")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Unexpected error: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    // Unary Method - Schedule a device to turn ON or OFF at a given time
    @Override
    public void setSchedule(SetScheduleRequest request, StreamObserver<SetScheduleResponse> responseObserver) {
        if (request.getDeviceId().isEmpty() || request.getScheduleTime().isEmpty()) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Device ID and schedule time are required.")
                    .asRuntimeException());
            return;
        }

        String deviceId = request.getDeviceId();
        String time = request.getScheduleTime();
        boolean turnOn = request.getTurnOn();

        String action = turnOn ? "turn ON" : "turn OFF";
        String msg = "Routine scheduled: Set " + deviceId + " to " + action + " at " + time;

        System.out.println("[SCHEDULE] " + msg);

        SetScheduleResponse response = SetScheduleResponse.newBuilder()
                .setSuccess(true)
                .setMessage(msg)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Server Streaming Method - Send a series of status updates
    @Override
    public void streamDeviceStatus(StreamDeviceStatusRequest request, StreamObserver<DeviceStatusResponse> responseObserver) {
        try {
            for (int i = 0; i < 5; i++) {
                // Verifica se o cliente cancelou a requisição
                if (Context.current().isCancelled()) {
                    System.out.println("[CANCELLED] streamDeviceStatus was cancelled by client.");
                    return;
                }

                DeviceStatusResponse response = DeviceStatusResponse.newBuilder()
                        .setStatus("ON")
                        .setTimestamp(Instant.now().toString())
                        .build();

                responseObserver.onNext(response);
                Thread.sleep(1000); // 1 segundo
            }
        } catch (InterruptedException e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Streaming interrupted.")
                    .withCause(e)
                    .asRuntimeException());
        } finally {
            responseObserver.onCompleted();
        }
    }

    // Client Streaming Method - Accept multiple device commands from client
    @Override
    public StreamObserver<DeviceCommand> sendDeviceCommands(final StreamObserver<CommandSummaryResponse> responseObserver) {
        return new StreamObserver<DeviceCommand>() {
            int count = 0;

            @Override
            public void onNext(DeviceCommand command) {
                if (Context.current().isCancelled()) {
                    System.out.println("[CANCELLED] sendDeviceCommands was cancelled by client.");
                    return;
                }

                count++;
                System.out.println("[COMMAND] " + command.getDeviceId() + " -> " + command.getCommand());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("[ERROR] Command processing failed: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                CommandSummaryResponse response = CommandSummaryResponse.newBuilder()
                        .setSuccess(true)
                        .setCommandsReceived(count)
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    // Bidirectional Streaming Method - Interactive communication with devices
    @Override
    public StreamObserver<DeviceMessage> communicateWithDevice(final StreamObserver<DeviceMessage> responseObserver) {
        return new StreamObserver<DeviceMessage>() {
            @Override
            public void onNext(DeviceMessage message) {
                if (Context.current().isCancelled()) {
                    System.out.println("[CANCELLED] communicateWithDevice was cancelled by client.");
                    return;
                }

                String replyMessage;

                try {
                    String msg = message.getMessage().toLowerCase();

                    switch (msg) {
                        case "status?":
                            replyMessage = "Current status: ON";
                            break;
                        case "temperature?":
                            replyMessage = "Current temperature: 21.5 Degree Celsius";
                            break;
                        case "position?":
                        case "position ?":
                            int position = blindsPosition.getOrDefault(message.getDeviceId(), 50);
                            replyMessage = "Blinds are " + position + "% closed";
                            break;
                        case "restart":
                            replyMessage = "Restarting device...";
                            break;
                        default:
                            // Tratamento de comandos setados (ex: set position 40)
                            if (msg.startsWith("set position")) {
                                int newPosition = Integer.parseInt(msg.split(" ")[2]);
                                blindsPosition.put(message.getDeviceId(), newPosition);
                                replyMessage = "Blinds position set to " + newPosition + "%";
                            } else if (msg.startsWith("set temperature")) {
                                int newTemp = Integer.parseInt(msg.split(" ")[2]);
                                if (newTemp < 16 || newTemp > 30) {
                                    replyMessage = "Temperature out of range (16–30°C)";
                                } else {
                                    airConditionerTemperature.put(message.getDeviceId(), newTemp);
                                    replyMessage = "Air conditioner temperature set to " + newTemp + "°C";
                                }
                            } else {
                                replyMessage = "Command '" + message.getMessage() + "' received";
                            }
                            break;
                    }

                    DeviceMessage reply = DeviceMessage.newBuilder()
                            .setDeviceId(message.getDeviceId())
                            .setMessage(" " + replyMessage)
                            .build();

                    System.out.println("[MESSAGE] Device " + message.getDeviceId() + " replied: " + replyMessage);
                    responseObserver.onNext(reply);

                } catch (Exception e) {
                    responseObserver.onError(Status.INVALID_ARGUMENT
                            .withDescription("Invalid command or parameters: " + e.getMessage())
                            .withCause(e)
                            .asRuntimeException());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("[ERROR] Bidirectional communication failed: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * Internal logic to simulate turning devices ON/OFF.
     * Prints the simulated result based on device type.
     */
    private boolean controlDevice(String deviceId, boolean turnOn) {
        devices.put(deviceId, turnOn);

        String action = turnOn ? "ON" : "OFF";
        String type;

        if (deviceId.toLowerCase().contains("air")) {
            type = "Air Conditioner";
        } else if (deviceId.toLowerCase().contains("blinds")) {
            type = "Blinds";
            action = turnOn ? "opened" : "closed";
        } else if (deviceId.toLowerCase().contains("light")) {
            type = "Light";
        } else {
            type = "Device";
        }

        System.out.println("[ACTION] " + type + " '" + deviceId + "' has been " + action);
        return true;
    }
}