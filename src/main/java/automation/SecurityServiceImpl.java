package automation;

import io.grpc.stub.StreamObserver;
import automation.Security.*;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class SecurityServiceImpl extends SecurityServiceGrpc.SecurityServiceImplBase {

    @Override
    public void toggleAlarm(ToggleAlarmRequest request,
                            StreamObserver<ToggleAlarmResponse> responseObserver) {
        boolean activate = request.getActivate();
        String status = activate ? "activated" : "deactivated";

        ToggleAlarmResponse response = ToggleAlarmResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Alarm system " + status + " successfully")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void monitorAlarmStatus(AlarmStatusRequest request,
                                   StreamObserver<AlarmStatusResponse> responseObserver) {
        try {
            // Simula o envio de 5 status com intervalo de 1 segundo
            for (int i = 1; i <= 5; i++) {
                String status = (i % 2 == 0) ? "NORMAL" : "ALERT";

                AlarmStatusResponse response = AlarmStatusResponse.newBuilder()
                        .setStatus(status)
                        .setTimestamp(Instant.now().toString())
                        .build();

                responseObserver.onNext(response);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Status monitoring interrupted: " + e.getMessage());
            responseObserver.onError(e);
            return;
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<SecurityEvent> liveSecurityFeed(
            StreamObserver<SecurityAlert> responseObserver) {

        AtomicInteger eventCounter = new AtomicInteger(0);

        return new StreamObserver<SecurityEvent>() {
            @Override
            public void onNext(SecurityEvent event) {
                int count = eventCounter.incrementAndGet();
                String eventType = event.getEventType();
                String details = event.getDetails();

                System.out.printf("Processing security event #%d: %s - %s%n",
                        count, eventType, details);

                // Determina o nível de alerta baseado no tipo de evento
                String alertLevel = eventType.equals("INTRUSION") ? "CRITICAL" : "WARNING";

                SecurityAlert alert = SecurityAlert.newBuilder()
                        .setAlertLevel(alertLevel)
                        .setMessage("Processed event #" + count + ": " + details)
                        .build();

                responseObserver.onNext(alert);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error in security feed: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Security feed closed by client");
                responseObserver.onCompleted();
            }
        };
    }

}