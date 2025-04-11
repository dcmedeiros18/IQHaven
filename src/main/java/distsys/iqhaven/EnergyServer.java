/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package distsys.iqhaven;

import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import energy.Energy.EnergyUsageResponse;
import energy.Energy.StreamEnergyUsageRequest;
import energy.EnergyServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnergyServer {

    private static final Logger logger = Logger.getLogger(EnergyServer.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        Server server = ServerBuilder.forPort(port)
                .addService(new EnergyServiceImpl())
                .build();

        logger.info("gRPC server started on port " + port);
        server.start();
        server.awaitTermination();
    }

    static class EnergyServiceImpl extends EnergyServiceGrpc.EnergyServiceImplBase {

        @Override
        public void streamEnergyUsage(StreamEnergyUsageRequest request, StreamObserver<EnergyUsageResponse> responseObserver) {
            String deviceId = request.getDeviceId();
            logger.info("Started streaming energy usage for device: " + deviceId);

            try {
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
                            .setTimestamp(timestamp)  // Set the Timestamp object
                            .build();

                    responseObserver.onNext(response);

                    // Simulate a delay
                    Thread.sleep(1000);
                }

                responseObserver.onCompleted();
                logger.info("Energy usage streaming completed successfully.");

            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Energy usage streaming interrupted", e);
                responseObserver.onError(new StatusRuntimeException(Status.CANCELLED.withDescription("Streaming canceled by client").withCause(e)));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error during energy usage streaming", e);
                responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Error during streaming").withCause(e)));
            }
        }
    }
}
