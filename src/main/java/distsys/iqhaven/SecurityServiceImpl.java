/*
 * This class implements a gRPC service for a security system. It provides methods for toggling the alarm state,
 * monitoring the alarm status, and streaming security events in real-time.
 */

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
 * Implementation of the SecurityServiceGrpc.SecurityServiceImplBase class. This class provides methods to control
 * and monitor the security alarm system, including toggling the alarm, monitoring its status, and providing a live
 * feed of security event alerts.
 * 
 * Author: dcmed
 */
public class SecurityServiceImpl extends SecurityServiceGrpc.SecurityServiceImplBase {

    // AtomicBoolean to safely manage the alarm state in a multi-threaded environment
    private final AtomicBoolean alarmActive = new AtomicBoolean(false);

    /**
     * Toggles the alarm state based on the request.
     * If the request is to activate the alarm, it sets the state to true, otherwise false.
     * It sends a response back to the client indicating the success and current state of the alarm.
     * 
     * @param request The request containing the desired state of the alarm.
     * @param responseObserver The StreamObserver used to send the response back to the client.
     */
    @Override
    public void toggleAlarm(ToggleAlarmRequest request, StreamObserver<ToggleAlarmResponse> responseObserver) {
        // Get the requested alarm state (true for activation, false for deactivation)
        boolean activate = request.getActivate();
        alarmActive.set(activate);  // Set the alarm state to the requested value

        // Create the response to be sent back to the client
        ToggleAlarmResponse response = ToggleAlarmResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Alarm " + (activate ? "activated" : "deactivated"))  // Success message with current state
                .build();

        // Send the response to the client
        responseObserver.onNext(response);
        responseObserver.onCompleted();  // Mark the RPC as completed
    }

    /**
     * Monitors the status of the alarm system, sending updates every 3 seconds.
     * The status of the alarm is either "ACTIVE" or "INACTIVE", and it also includes a timestamp.
     * 
     * @param request The request (unused here, but required by gRPC service method signature).
     * @param responseObserver The StreamObserver used to send alarm status updates to the client.
     */
    @Override
    public void monitorAlarmStatus(AlarmStatusRequest request, StreamObserver<AlarmStatusResponse> responseObserver) {
        // Start a new thread to periodically monitor and send the alarm status
        new Thread(() -> {
            try {
                while (true) {
                    // Check the current alarm status (either ACTIVE or INACTIVE)
                    String status = alarmActive.get() ? "ACTIVE" : "INACTIVE";

                    // Build the response with the current status and a timestamp
                    AlarmStatusResponse response = AlarmStatusResponse.newBuilder()
                            .setTimestamp(Instant.now().toString())  // Add current timestamp
                            .setStatus(status)  // Add the current alarm status
                            .build();

                    // Send the status update to the client
                    responseObserver.onNext(response);
                    Thread.sleep(3000);  // Wait for 3 seconds before sending the next update
                }
            } catch (InterruptedException e) {
                responseObserver.onError(e);  // Handle any interruption errors
            }
        }).start();  // Start the monitoring thread
    }

    /**
     * Provides a live feed of security events, sending an alert based on the event details.
     * The alert's level is determined by the type of event (e.g., "movement" triggers a HIGH alert).
     * 
     * @param responseObserver The StreamObserver used to send SecurityAlert messages to the client.
     * @return A StreamObserver to handle incoming SecurityEvent messages from the client.
     */
    @Override
    public StreamObserver<SecurityEvent> liveSecurityFeed(StreamObserver<SecurityAlert> responseObserver) {
        // Return a new StreamObserver to handle incoming security events
        return new StreamObserver<SecurityEvent>() {
            @Override
            public void onNext(SecurityEvent event) {
                // Determine the alert level based on the event type (movement is HIGH, others are LOW)
                String alertLevel = event.getEventType().equalsIgnoreCase("movement") ? "HIGH" : "LOW";
                
                // Build the security alert message with event details and alert level
                SecurityAlert alert = SecurityAlert.newBuilder()
                        .setAlertLevel(alertLevel)  // Set the alert level
                        .setMessage("Detected: " + event.getDetails())  // Add event details to the message
                        .build();

                // Send the alert to the client
                responseObserver.onNext(alert);
            }

            @Override
            public void onError(Throwable t) {
                // Log any errors that occur during the stream
                System.err.println("Error in stream: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                // Indicate that the stream has completed
                responseObserver.onCompleted();
            }
        };
    }
}
