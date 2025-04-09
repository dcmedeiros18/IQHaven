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
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * 
 *@author dcmed
 */

public class EnergyClient {

    // Setting up the Logger to track messages throughout the process
    private static final Logger logger = Logger.getLogger(EnergyClient.class.getName());

    public static void main(String[] args) throws InterruptedException {
        // Logging the initialization of the energy client to track execution
        logger.info("Starting the energy client...");

        // Setting up the communication channel to the server (gRPC connection)
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                // Using plaintext communication (no TLS)
                .usePlaintext()  
                .build();

        // Creating the stub (client) to interact with the remote energy service
        EnergyServiceGrpc.EnergyServiceStub stub = EnergyServiceGrpc.newStub(channel);

        // Creating and configuring the request for energy optimization via Unary RPC
        OptimizeEnergyRequest request = OptimizeEnergyRequest.newBuilder()
                // The ID of the device requiring optimization
                .setDeviceId("device123") 
                // Suggestion for energy optimization
                .setSuggestion("Turn off lights when not in use")  
                .build();

        // Logging the sending of the optimization request
        logger.info("Sending energy optimization request...");

        // Calling the energy optimization service
        stub.optimizeEnergy(request, new StreamObserver<OptimizeEnergyResponse>() {
            @Override
            public void onNext(OptimizeEnergyResponse response) {
                // Logging the server's response with the optimization result
                logger.info("Optimization result: " + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                // Logging an error if one occurs during the optimization call
                logger.log(Level.SEVERE, "Error in energy optimization", t);
            }

            @Override
            public void onCompleted() {
                // Logging the completion of the RPC call
                logger.info("Energy optimization completed.");
            }
        });

        // Creating a request for continuous energy usage streaming (Server Streaming RPC)
        StreamEnergyUsageRequest usageRequest = StreamEnergyUsageRequest.newBuilder()
                // The device ID for monitoring energy usage
                .setDeviceId("device123")  
                .build();

        // Logging the start of energy usage streaming
        logger.info("Starting energy usage streaming...");

        // Performing the call for the energy usage streaming service
        stub.streamEnergyUsage(usageRequest, new StreamObserver<EnergyUsageResponse>() {
            @Override
            public void onNext(EnergyUsageResponse response) {
                // Logging the server's response for energy usage, including timestamp
                logger.info("Energy usage: " + response.getUsage() + " at " + response.getTimestamp());
            }

            @Override
            public void onError(Throwable t) {
                // Logging an error if one occurs during the streaming of data
                logger.log(Level.SEVERE, "Error in energy usage streaming", t);
            }

            @Override
            public void onCompleted() {
                // Logging the completion of the streaming
                logger.info("Energy streaming completed.");
            }
        });

        // Waiting to ensure the client remains active until the communications finish
        // This ensures that the client does not terminate before receiving server responses
        Thread.sleep(5000);

        // Closing the communication channel once the process is finished
        logger.info("Closing the communication channel...");
        channel.shutdown();
    }
}
