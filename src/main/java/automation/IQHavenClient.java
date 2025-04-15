package automation;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.time.Instant;

public class IQHavenClient {
    private final ManagedChannel channel;

    // Stubs para AutomationService
    private final AutomationServiceGrpc.AutomationServiceBlockingStub automationBlockingStub;
    private final AutomationServiceGrpc.AutomationServiceStub automationAsyncStub;

    // Stubs para EnergyService
    private final EnergyServiceGrpc.EnergyServiceBlockingStub energyBlockingStub;
    private final EnergyServiceGrpc.EnergyServiceStub energyAsyncStub;

    // Stubs para SecurityService
    private final SecurityServiceGrpc.SecurityServiceBlockingStub securityBlockingStub;
    private final SecurityServiceGrpc.SecurityServiceStub securityAsyncStub;

    public IQHavenClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        // Inicializa stubs para todos os serviços
        this.automationBlockingStub = AutomationServiceGrpc.newBlockingStub(channel);
        this.automationAsyncStub = AutomationServiceGrpc.newStub(channel);

        this.energyBlockingStub = EnergyServiceGrpc.newBlockingStub(channel);
        this.energyAsyncStub = EnergyServiceGrpc.newStub(channel);

        this.securityBlockingStub = SecurityServiceGrpc.newBlockingStub(channel);
        this.securityAsyncStub = SecurityServiceGrpc.newStub(channel);
    }

    // ==================== AUTOMATION SERVICE ====================

    public void toggleDevice(String deviceId, boolean turnOn) {
        Automation.ToggleDeviceRequest request = Automation.ToggleDeviceRequest.newBuilder()
                .setDeviceId(deviceId)
                .setTurnOn(turnOn)
                .build();

        Automation.ToggleDeviceResponse response = automationBlockingStub.toggleDevice(request);
        System.out.println("[Automation] " + response.getMessage());
    }

    public void streamDeviceStatus(String deviceId) {
        Automation.StreamDeviceStatusRequest request = Automation.StreamDeviceStatusRequest.newBuilder()
                .setDeviceId(deviceId)
                .build();

        System.out.println("[Automation] Recebendo status do dispositivo:");
        automationBlockingStub.streamDeviceStatus(request)
                .forEachRemaining(status -> {
                    System.out.println("  → " + status.getStatus() + " | " + status.getTimestamp());
                });
    }

    public void sendDeviceCommands() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<Automation.CommandSummaryResponse> responseObserver =
                new StreamObserver<Automation.CommandSummaryResponse>() {
                    @Override
                    public void onNext(Automation.CommandSummaryResponse summary) {
                        System.out.println("[Automation] Total de comandos processados: " +
                                summary.getCommandsReceived());
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

        StreamObserver<Automation.DeviceCommand> requestObserver =
                automationAsyncStub.sendDeviceCommands(responseObserver);

        // Envia comandos de exemplo
        String[] commands = {"LIGAR", "AJUSTAR_TEMPERATURA_22", "MODO_AUTOMATICO", "DESLIGAR"};
        for (String cmd : commands) {
            requestObserver.onNext(
                    Automation.DeviceCommand.newBuilder()
                            .setDeviceId("TERMOSTATO_01")
                            .setCommand(cmd)
                            .build()
            );
            Thread.sleep(800);
        }

        requestObserver.onCompleted();
        latch.await(5, TimeUnit.SECONDS);
    }

    // ==================== ENERGY SERVICE ====================

    public void optimizeEnergy(String deviceId) {
        Energy.OptimizeEnergyRequest request = Energy.OptimizeEnergyRequest.newBuilder()
                .setDeviceId(deviceId)
                .build();

        Energy.OptimizeEnergyResponse response = energyBlockingStub.optimizeEnergy(request);
        System.out.println("[Energy] " + response.getMessage());
    }

    public void streamEnergyUsage(String deviceId) {
        Energy.StreamEnergyUsageRequest request = Energy.StreamEnergyUsageRequest.newBuilder()
                .setDeviceId(deviceId)
                .build();

        System.out.println("[Energy] Monitorando consumo de energia:");
        energyBlockingStub.streamEnergyUsage(request)
                .forEachRemaining(usage -> {
                    System.out.printf("  → %.2f kWh às %s%n",
                            usage.getUsage(),
                            usage.getTimestamp());
                });
    }

    public void sendEnergyData() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<Energy.EnergyDataSummaryResponse> responseObserver =
                new StreamObserver<Energy.EnergyDataSummaryResponse>() {
                    @Override
                    public void onNext(Energy.EnergyDataSummaryResponse summary) {
//                        System.out.printf("[Energy] Dados recebidos: %d leituras | Média: %.2f kWh%n",
//                                summary.getDataPointsReceived(),
//                                summary.getAverageConsumption());
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

        StreamObserver<Energy.EnergyData> requestObserver =
                energyAsyncStub.sendEnergyData(responseObserver);

        // Envia dados de exemplo
        for (int i = 0; i < 10; i++) {
            double consumption = 1.5 + (Math.random() * 3.5);
            requestObserver.onNext(
                    Energy.EnergyData.newBuilder()
                            .setDeviceId("MEDIDOR_01")
                            .setEnergyConsumption(consumption)
                            .build()
            );
            Thread.sleep(500);
        }

        requestObserver.onCompleted();
        latch.await(5, TimeUnit.SECONDS);
    }

    // ==================== SECURITY SERVICE ====================

    public void toggleAlarm(boolean activate) {
        Security.ToggleAlarmRequest request = Security.ToggleAlarmRequest.newBuilder()
                .setActivate(activate)
                .build();

        Security.ToggleAlarmResponse response = securityBlockingStub.toggleAlarm(request);
        System.out.println("[Security] " + response.getMessage());
    }

    public void monitorAlarmStatus() {
        Security.AlarmStatusRequest request = Security.AlarmStatusRequest.newBuilder().build();

        System.out.println("[Security] Status do alarme:");
        securityBlockingStub.monitorAlarmStatus(request)
                .forEachRemaining(status -> {
                    System.out.println("  → " + status.getStatus() + " | " + status.getTimestamp());
                });
    }

    public void liveSecurityFeed() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<Security.SecurityAlert> responseObserver =
                new StreamObserver<Security.SecurityAlert>() {
                    @Override
                    public void onNext(Security.SecurityAlert alert) {
                        System.out.printf("[Security] Alerta: %s - %s%n",
                                alert.getAlertLevel(),
                                alert.getMessage());
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.err.println("Erro: " + t.getMessage());
                        latch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("[Security] Feed encerrado");
                        latch.countDown();
                    }
                };

        StreamObserver<Security.SecurityEvent> requestObserver =
                securityAsyncStub.liveSecurityFeed(responseObserver);

        // Envia eventos de exemplo
        String[] events = {
                "MOVIMENTO: Área externa",
                "PORTA_ABERTA: Entrada principal",
                "VIDRO_QUEBRADO: Janela sala",
                "MOVIMENTO: Corredor"
        };

        for (String event : events) {
            String[] parts = event.split(":");
            requestObserver.onNext(
                    Security.SecurityEvent.newBuilder()
                            .setEventType(parts[0].trim())
                            .setDetails(parts[1].trim())
                            .build()
            );
            Thread.sleep(1500);
        }

        requestObserver.onCompleted();
        latch.await(10, TimeUnit.SECONDS);
    }

    // ==================== TEST METHODS ====================

    public void testAllServices() throws InterruptedException {
        System.out.println("\n=== TESTANDO TODOS OS SERVIÇOS ===");

        // Testa AutomationService
        System.out.println("\n[AutomationService]");
        toggleDevice("LUZ_01", true);
        streamDeviceStatus("TERMOSTATO_01");
        sendDeviceCommands();

        // Testa EnergyService
        System.out.println("\n[EnergyService]");
        optimizeEnergy("PAINEL_SOLAR_01");
        streamEnergyUsage("CASA_01");
        sendEnergyData();

        // Testa SecurityService
        System.out.println("\n[SecurityService]");
        toggleAlarm(true);
        monitorAlarmStatus();
        liveSecurityFeed();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("\nConexão encerrada");
    }

    public static void main(String[] args) {
        System.out.println("Iniciando cliente IQHaven...");

        IQHavenClient client = new IQHavenClient("localhost", 50051);

        try {
            client.testAllServices();
        } catch (InterruptedException e) {
            System.err.println("Teste interrompido: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        } finally {
            try {
                client.shutdown();
            } catch (InterruptedException e) {
                System.err.println("Erro ao encerrar: " + e.getMessage());
            }
        }
    }
}