package automation;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class IQHavenClient {

    private final ManagedChannel channel;
    private final AutomationServiceGrpc.AutomationServiceBlockingStub blockingStub;
    private final AutomationServiceGrpc.AutomationServiceStub asyncStub;

    public IQHavenClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = AutomationServiceGrpc.newBlockingStub(channel);
        asyncStub = AutomationServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    // 1. Unary RPC
    public void toggleDevice() {
        Automation.ToggleDeviceRequest request = Automation.ToggleDeviceRequest.newBuilder()
                .setDeviceId("Lamp01")
                .setTurnOn(true)
                .build();

        Automation.ToggleDeviceResponse response = blockingStub.toggleDevice(request);
        System.out.println("[Unary] toggleDevice: " + response.getMessage());
    }

    // 2. Server Streaming RPC
    public void streamDeviceStatus() {
        Automation.StreamDeviceStatusRequest request = Automation.StreamDeviceStatusRequest.newBuilder()
                .setDeviceId("Fan01")
                .build();

        System.out.println("[Server Streaming] Status:");
        blockingStub.streamDeviceStatus(request).forEachRemaining(status -> {
            System.out.println("- " + status.getStatus() + " @ " + status.getTimestamp());
        });
    }

    // 3. Client Streaming RPC
    public void sendDeviceCommands() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver responseObserver = new StreamObserver() {

            public void onNext(Automation.CommandSummaryResponse summary) {
                System.out.println("[Client Streaming] Comandos recebidos: " + summary.getCommandsReceived());
            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Erro: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };

        StreamObserver<Automation.DeviceCommand> requestObserver = asyncStub.sendDeviceCommands(responseObserver);

        requestObserver.onNext(Automation.DeviceCommand.newBuilder()
                .setDeviceId("TV01")
                .setCommand("Ligar")
                .build());

        requestObserver.onNext(Automation.DeviceCommand.newBuilder()
                .setDeviceId("TV01")
                .setCommand("Aumentar volume")
                .build());

        requestObserver.onNext(Automation.DeviceCommand.newBuilder()
                .setDeviceId("TV01")
                .setCommand("Mudo")
                .build());

        requestObserver.onCompleted();
        latch.await(3, TimeUnit.SECONDS);
    }

    // 4. Bidirectional Streaming RPC
    public void communicateWithDevice() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<Automation.DeviceMessage> responseObserver = new StreamObserver<Automation.DeviceMessage>() {
            @Override
            public void onNext(Automation.DeviceMessage message) {
                System.out.println("[BiDi Streaming] Resposta do servidor: " + message.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Erro: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Comunicação finalizada.");
                latch.countDown();
            }
        };

        StreamObserver<Automation.DeviceMessage> requestObserver = asyncStub.communicateWithDevice(responseObserver);

        requestObserver.onNext(Automation.DeviceMessage.newBuilder()
                .setDeviceId("Sensor01")
                .setMessage("Temperatura alta")
                .build());

        requestObserver.onNext(Automation.DeviceMessage.newBuilder()
                .setDeviceId("Sensor01")
                .setMessage("Abrir janela")
                .build());

        requestObserver.onCompleted();

        latch.await(3, TimeUnit.SECONDS);
    }


    // Método main para teste
    public static void main(String[] args) throws InterruptedException {
        IQHavenClient client = new IQHavenClient("localhost", 50051);

        client.toggleDevice();
        client.streamDeviceStatus();
        client.sendDeviceCommands();
        client.communicateWithDevice();

        client.shutdown();
    }
}
