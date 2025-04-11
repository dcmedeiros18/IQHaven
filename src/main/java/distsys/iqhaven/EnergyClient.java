/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package distsys.iqhaven;

import energy.Energy.EnergyUsageResponse;
import energy.Energy.OptimizeEnergyRequest;
import energy.Energy.OptimizeEnergyResponse;
import energy.Energy.StreamEnergyUsageRequest;
import energy.EnergyServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EnergyClient - gRPC client to interact with the EnergyService.
 * Demonstrates Unary and Server Streaming RPCs for energy optimization and monitoring.
 * Implements error handling and message cancellation for robustness.
 * 
 * Author: dcmed
 */
public class EnergyClient {

    // Logger to track application activity
    private static final Logger logger = Logger.getLogger(EnergyClient.class.getName());

    public static void main(String[] args) throws InterruptedException {
        logger.info("Starting the energy client...");

        // 1. Set up the gRPC channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()  // No TLS for development/testing
                .build();

        // 2. Create a non-blocking stub for asynchronous communication
        EnergyServiceGrpc.EnergyServiceStub stub = EnergyServiceGrpc.newStub(channel);

        // === Unary RPC: Send optimization suggestion ===
        OptimizeEnergyRequest request = OptimizeEnergyRequest.newBuilder()
                .setDeviceId("device123")
                .setSuggestion("Turn off lights when not in use")
                .build();

        logger.info("Sending energy optimization request...");

        // Unary request with error handling
        stub.optimizeEnergy(request, new StreamObserver<OptimizeEnergyResponse>() {
            @Override
            public void onNext(OptimizeEnergyResponse response) {
                logger.info("[Unary] Optimization result: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                if (t instanceof StatusRuntimeException) {
                    logger.log(Level.SEVERE, "[Unary] gRPC Error: " + ((StatusRuntimeException) t).getStatus().getDescription());
                } else {
                    logger.log(Level.SEVERE, "[Unary] Error in energy optimization", t);
                }
            }

            @Override
            public void onCompleted() {
                logger.info("[Unary] Energy optimization completed.");
            }
        });

        // === Server Streaming RPC: Listen for continuous energy usage updates ===
        StreamEnergyUsageRequest usageRequest = StreamEnergyUsageRequest.newBuilder()
                .setDeviceId("device123")
                .build();

        logger.info("Starting energy usage streaming...");

        // Server streaming request with cancel logic
        StreamObserver<EnergyUsageResponse> responseObserver = new StreamObserver<EnergyUsageResponse>() {
            @Override
            public void onNext(EnergyUsageResponse response) {
                logger.info("[Streaming] Energy usage: " + response.getUsage() + " at " + response.getTimestamp());
            }

            @Override
            public void onError(Throwable t) {
                if (t instanceof StatusRuntimeException) {
                    logger.log(Level.SEVERE, "[Streaming] gRPC Error: " + ((StatusRuntimeException) t).getStatus().getDescription());
                } else {
                    logger.log(Level.SEVERE, "[Streaming] Error in energy usage streaming", t);
                }
            }

            @Override
            public void onCompleted() {
                logger.info("[Streaming] Energy streaming completed.");
            }
        };

        // Simulate cancellation after a timeout
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Simulate waiting for some time before cancellation
                logger.info("[Streaming] Cancelling the streaming...");
                responseObserver.onCompleted(); // Cancel the streaming by completing the observer
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "[Streaming] Error in cancellation", e);
            }
        }).start();

        // Perform the streaming RPC call
        stub.streamEnergyUsage(usageRequest, responseObserver);

        // Wait to receive responses before shutting down
        Thread.sleep(5000);  // Ensures we wait for responses before closing

        // === Cleanup ===
        logger.info("Closing the communication channel...");
        channel.shutdown();
    }
}
