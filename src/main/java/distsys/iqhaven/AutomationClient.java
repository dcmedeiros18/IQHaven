/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.iqhaven;

/**
 *
 * @author dcmed
 */

import automation.Automation.*;
import automation.AutomationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    // 1. Unary - Toggle device
    public void toggleDevice(String deviceId, boolean turnOn) {
        ToggleDeviceRequest request = ToggleDeviceRequest.newBuilder()
            .setDeviceId(deviceId)
            .setTurnOn(turnOn)
            .build();

        ToggleDeviceResponse response = blockingStub.toggleDevice(request);
        System.out.println("ToggleDevice: " + response.getMessage());
    }

    // 2. Unary - Set schedule
    public void setSchedule(String deviceId, String time, boolean turnOn) {
        SetScheduleRequest request = SetScheduleRequest.newBuilder()
            .setDeviceId(deviceId)
            .setScheduleTime(time)
            .setTurnOn(turnOn)
            .build();

        SetScheduleResponse response = blockingStub.setSchedule(request);
        System.out.println("SetSchedule: " + response.getMessage());
    }

    // 3. Server streaming - Stream device status
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

    // 4. Client streaming - Send multiple device commands
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

    // 5. Bidirectional streaming - Communicate with device
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

    public static void main(String[] args) throws InterruptedException {
        AutomationClient client = new AutomationClient("localhost", 50051);

        // 1. Toggle
        client.toggleDevice("luz-sala", true);

        // 2. Schedule
        client.setSchedule("luz-sala", "22:00", false);

        // 3. Stream status
        client.streamDeviceStatus("luz-sala");

        // 4. Send multiple commands
        DeviceCommand[] commands = {
            DeviceCommand.newBuilder().setDeviceId("luz-sala").setCommand("turn_on").build(),
            DeviceCommand.newBuilder().setDeviceId("luz-cozinha").setCommand("turn_off").build()
        };
        client.sendDeviceCommands(commands);

        // 5. Bidirectional communication
        DeviceMessage[] messages = {
            DeviceMessage.newBuilder().setDeviceId("luz-sala").setMessage("status?").build(),
            DeviceMessage.newBuilder().setDeviceId("luz-cozinha").setMessage("reiniciar").build()
        };
        client.communicateWithDevice(messages);
    }
}
