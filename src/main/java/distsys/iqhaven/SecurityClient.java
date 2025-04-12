package distsys.iqhaven;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import security.Security.AlarmStatusRequest;
import security.Security.AlarmStatusResponse;
import security.Security.SecurityAlert;
import security.Security.SecurityEvent;
import security.Security.ToggleAlarmRequest;
import security.Security.ToggleAlarmResponse;
import security.SecurityServiceGrpc;

/**
 * This class is a gRPC client that talks to the SecurityServer.
 * It sends requests to toggle the alarm, receive alarm status updates,
 * and stream security events like motion detection.
 *
 * Author: dcmed (edited)
 */
public class SecurityClient {

    public static void main(String[] args) throws Exception {
        // Create a communication channel to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // We are not using encryption here
                .build();

        // Blocking stub for unary and server streaming calls
        SecurityServiceGrpc.SecurityServiceBlockingStub blockingStub = SecurityServiceGrpc.newBlockingStub(channel);

        // Async stub for bidirectional streaming
        SecurityServiceGrpc.SecurityServiceStub asyncStub = SecurityServiceGrpc.newStub(channel);

        // === 1. UNARY CALL - Toggle Alarm ===
        try {
            ToggleAlarmRequest toggleRequest = ToggleAlarmRequest.newBuilder().setActivate(true).build();
            ToggleAlarmResponse toggleResponse = blockingStub.toggleAlarm(toggleRequest);
            System.out.println("Toggle Response: " + toggleResponse.getMessage());
        } catch (StatusRuntimeException e) {
            System.err.println("Error toggling alarm: " + e.getStatus().getDescription());
        }

        // === 2. SERVER STREAMING - Monitor Alarm Status ===
        try {
            AlarmStatusRequest statusRequest = AlarmStatusRequest.newBuilder().build();
            Iterator<AlarmStatusResponse> responses = blockingStub.monitorAlarmStatus(statusRequest);

            // We'll only listen to 3 updates, then "simulate" a cancel
            new Thread(() -> {
                int count = 0;
                try {
                    while (responses.hasNext() && count++ < 3) {
                        AlarmStatusResponse res = responses.next();
                        System.out.println("Alarm Status: " + res.getTimestamp() + " - " + res.getStatus());
                    }
                    // Simulate canceling by breaking out after a few messages
                    System.out.println("Stopping alarm monitoring stream...");
                } catch (StatusRuntimeException e) {
                    System.err.println("Error receiving alarm status: " + e.getStatus().getDescription());
                }
            }).start();
        } catch (Exception e) {
            System.err.println("Streaming failed: " + e.getMessage());
        }

        // === 3. BIDIRECTIONAL STREAMING - Live Security Feed ===
        CountDownLatch latch = new CountDownLatch(1);

        // This observer handles the incoming security alerts from the server
        StreamObserver<SecurityAlert> alertObserver = new StreamObserver<SecurityAlert>() {
            @Override
            public void onNext(SecurityAlert alert) {
                System.out.println("Received Alert: " + alert.getAlertLevel() + " - " + alert.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error in security feed: " + t.getMessage());
                latch.countDown(); // Signal that we're done
            }

            @Override
            public void onCompleted() {
                System.out.println("Security feed ended.");
                latch.countDown(); // Signal that we're done
            }
        };

        // Create a stream to send events to the server
        StreamObserver<SecurityEvent> eventStream = asyncStub.liveSecurityFeed(alertObserver);

        try {
            // Send some test security events to the server
            eventStream.onNext(SecurityEvent.newBuilder().setEventType("movement").setDetails("Living Room").build());
            eventStream.onNext(SecurityEvent.newBuilder().setEventType("door").setDetails("Front Door Opened").build());

            // Wait a bit and finish
            Thread.sleep(2000);
            eventStream.onCompleted(); // Tell the server we're done sending
        } catch (Exception e) {
            eventStream.onError(e); // Send error if something went wrong
        }

        // Wait for the response from the server to complete
        latch.await(5, TimeUnit.SECONDS);

        // Close the communication channel
        channel.shutdown();
    }
}
