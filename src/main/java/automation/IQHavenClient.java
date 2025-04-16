package automation;

import automation.Automation.*;
import automation.Energy.*;
import automation.Security.*;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Unified gRPC client for IQHaven services (Automation, Energy, Security).
 * Supports all RPC types (Unary, Server Streaming, Client Streaming, Bidirectional).
 */
public class IQHavenClient {
    private static final Logger logger = Logger.getLogger(IQHavenClient.class.getName());

    private final ManagedChannel channel;

    {
        channel = null;
    }

    // Automation service stubs
    private final AutomationServiceGrpc.AutomationServiceBlockingStub automationBlockingStub;
    private final AutomationServiceGrpc.AutomationServiceStub automationAsyncStub;

    // Energy service stubs
    private final EnergyServiceGrpc.EnergyServiceBlockingStub energyBlockingStub;
    private final EnergyServiceGrpc.EnergyServiceStub energyAsyncStub;

    // Security service stubs
    private final SecurityServiceGrpc.SecurityServiceBlockingStub securityBlockingStub;
    private final SecurityServiceGrpc.SecurityServiceStub securityAsyncStub;

    /**
     * Constructor to create a client connecting to the given host and port.
     */
    public IQHavenClient(String host, int port) {
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(host, port)
                // Configuração de compressão (adicionado)
                .compressorRegistry(CompressorRegistry.getDefaultInstance())
                .decompressorRegistry(DecompressorRegistry.getDefaultInstance());

        boolean useTls = false;
        if (useTls) {
            channelBuilder.useTransportSecurity();  // Removido o .clone()
        } else {
            channelBuilder.usePlaintext();
        }


        // Configuração de interceptors para autenticação (se necessário)
        String authToken = "";
        if (authToken != null && !authToken.isEmpty()) {
            // Cria um ClientInterceptor para adicionar o token no header
            ClientInterceptor authInterceptor = new ClientInterceptor() {
                @Override
                public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
                        MethodDescriptor<ReqT, RespT> method,
                        CallOptions callOptions,
                        Channel next
                ) {
                    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
                        @Override
                        public void start(Listener<RespT> responseListener, Metadata headers) {
                            // Adiciona o token no header (ex: "Bearer <token>")
                            headers.put(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER),
                                    "Bearer " + authToken);
                            super.start(responseListener, headers);
                        }
                    };
                }
            };
            channelBuilder.intercept(authInterceptor); // Aplica o interceptor
        }

        // Constrói o canal gRPC
        ManagedChannel channel = channelBuilder.build();
        // ... (cria stubs gRPC, como newStub ou blockingStub)


        channel = channelBuilder.build();

        // Inicialização dos stubs
        automationBlockingStub = AutomationServiceGrpc.newBlockingStub(channel);
        automationAsyncStub = AutomationServiceGrpc.newStub(channel);
        energyBlockingStub = EnergyServiceGrpc.newBlockingStub(channel);
        energyAsyncStub = EnergyServiceGrpc.newStub(channel);
        securityBlockingStub = SecurityServiceGrpc.newBlockingStub(channel);
        securityAsyncStub = SecurityServiceGrpc.newStub(channel);
    }
    // ==================== Automation Service Methods ====================

    public void toggleDevice(String deviceId, boolean turnOn) {
        ToggleDeviceRequest request = ToggleDeviceRequest.newBuilder()
                .setDeviceId(deviceId)
                .setTurnOn(turnOn)
                .build();

        try {
            ToggleDeviceResponse response = automationBlockingStub.toggleDevice(request);
            logger.info("[Automation] ToggleDevice: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            logger.log(Level.SEVERE, "[Automation Error] ToggleDevice failed: " + e.getStatus().getDescription());
        }
    }

    public void setSchedule(String deviceId, String time, boolean turnOn) {
        SetScheduleRequest request = SetScheduleRequest.newBuilder()
                .setDeviceId(deviceId)
                .setScheduleTime(time)
                .setTurnOn(turnOn)
                .build();

        try {
            SetScheduleResponse response = automationBlockingStub.setSchedule(request);
            logger.info("[Automation] SetSchedule: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            logger.log(Level.SEVERE, "[Automation Error] SetSchedule failed: " + e.getStatus().getDescription());
        }
    }

    public void streamDeviceStatus(String deviceId) {
        StreamDeviceStatusRequest request = StreamDeviceStatusRequest.newBuilder()
                .setDeviceId(deviceId)
                .build();

        try {
            Iterator<DeviceStatusResponse> responses = automationBlockingStub.streamDeviceStatus(request);
            while (responses.hasNext()) {
                DeviceStatusResponse response = responses.next();
                logger.info("[Automation Status] " + response.getStatus() + " | Time: " + response.getTimestamp());
            }
        } catch (StatusRuntimeException e) {
            logger.log(Level.SEVERE, "[Automation Error] StreamDeviceStatus failed: " + e.getStatus().getDescription());
        }
    }

    public void sendDeviceCommands(DeviceCommand[] commands) throws InterruptedException {
        CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<CommandSummaryResponse> responseObserver = new StreamObserver<CommandSummaryResponse>() {
            @Override
            public void onNext(CommandSummaryResponse response) {
                logger.info("[Automation] Commands received: " + response.getCommandsReceived() + ", Success: " + response.getSuccess());
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.SEVERE, "[Automation Error] sendDeviceCommands failed: " + t.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        };

        StreamObserver<DeviceCommand> requestObserver = automationAsyncStub.sendDeviceCommands(responseObserver);

        try {
            for (DeviceCommand command : commands) {
                requestObserver.onNext(command);
            }
            requestObserver.onCompleted();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "[Automation Error] Exception while sending commands: " + e.getMessage());
            requestObserver.onError(e);
        }

        finishLatch.await(5, TimeUnit.SECONDS);
    }

    public void communicateWithDevice(DeviceMessage[] messages) throws InterruptedException {
        CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<DeviceMessage> responseObserver = new StreamObserver<DeviceMessage>() {
            @Override
            public void onNext(DeviceMessage response) {
                logger.info("[Automation] Device responded: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.SEVERE, "[Automation Error] Bidirectional stream failed: " + t.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        };

        StreamObserver<DeviceMessage> requestObserver = automationAsyncStub.communicateWithDevice(responseObserver);

        try {
            for (DeviceMessage message : messages) {
                requestObserver.onNext(message);
            }
            requestObserver.onCompleted();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "[Automation Error] Exception during bidirectional stream: " + e.getMessage());
            requestObserver.onError(e);
        }

        finishLatch.await(10, TimeUnit.SECONDS);
    }

    // ==================== Energy Service Methods ====================

    public void optimizeEnergy(String deviceId, String suggestion) {
        OptimizeEnergyRequest request = OptimizeEnergyRequest.newBuilder()
                .setDeviceId(deviceId)
                .setSuggestion(suggestion)
                .build();

        energyAsyncStub.optimizeEnergy(request, new StreamObserver<OptimizeEnergyResponse>() {
            @Override
            public void onNext(OptimizeEnergyResponse response) {
                logger.info("[Energy] Optimization result: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                if (t instanceof StatusRuntimeException) {
                    logger.log(Level.SEVERE, "[Energy Error] gRPC Error: " + ((StatusRuntimeException) t).getStatus().getDescription());
                } else {
                    logger.log(Level.SEVERE, "[Energy Error] Error in energy optimization", t);
                }
            }

            @Override
            public void onCompleted() {
                logger.info("[Energy] Energy optimization completed.");
            }
        });
    }

    public void streamEnergyUsage(String deviceId) {
        StreamEnergyUsageRequest request = StreamEnergyUsageRequest.newBuilder()
                .setDeviceId(deviceId)
                .build();

        StreamObserver<EnergyUsageResponse> responseObserver = new StreamObserver<EnergyUsageResponse>() {
            @Override
            public void onNext(EnergyUsageResponse response) {
                logger.info("[Energy] Energy usage: " + response.getUsage() + " at " + response.getTimestamp());
            }

            @Override
            public void onError(Throwable t) {
                if (t instanceof StatusRuntimeException) {
                    logger.log(Level.SEVERE, "[Energy Error] gRPC Error: " + ((StatusRuntimeException) t).getStatus().getDescription());
                } else {
                    logger.log(Level.SEVERE, "[Energy Error] Error in energy usage streaming", t);
                }
            }

            @Override
            public void onCompleted() {
                logger.info("[Energy] Energy streaming completed.");
            }
        };

        energyAsyncStub.streamEnergyUsage(request, responseObserver);
    }

    // ==================== Security Service Methods ====================

    public void toggleAlarm(boolean activate) {
        try {
            ToggleAlarmRequest request = ToggleAlarmRequest.newBuilder().setActivate(activate).build();
            ToggleAlarmResponse response = securityBlockingStub.toggleAlarm(request);
            logger.info("[Security] Toggle Response: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            logger.log(Level.SEVERE, "[Security Error] Error toggling alarm: " + e.getStatus().getDescription());
        }
    }

    public void monitorAlarmStatus() {
        try {
            AlarmStatusRequest request = AlarmStatusRequest.newBuilder().build();
            Iterator<AlarmStatusResponse> responses = securityBlockingStub.monitorAlarmStatus(request);

            new Thread(() -> {
                int count = 0;
                try {
                    while (responses.hasNext() && count++ < 3) {
                        AlarmStatusResponse res = responses.next();
                        logger.info("[Security] Alarm Status: " + res.getTimestamp() + " - " + res.getStatus());
                    }
                    logger.info("[Security] Stopping alarm monitoring stream...");
                } catch (StatusRuntimeException e) {
                    logger.log(Level.SEVERE, "[Security Error] Error receiving alarm status: " + e.getStatus().getDescription());
                }
            }).start();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "[Security Error] Streaming failed: " + e.getMessage());
        }
    }

    public void liveSecurityFeed(SecurityEvent[] events) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<SecurityAlert> alertObserver = new StreamObserver<SecurityAlert>() {
            @Override
            public void onNext(SecurityAlert alert) {
                logger.info("[Security] Received Alert: " + alert.getAlertLevel() + " - " + alert.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.SEVERE, "[Security Error] Error in security feed: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                logger.info("[Security] Security feed ended.");
                latch.countDown();
            }
        };

        StreamObserver<SecurityEvent> eventStream = securityAsyncStub.liveSecurityFeed(alertObserver);

        try {
            for (SecurityEvent event : events) {
                eventStream.onNext(event);
            }
            eventStream.onCompleted();
        } catch (Exception e) {
            eventStream.onError(e);
        }

        latch.await(5, TimeUnit.SECONDS);
    }

    /**
     * Shuts down the channel gracefully.
     */
    public void shutdown() throws InterruptedException {
        if (channel != null && !channel.isShutdown()) {
            logger.info("Shutting down channel...");
            channel.shutdown().awaitTermination(3, TimeUnit.SECONDS);
        }
    }

    /**
     * Example usage of the unified client.
     */
    public static void main(String[] args) throws InterruptedException {
        IQHavenClient client = new IQHavenClient("localhost", 50051);

        try {
            // Test Automation service
            client.toggleDevice("room light", true);
            client.setSchedule("room light", "10:00 pm", false);
            client.streamDeviceStatus("room light");

            DeviceCommand[] commands = {
                    DeviceCommand.newBuilder().setDeviceId("room light").setCommand("turn_on").build(),
                    DeviceCommand.newBuilder().setDeviceId("kitchen light").setCommand("turn_off").build()
            };
            client.sendDeviceCommands(commands);

            // Test Energy service
            client.optimizeEnergy("device123", "Turn off lights when not in use");
            client.streamEnergyUsage("device123");

            // Test Security service
            client.toggleAlarm(true);
            client.monitorAlarmStatus();

            SecurityEvent[] events = {
                    SecurityEvent.newBuilder().setEventType("movement").setDetails("Living Room").build(),
                    SecurityEvent.newBuilder().setEventType("door").setDetails("Front Door Opened").build()
            };
            client.liveSecurityFeed(events);

        } finally {
            client.shutdown();
        }
    }
}