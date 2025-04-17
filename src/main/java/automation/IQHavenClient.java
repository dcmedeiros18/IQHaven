package automation;

public class IQHavenClient { /*
    private final ManagedChannel channel;
    private final EnergyServiceGrpc.EnergyServiceStub energyStub;
    private final AutomationServiceGrpc.AutomationServiceBlockingStub automationBlockingStub;
    private final AutomationServiceGrpc.AutomationServiceStub automationAsyncStub;
    private final SecurityServiceGrpc.SecurityServiceStub securityAsyncStub;
    private final SecurityServiceGrpc.SecurityServiceBlockingStub securityBlockingStub;

    public IQHavenClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        this.energyStub = EnergyServiceGrpc.newStub(channel);
        this.automationBlockingStub = AutomationServiceGrpc.newBlockingStub(channel);
        this.automationAsyncStub = AutomationServiceGrpc.newStub(channel);
        this.securityAsyncStub = SecurityServiceGrpc.newStub(channel);
        this.securityBlockingStub = SecurityServiceGrpc.newBlockingStub(channel);
    }

    // Automation Methods
    public void toggleDevice(String deviceId, boolean turnOn) {
        System.out.println("Toggling device: " + deviceId + " to " + (turnOn ? "ON" : "OFF"));

        try {
            ToggleDeviceResponse response = automationBlockingStub
                    .toggleDevice(ToggleDeviceRequest.newBuilder()
                            .setDeviceId(deviceId)
                            .setTurnOn(turnOn)
                            .build());

            System.out.println("Toggle successful: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            System.err.println("Toggle failed: " + e.getStatus().getDescription());
        }
    }

    public void setSchedule(String deviceId, String time, boolean enable) {
        System.out.println("Setting schedule for " + deviceId + " at " + time + " to " + (enable ? "ON" : "OFF"));

        try {
            SetScheduleResponse response = automationBlockingStub
                    .setSchedule(SetScheduleRequest.newBuilder()
                            .setDeviceId(deviceId)
                            .setScheduleTime(time)
                            .setTurnOn(enable)
                            .build());

            System.out.println("Schedule set: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            System.err.println("Failed to set schedule: " + e.getStatus().getDescription());
        }
    }

    public void communicateWithDevice(DeviceMessage[] messages) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<DeviceMessage> responseObserver = new StreamObserver<DeviceMessage>() {
            @Override
            public void onNext(DeviceMessage response) {
                System.out.println("Device response: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Communication error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Communication completed");
                latch.countDown();
            }
        };

        StreamObserver<DeviceMessage> requestObserver = automationAsyncStub
                .communicateWithDevice(responseObserver);

        try {
            for (DeviceMessage message : messages) {
                System.out.println("Sending to " + message.getDeviceId() + ": " + message.getMessage());
                requestObserver.onNext(message);
            }
            requestObserver.onCompleted();
        } catch (Exception e) {
            requestObserver.onError(e);
            throw e;
        }

        latch.await(5, TimeUnit.SECONDS);
    }

    // Energy Methods
    public void optimizeEnergy(String deviceId, String suggestion) {
        System.out.println("Optimizing device: " + deviceId + " with setting: " + suggestion);

        try {
            OptimizeEnergyRequest request = OptimizeEnergyRequest.newBuilder()
                    .setDeviceId(deviceId)
                    .setSuggestion(suggestion)
                    .build();

            energyStub.optimizeEnergy(request, new StreamObserver<OptimizeEnergyResponse>() {
                @Override
                public void onNext(OptimizeEnergyResponse response) {
                    System.out.println("Optimization result: " + response.getMessage());
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Optimization error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Optimization completed");
                }
            });
        } catch (StatusRuntimeException e) {
            System.err.println("Optimization failed: " + e.getStatus().getDescription());
        }
    }

    public void streamEnergyUsage(String deviceId) {
        System.out.println("Starting energy usage stream for: " + deviceId);

        StreamObserver<EnergyUsageResponse> responseObserver = new StreamObserver<EnergyUsageResponse>() {
            @Override
            public void onNext(EnergyUsageResponse data) {
                System.out.println("Energy usage: " + data.getUsage() + " kWh at " + data.getTimestamp());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Energy stream error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Energy stream completed");
            }
        };

        StreamEnergyUsageRequest request = StreamEnergyUsageRequest.newBuilder()
                .setDeviceId(deviceId)
                .build();

        energyStub.streamEnergyUsage(request, responseObserver);
    }

    // Security Methods
    public void toggleAlarm(boolean enable) {
        System.out.println((enable ? "Enabling" : "Disabling") + " alarm system");

        try {
            ToggleAlarmResponse response = securityBlockingStub
                    .toggleAlarm(ToggleAlarmRequest.newBuilder()
                            .setActivate(enable)
                            .build());

            System.out.println("Alarm status: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            System.err.println("Alarm operation failed: " + e.getStatus().getDescription());
        }
    }

    public void liveSecurityFeed(SecurityEvent[] events) {
        System.out.println("Sending security events");

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<SecurityEvent> requestObserver = securityAsyncStub.liveSecurityFeed(
                new StreamObserver<SecurityAlert>() {
                    @Override
                    public void onNext(SecurityAlert response) {
                        System.out.println("Security status: " + response.getMessage() + " (Level: " + response.getAlertLevel() + ")");
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.err.println("Security feed error: " + t.getMessage());
                        latch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Security feed completed");
                        latch.countDown();
                    }
                }
        );

        try {
            for (SecurityEvent event : events) {
                requestObserver.onNext(event);
            }
            requestObserver.onCompleted();
            latch.await(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            requestObserver.onError(e);
            System.err.println("Error sending security events: " + e.getMessage());
        }
    }

    public void shutdown() {
        channel.shutdown();
    }

    public static void main(String[] args) {
        IQHavenClient client = null;

        try {
            client = new IQHavenClient("localhost", 50051);

            System.out.println("[Connection] Testing...");
            client.toggleDevice("test_device", false);
            System.out.println("[Connection] Successful!\n");

            System.out.println("=== AUTOMATION COMMANDS ===");
            client.toggleDevice("living_room_light", true);
            client.setSchedule("air_conditioner", "22:00", false);

            DeviceMessage[] messages = {
                    DeviceMessage.newBuilder().setDeviceId("bedroom_light").setMessage("status").build(),
                    DeviceMessage.newBuilder().setDeviceId("living_room_curtain").setMessage("open").build()
            };
            client.communicateWithDevice(messages);

            System.out.println("\n=== ENERGY OPTIMIZATION ===");
            client.optimizeEnergy("air_conditioner", "Set to 24°C at night");
            client.streamEnergyUsage("living_room_light");

            System.out.println("\n=== SECURITY MONITORING ===");
            client.toggleAlarm(true);

            SecurityEvent[] events = {
                    SecurityEvent.newBuilder().setEventType("motion").setDetails("Outside area").build(),
                    SecurityEvent.newBuilder().setEventType("door").setDetails("Main door").build()
            };
            client.liveSecurityFeed(events);

            Thread.sleep(10000);

        } catch (Exception e) {
            System.err.println("\n[ERROR] " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.shutdown();
            }
            System.out.println("\n[Client] Shutdown");
        }
    }*/
}
