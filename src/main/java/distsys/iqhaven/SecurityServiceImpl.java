package distsys.iqhaven;

import io.grpc.stub.StreamObserver;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import security.Security.AlarmStatusRequest;
import security.Security.AlarmStatusResponse;
import security.Security.SecurityAlert;
import security.Security.SecurityEvent;
import security.Security.ToggleAlarmRequest;
import security.Security.ToggleAlarmResponse;
import security.SecurityServiceGrpc;

/**
 * This class is the implementation of the gRPC Security Service.
 * It lets the client turn the alarm on or off, check the alarm status,
 * and send live security events like movement detection.
 *
 * Author: dcmed
 */
public class SecurityServiceImpl extends SecurityServiceGrpc.SecurityServiceImplBase {

    // This variable keeps track of whether the alarm is ON (true) or OFF (false)
    private final AtomicBoolean alarmActive = new AtomicBoolean(false);

    // This method lets the client turn the alarm on or off
    @Override
    public void toggleAlarm(ToggleAlarmRequest request, StreamObserver<ToggleAlarmResponse> responseObserver) {
        try {
            boolean activate = request.getActivate(); // true = activate, false = deactivate

            // If the alarm is already in the requested state, we tell the client
            if (alarmActive.get() == activate) {
                ToggleAlarmResponse response = ToggleAlarmResponse.newBuilder()
                        .setSuccess(false)
                        .setMessage("Alarm is already " + (activate ? "activated" : "deactivated"))
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }

            // Set the alarm to the requested state
            alarmActive.set(activate);

            // Create a message to let the client know it worked
            ToggleAlarmResponse response = ToggleAlarmResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Alarm " + (activate ? "activated" : "deactivated"))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // If there is an error, we tell the client
            responseObserver.onError(new RuntimeException("Error toggling alarm: " + e.getMessage()));
        }
    }

    // This method sends updates about the alarm status every 3 seconds
    @Override
    public void monitorAlarmStatus(AlarmStatusRequest request, StreamObserver<AlarmStatusResponse> responseObserver) {
        // We use a new thread to keep sending updates without blocking
        Thread thread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String status = alarmActive.get() ? "ACTIVE" : "INACTIVE";

                    AlarmStatusResponse response = AlarmStatusResponse.newBuilder()
                            .setTimestamp(Instant.now().toString()) // add current time
                            .setStatus(status)
                            .build();

                    responseObserver.onNext(response); // send status to client
                    Thread.sleep(3000); // wait 3 seconds before sending again
                }
            } catch (InterruptedException e) {
                // If the thread is interrupted (for example, client cancels), we stop
                Thread.currentThread().interrupt();
                System.err.println("Monitoring was cancelled by client.");
                responseObserver.onCompleted(); // end the stream
            } catch (Exception ex) {
                // If something else goes wrong, we tell the client
                System.err.println("Unexpected error in monitoring: " + ex.getMessage());
                responseObserver.onError(ex);
            }
        });

        thread.start(); // Start the thread to send updates
    }

    // This method lets the client send security events (like "movement")
    // and the server will respond with alerts
    @Override
    public StreamObserver<SecurityEvent> liveSecurityFeed(StreamObserver<SecurityAlert> responseObserver) {
        return new StreamObserver<SecurityEvent>() {
            @Override
            public void onNext(SecurityEvent event) {
                try {
                    // Check if the event is missing required information
                    if (event.getDetails().isEmpty() || event.getEventType().isEmpty()) {
                        throw new IllegalArgumentException("Missing event details or type.");
                    }

                    // Decide alert level: HIGH if movement, LOW otherwise
                    String alertLevel = event.getEventType().equalsIgnoreCase("movement") ? "HIGH" : "LOW";

                    // Create a security alert message
                    SecurityAlert alert = SecurityAlert.newBuilder()
                            .setAlertLevel(alertLevel)
                            .setMessage("Detected: " + event.getDetails())
                            .build();

                    responseObserver.onNext(alert); // Send alert to client
                } catch (Exception e) {
                    // If something goes wrong, send an error
                    System.err.println("Error while processing security event: " + e.getMessage());
                    responseObserver.onError(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                // If the stream has an error, print it
                System.err.println("Stream error from client: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                // If the client finishes, we also complete
                System.out.println("Live security feed completed.");
                responseObserver.onCompleted();
            }
        };
    }
}
