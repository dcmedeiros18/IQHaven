/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package automation;


import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import com.google.protobuf.Timestamp;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import automation.Energy.EnergyUsageResponse;
import automation.Energy.StreamEnergyUsageRequest;
import automation.EnergyServiceGrpc;

public class EnergyServiceImpl extends EnergyServiceGrpc.EnergyServiceImplBase {

    private static final Logger logger = Logger.getLogger(EnergyServiceImpl.class.getName());

    @Override
    public void streamEnergyUsage(StreamEnergyUsageRequest request, StreamObserver<EnergyUsageResponse> responseObserver) {
        String deviceId = request.getDeviceId();
        logger.info("Started streaming energy usage for device: " + deviceId);

        // Simulating continuous data stream (Server Streaming)
        try {
            // Stream energy usage data for a period of time or until the client cancels
            for (int i = 0; i < 5; i++) {
                // Convert current time to Timestamp
                long currentMillis = System.currentTimeMillis();
                Timestamp timestamp = Timestamp.newBuilder()
                        .setSeconds(TimeUnit.MILLISECONDS.toSeconds(currentMillis))
                        .setNanos((int)(currentMillis % 1000) * 1000000)  // Milliseconds to nanos
                        .build();

                // Create the EnergyUsageResponse with simulated data and the timestamp
                EnergyUsageResponse response = EnergyUsageResponse.newBuilder()
                        .setUsage(i * 10)  // Example: simulated usage data
                        .setTimestamp(String.valueOf(timestamp))  // Set the Timestamp object
                        .build();

                // Send the response to the client
                responseObserver.onNext(response);

                // Simulate a small delay before sending the next data point
                Thread.sleep(1000);
            }

            // Complete the streaming response
            responseObserver.onCompleted();
            logger.info("Energy usage streaming completed successfully.");

        } catch (InterruptedException e) {
            // Handle thread interruption (client canceled the streaming)
            logger.log(Level.SEVERE, "Energy usage streaming interrupted", e);
            responseObserver.onError(new StatusRuntimeException(Status.CANCELLED.withDescription("Streaming canceled by client").withCause(e)));
        } catch (Exception e) {
            // Handle any error that occurred during streaming
            logger.log(Level.SEVERE, "Error during energy usage streaming", e);
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Error during streaming").withCause(e)));
        }
    }
}