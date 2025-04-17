package automation;

import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import automation.Security.AlarmStatusRequest;
import automation.Security.AlarmStatusResponse;
import automation.Security.SecurityAlert;
import automation.Security.SecurityEvent;
import automation.Security.ToggleAlarmRequest;
import automation.Security.ToggleAlarmResponse;

public class SecurityServiceImpl extends SecurityServiceGrpc.SecurityServiceImplBase {
    private final AtomicBoolean alarmActive = new AtomicBoolean(false);

    private String generateApiKey() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            int randIndex = (int) (Math.random() * chars.length());
            key.append(chars.charAt(randIndex));
        }
        return key.toString();
    }

    @Override
    public void toggleAlarm(ToggleAlarmRequest request, StreamObserver<ToggleAlarmResponse> responseObserver) {
        final String API_KEY = "T0ggl3AlmK3y" + generateApiKey().substring(0,3);
        try {
            boolean activate = request.getActivate();

            if (alarmActive.get() == activate) {
                ToggleAlarmResponse response = (ToggleAlarmResponse) ToggleAlarmResponse.newBuilder()
                        .setSuccess(false)
                        .setMessage("Alarm is already " + (activate ? "activated" : "deactivated"))
                        .setApiKey(API_KEY)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }

            alarmActive.set(activate);

            ToggleAlarmResponse response = (ToggleAlarmResponse) ToggleAlarmResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Alarm " + (activate ? "activated" : "deactivated"))
                    .setApiKey(API_KEY)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new RuntimeException("Error toggling alarm: " + e.getMessage()));
        }
    }

    @Override
    public StreamObserver<SecurityEvent> monitorAlarmStatus(AlarmStatusRequest request, StreamObserver<AlarmStatusResponse> responseObserver) {
        final String API_KEY = "M0n1t0rK3y" + generateApiKey().substring(0,3);
        Thread thread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String status = alarmActive.get() ? "ACTIVE" : "INACTIVE";

                    AlarmStatusResponse response = (AlarmStatusResponse) AlarmStatusResponse.newBuilder()
                            .setTimestamp(Instant.now().toString())
                            .setStatus(status)
                            .setApiKey(API_KEY)
                            .build();

                    responseObserver.onNext(response);
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Monitoring was cancelled by client.");
                responseObserver.onCompleted();
            } catch (Exception ex) {
                System.err.println("Unexpected error in monitoring: " + ex.getMessage());
                responseObserver.onError(ex);
            }
        });

        thread.start();
        return null;
    }

    @Override
    public StreamObserver<SecurityEvent> liveSecurityFeed(StreamObserver<SecurityAlert> responseObserver) {

        return new StreamObserver<SecurityEvent>() {
            @Override
            public void onNext(SecurityEvent event) {
                try {
                    if (event.getDetails().isEmpty() || event.getEventType().isEmpty()) {
                        throw new IllegalArgumentException("Missing event details or type.");
                    }

                    String alertLevel = event.getEventType().equalsIgnoreCase("movement") ? "HIGH" : "LOW" ;

                    SecurityAlert alert = SecurityAlert.newBuilder()
                            .setAlertLevel(alertLevel)
                            .setMessage(event.getDetails())
                            .build();

                    responseObserver.onNext(alert);
                } catch (Exception e) {
                    System.err.println("Error while processing security event: " + e.getMessage());
                    responseObserver.onError(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Stream error from client: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Live security feed completed.");
                responseObserver.onCompleted();
            }
        };
    }
}