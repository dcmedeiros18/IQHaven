package distsys.iqhaven;

import automation.Automation.*;
import automation.AutomationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Client to interact with AutomationService via gRPC.
 * Supports Unary, Server Streaming, Client Streaming, and Bidirectional Streaming RPCs.
 * Handles devices like lights, air conditioners, and blinds with real-time control and feedback.
 * 
 * Author: dcmed (atualizado com melhorias por ChatGPT)
 */
public class AutomationClient {
    private final AutomationServiceGrpc.AutomationServiceBlockingStub blockingStub;
    private final AutomationServiceGrpc.AutomationServiceStub asyncStub;
    private final ManagedChannel channel;

    /**
     * Constructor to create a client connecting to the given host and port.
     */
    public AutomationClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext() // Disable TLS for simplicity
            .build();

        blockingStub = AutomationServiceGrpc.newBlockingStub(channel);
        asyncStub = AutomationServiceGrpc.newStub(channel);
    }

    // 1. Unary RPC - Toggle a specific device ON or OFF
    public void toggleDevice(String deviceId, boolean turnOn) {
        ToggleDeviceRequest request = ToggleDeviceRequest.newBuilder()
            .setDeviceId(deviceId)
            .setTurnOn(turnOn)
            .build();

        try {
            ToggleDeviceResponse response = blockingStub.toggleDevice(request);
            System.out.println("[Client] ToggleDevice: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            System.err.println("[Client Error] ToggleDevice failed: " + e.getStatus().getDescription());
        }
    }

    // 2. Unary RPC - Schedule a device to turn ON/OFF at a specific time
    public void setSchedule(String deviceId, String time, boolean turnOn) {
        SetScheduleRequest request = SetScheduleRequest.newBuilder()
            .setDeviceId(deviceId)
            .setScheduleTime(time)
            .setTurnOn(turnOn)
            .build();

        try {
            SetScheduleResponse response = blockingStub.setSchedule(request);
            System.out.println("[Client] SetSchedule: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            System.err.println("[Client Error] SetSchedule failed: " + e.getStatus().getDescription());
        }
    }

    // 3. Server Streaming RPC - Continuously receive status updates from a device
    public void streamDeviceStatus(String deviceId) {
        StreamDeviceStatusRequest request = StreamDeviceStatusRequest.newBuilder()
            .setDeviceId(deviceId)
            .build();

        try {
            Iterator<DeviceStatusResponse> responses = blockingStub.streamDeviceStatus(request);
            while (responses.hasNext()) {
                DeviceStatusResponse response = responses.next();
                System.out.println("[Status] " + response.getStatus() + " | Time: " + response.getTimestamp());
            }
        } catch (StatusRuntimeException e) {
            System.err.println("[Client Error] StreamDeviceStatus failed: " + e.getStatus().getDescription());
        }
    }

    // 4. Client Streaming RPC - Send multiple commands to the server and receive a summary
    public void sendDeviceCommands(DeviceCommand[] commands) throws InterruptedException {
        CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<CommandSummaryResponse> responseObserver = new StreamObserver<CommandSummaryResponse>() {
            @Override
            public void onNext(CommandSummaryResponse response) {
                System.out.println("[Client] Commands received: " + response.getCommandsReceived() + ", Success: " + response.getSuccess());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("[Client Error] sendDeviceCommands failed: " + t.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        };

        StreamObserver<DeviceCommand> requestObserver = asyncStub.sendDeviceCommands(responseObserver);

        try {
            for (DeviceCommand command : commands) {
                requestObserver.onNext(command);
            }
            requestObserver.onCompleted();
        } catch (Exception e) {
            System.err.println("[Client Error] Exception while sending commands: " + e.getMessage());
            requestObserver.onError(e);
        }

        finishLatch.await(5, TimeUnit.SECONDS);
    }

    // 5. Bidirectional Streaming RPC - Real-time communication with devices
    public void communicateWithDevice(DeviceMessage[] messages) throws InterruptedException {
        CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<DeviceMessage> responseObserver = new StreamObserver<DeviceMessage>() {
            @Override
            public void onNext(DeviceMessage response) {
                System.out.println("[Client] Device responded: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("[Client Error] Bidirectional stream failed: " + t.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        };

        StreamObserver<DeviceMessage> requestObserver = asyncStub.communicateWithDevice(responseObserver);

        try {
            for (DeviceMessage message : messages) {
                requestObserver.onNext(message);
            }
            requestObserver.onCompleted();
        } catch (Exception e) {
            System.err.println("[Client Error] Exception during bidirectional stream: " + e.getMessage());
            requestObserver.onError(e);
        }

        finishLatch.await(10, TimeUnit.SECONDS);
    }

    /**
     * Optional: shuts down the channel gracefully.
     */
    public void shutdown() throws InterruptedException {
        if (channel != null && !channel.isShutdown()) {
            System.out.println("[Client] Shutting down channel...");
            channel.shutdown().awaitTermination(3, TimeUnit.SECONDS);
        }
    }

    /**
     * Entry point for testing all gRPC client methods.
     */
    public static void main(String[] args) throws InterruptedException {
        AutomationClient client = new AutomationClient("localhost", 50051);

        try {
            client.toggleDevice("room light", true);
            client.setSchedule("room light", "10:00 pm", false);
            client.streamDeviceStatus("room light");

            DeviceCommand[] commands = {
                DeviceCommand.newBuilder().setDeviceId("room light").setCommand("turn_on").build(),
                DeviceCommand.newBuilder().setDeviceId("kitchen light").setCommand("turn_off").build(),
                DeviceCommand.newBuilder().setDeviceId("room air conditioning").setCommand("turn_on").build(),
                DeviceCommand.newBuilder().setDeviceId("living room blinds").setCommand("close").build()
            };
            client.sendDeviceCommands(commands);

            DeviceMessage[] messages = {
                DeviceMessage.newBuilder().setDeviceId("room light").setMessage("status?").build(),
                DeviceMessage.newBuilder().setDeviceId("kitchen light").setMessage("restart").build(),
                DeviceMessage.newBuilder().setDeviceId("room air conditioning").setMessage("temperature?").build(),
                DeviceMessage.newBuilder().setDeviceId("living room blinds").setMessage("position?").build()
            };
            client.communicateWithDevice(messages);

        } finally {
            client.shutdown(); // Close channel cleanly after execution
        }
    }
}
