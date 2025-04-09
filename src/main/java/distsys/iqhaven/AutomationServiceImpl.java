/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.iqhaven;

import automation.Automation.*;
import automation.AutomationServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the Automation gRPC service.
 * Handles smart device operations: toggle, scheduling, streaming status,
 * receiving commands and real-time messaging.
 * @author dcmed 
 */
public class AutomationServiceImpl extends AutomationServiceGrpc.AutomationServiceImplBase {

    // Simulated device state storage
    private final Map<String, Boolean> devices = new HashMap<>();
    private final Map<String, Integer> blindsPosition = new HashMap<>();
    private final Map<String, Integer> airConditionerTemperature = new HashMap<>();

    // Unary Method - Turn a device ON or OFF
    @Override
    public void toggleDevice(ToggleDeviceRequest request, StreamObserver<ToggleDeviceResponse> responseObserver) {
        boolean success = controlDevice(request.getDeviceId(), request.getTurnOn());

        ToggleDeviceResponse response = ToggleDeviceResponse.newBuilder()
            .setSuccess(success)
            .setMessage(success ? "Device turned ON" : "Failed to control device")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Unary Method - Schedule a device to turn ON or OFF at a given time
    @Override
    public void setSchedule(SetScheduleRequest request, StreamObserver<SetScheduleResponse> responseObserver) {
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
        DeviceStatusResponse response = DeviceStatusResponse.newBuilder()
            .setStatus("ON")
            .setTimestamp(Instant.now().toString())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Client Streaming Method - Accept multiple device commands from client
    @Override
    public StreamObserver<DeviceCommand> sendDeviceCommands(final StreamObserver<CommandSummaryResponse> responseObserver) {
        return new StreamObserver<DeviceCommand>() {
            int count = 0;

            @Override
            public void onNext(DeviceCommand command) {
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
                String replyMessage;

                switch (message.getMessage().toLowerCase()) {
                    case "status?":
                        replyMessage = "Current status: ON";
                        break;
                    case "temperature?":
                        replyMessage = "Current temperature: 21.5 Degree Celsius";
                        break;
                    case "position?":
                    case "position ?":
                         // Default 50%
                        int position = blindsPosition.getOrDefault(message.getDeviceId(), 50);
                        replyMessage = "Blinds are " + position + "% closed";
                        break;
                    case "restart":
                        replyMessage = "Restarting device...";
                        break;
                    case "set position":
                        int newPosition = Integer.parseInt(message.getMessage().split(" ")[2]);
                        blindsPosition.put(message.getDeviceId(), newPosition);
                        replyMessage = "Blinds position set to " + newPosition + "%";
                        break;
                    case "set temperature":
                        int newTemperature = Integer.parseInt(message.getMessage().split(" ")[2]);
                        airConditionerTemperature.put(message.getDeviceId(), newTemperature);
                        replyMessage = "Air conditioner temperature set to " + newTemperature + "°C";
                        break;
                    default:
                        replyMessage = "Command '" + message.getMessage() + "' received";
                }

                DeviceMessage reply = DeviceMessage.newBuilder()
                    .setDeviceId(message.getDeviceId())
                    .setMessage(" " + replyMessage)
                    .build();

                System.out.println("[MESSAGE] Device " + message.getDeviceId() + " replied: " + replyMessage);
                responseObserver.onNext(reply);
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
