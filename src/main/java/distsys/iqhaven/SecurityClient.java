/*
 * This is a client-side implementation of a gRPC security service.
 * It communicates with the SecurityServer to perform various actions like toggling the alarm, monitoring its status,
 * and receiving a live feed of security events.
 * 
 * Author: dcmed
 */

package distsys.iqhaven;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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
 * This class demonstrates a gRPC client that interacts with the SecurityServer service.
 * It includes examples of Simple RPC, Server Streaming, and Bidirectional Streaming.
 */
public class SecurityClient {
    public static void main(String[] args) throws Exception {
        // Create a managed channel for communication with the server at localhost:50051
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()  // Use plaintext communication (no encryption)
                .build();

        // Create blocking and asynchronous stubs to communicate with the server
        SecurityServiceGrpc.SecurityServiceBlockingStub blockingStub = SecurityServiceGrpc.newBlockingStub(channel);
        SecurityServiceGrpc.SecurityServiceStub asyncStub = SecurityServiceGrpc.newStub(channel);

        // 1. Simple RPC
        // Create a request to toggle the alarm (set to true to activate it)
        ToggleAlarmRequest toggleRequest = ToggleAlarmRequest.newBuilder().setActivate(true).build();
        // Make the RPC call to toggle the alarm and get the response
        ToggleAlarmResponse toggleResponse = blockingStub.toggleAlarm(toggleRequest);
        System.out.println("Toggle Response: " + toggleResponse.getMessage());

        // 2. Server Streaming
        // Create a request to monitor the alarm status
        AlarmStatusRequest statusRequest = AlarmStatusRequest.newBuilder().build();
        // Make the RPC call and get an iterator for the server's responses (streaming)
        Iterator<AlarmStatusResponse> responses = blockingStub.monitorAlarmStatus(statusRequest);

        // Start a new thread to handle the server's streaming responses
        new Thread(() -> {
            int count = 0;
            // Receive and print up to 3 responses from the server
            while (responses.hasNext() && count++ < 3) {
                AlarmStatusResponse res = responses.next();
                System.out.println("Alarm Status: " + res.getTimestamp() + " - " + res.getStatus());
            }
        }).start();

        // 3. Bidirectional Streaming
        // CountDownLatch is used to wait for the streaming to complete
        CountDownLatch latch = new CountDownLatch(1);

        // Create a stream observer for the live security feed
        StreamObserver<SecurityEvent> eventStream = asyncStub.liveSecurityFeed(new StreamObserver<SecurityAlert>() {
            @Override
            public void onNext(SecurityAlert value) {
                // Handle each incoming security alert
                System.out.println("Received Alert: " + value.getAlertLevel() + " - " + value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                // Handle errors in the stream
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                // Indicate that the stream has ended
                System.out.println("Security feed ended.");
                latch.countDown();
            }
        });

        // Simulate sending security events to the server
        eventStream.onNext(SecurityEvent.newBuilder().setEventType("movement").setDetails("Living Room").build());
        eventStream.onNext(SecurityEvent.newBuilder().setEventType("door").setDetails("Front Door Opened").build());

        // Wait for 2 seconds before completing the stream
        Thread.sleep(2000);
        eventStream.onCompleted();  // Close the stream

        // Wait for the latch to count down, ensuring all responses are received
        latch.await(5, TimeUnit.SECONDS);

        // Shut down the channel to clean up resources
        channel.shutdown();
    }
}
