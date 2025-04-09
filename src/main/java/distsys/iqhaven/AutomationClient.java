package distsys.iqhaven;

import automation.Automation.*;
import automation.AutomationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * AutomationService via gRPC.
 * Communication types: Unary, Server Streaming,
 * Client Streaming, and Bidirectional Streaming.
 * @author dcmed
 */
public class AutomationClient {
    private final AutomationServiceGrpc.AutomationServiceBlockingStub blockingStub;
    private final AutomationServiceGrpc.AutomationServiceStub asyncStub;

    /**
     * Constructor to create a client connecting to the given host and port.
     */
    public AutomationClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext()
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

        ToggleDeviceResponse response = blockingStub.toggleDevice(request);
        System.out.println("ToggleDevice: " + response.getMessage());
    }

    // 2. Unary RPC - Schedule a device to be turned ON/OFF at a specific time
    public void setSchedule(String deviceId, String time, boolean turnOn) {
        SetScheduleRequest request = SetScheduleRequest.newBuilder()
            .setDeviceId(deviceId)
            .setScheduleTime(time)
            .setTurnOn(turnOn)
            .build();

        SetScheduleResponse response = blockingStub.setSchedule(request);
        System.out.println("SetSchedule: " + response.getMessage());
    }

    // 3. Server Streaming RPC - Continuously receive status updates from a device
    public void streamDeviceStatus(String deviceId) {
        StreamDeviceStatusRequest request = StreamDeviceStatusRequest.newBuilder()
            .setDeviceId(deviceId)
            .build();

        Iterator<DeviceStatusResponse> responses = blockingStub.streamDeviceStatus(request);
        while (responses.hasNext()) {
            DeviceStatusResponse response = responses.next();
            System.out.println("Status: " + response.getStatus() + " | Time: " + response.getTimestamp());
        }
    }

    // 4. Client Streaming RPC - Send multiple commands to the server and receive a single summary response
    public void sendDeviceCommands(DeviceCommand[] commands) throws InterruptedException {
        final CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<CommandSummaryResponse> responseObserver = new StreamObserver<CommandSummaryResponse>() {
            @Override
            public void onNext(CommandSummaryResponse response) {
                System.out.println("Commands received: " + response.getCommandsReceived() + ", Success: " + response.getSuccess());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        };

        StreamObserver<DeviceCommand> requestObserver = asyncStub.sendDeviceCommands(responseObserver);

        for (DeviceCommand command : commands) {
            requestObserver.onNext(command);
        }
        requestObserver.onCompleted();

        finishLatch.await(5, TimeUnit.SECONDS);
    }

    // 5. Bidirectional Streaming RPC - Real-time communication with devices
    public void communicateWithDevice(DeviceMessage[] messages) throws InterruptedException {
        final CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<DeviceMessage> responseObserver = new StreamObserver<DeviceMessage>() {
            @Override
            public void onNext(DeviceMessage response) {
                System.out.println("Device responded: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        };

        StreamObserver<DeviceMessage> requestObserver = asyncStub.communicateWithDevice(responseObserver);

        for (DeviceMessage message : messages) {
            requestObserver.onNext(message);
        }

        requestObserver.onCompleted();
        finishLatch.await(10, TimeUnit.SECONDS);
    }

    /**
     * Entry point for testing all available gRPC methods.
     */
    public static void main(String[] args) throws InterruptedException {
        AutomationClient client = new AutomationClient("localhost", 50051);

        // 1. Toggle living room light ON
        client.toggleDevice("room light", true);

        // 2. Schedule living room light to turn OFF at 10 PM
        client.setSchedule("room light", "10:00 pm", false);

        // 3. Stream status updates for the living room light
        client.streamDeviceStatus("room light");

        // 4. Send multiple commands to different devices
        DeviceCommand[] commands = {
            DeviceCommand.newBuilder().setDeviceId("room light").setCommand("turn_on").build(),
            DeviceCommand.newBuilder().setDeviceId("kitchen light").setCommand("turn_off").build(),
            DeviceCommand.newBuilder().setDeviceId("room air conditioning").setCommand("turn_on").build(),
            DeviceCommand.newBuilder().setDeviceId("living room blinds").setCommand("close").build()
        };
        client.sendDeviceCommands(commands);

        // 5. Bidirectional communication with devices
        DeviceMessage[] messages = {
            DeviceMessage.newBuilder().setDeviceId("room light").setMessage("status?").build(),
            DeviceMessage.newBuilder().setDeviceId("kitchen light").setMessage("restart").build(),
            DeviceMessage.newBuilder().setDeviceId("room air conditioning").setMessage("temperature?").build(),
            DeviceMessage.newBuilder().setDeviceId("living room blinds").setMessage("position?").build()
        };
        client.communicateWithDevice(messages);
    }
}
