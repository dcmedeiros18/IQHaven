package automation;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import automation.Automation.*;

public class AutomationClient {

    private final AutomationServiceGrpc.AutomationServiceBlockingStub blockingStub;
    private final AutomationServiceGrpc.AutomationServiceStub asyncStub;

    public AutomationClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = AutomationServiceGrpc.newBlockingStub(channel);
        asyncStub = AutomationServiceGrpc.newStub(channel);
    }

    // Unary RPC - ToggleDevice
    public void toggleDevice(String deviceId, boolean turnOn) {
        Automation.ToggleDeviceRequest request = Automation.ToggleDeviceRequest.newBuilder()
                .setDeviceId(deviceId)
                .setTurnOn(turnOn)
                .build();

        ToggleDeviceResponse response = blockingStub.toggleDevice(request);
        System.out.println("ToggleDevice response: " + response.getMessage());
    }

    // Unary RPC - SetSchedule
    public void setSchedule(String deviceId, String time, boolean turnOn) {
        SetScheduleRequest request = SetScheduleRequest.newBuilder()
                .setDeviceId(deviceId)
                .setScheduleTime(time)
                .setTurnOn(turnOn)
                .build();

        SetScheduleResponse response = blockingStub.setSchedule(request);
        System.out.println("SetSchedule response: " + response.getMessage());
    }

    // Server Streaming - StreamDeviceStatus
    public void streamDeviceStatus(String deviceId) {
        StreamDeviceStatusRequest request = StreamDeviceStatusRequest.newBuilder()
                .setDeviceId(deviceId)
                .build();

        Iterator<DeviceStatusResponse> responses = blockingStub.streamDeviceStatus(request);
        while (responses.hasNext()) {
            DeviceStatusResponse response = responses.next();
            System.out.println("Device status: " + response.getStatus() + " at " + response.getTimestamp());
        }
    }

    // Client Streaming - SendDeviceCommands
    public void sendDeviceCommands() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver responseObserver = new StreamObserver() {

            public void onNext(CommandSummaryResponse response) {
                System.out.println("Commands received: " + response.getCommandsReceived());
            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Command stream completed.");
                latch.countDown();
            }
        };

        StreamObserver<DeviceCommand> requestObserver = asyncStub.sendDeviceCommands(responseObserver);
        try {
            requestObserver.onNext(DeviceCommand.newBuilder().setDeviceId("device1").setCommand("ON").build());
            requestObserver.onNext(DeviceCommand.newBuilder().setDeviceId("device2").setCommand("OFF").build());
            requestObserver.onNext(DeviceCommand.newBuilder().setDeviceId("device3").setCommand("RESET").build());
            requestObserver.onCompleted();
        } catch (Exception e) {
            requestObserver.onError(e);
        }

        latch.await(3, TimeUnit.SECONDS);
    }

    // Bidirectional Streaming - CommunicateWithDevice
    public void communicateWithDevice() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<DeviceMessage> requestObserver = asyncStub.communicateWithDevice(new StreamObserver() {

            public void onNext(DeviceMessage value) {
                System.out.println("Received from server: " + value.getMessage());
            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Bi-directional stream ended.");
                latch.countDown();
            }
        });

        requestObserver.onNext(DeviceMessage.newBuilder().setDeviceId("device1").setMessage("Start").build());
        requestObserver.onNext(DeviceMessage.newBuilder().setDeviceId("device1").setMessage("Check").build());
        requestObserver.onNext(DeviceMessage.newBuilder().setDeviceId("device1").setMessage("Stop").build());
        requestObserver.onCompleted();

        latch.await(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        AutomationClient client = new AutomationClient("localhost", 50051);

        client.toggleDevice("lamp01", true);
        client.setSchedule("lamp01", "22:00", false);
        client.streamDeviceStatus("lamp01");
        client.sendDeviceCommands();
        client.communicateWithDevice();
    }
}
