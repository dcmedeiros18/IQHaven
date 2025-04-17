package automation;

import com.google.protobuf.GeneratedMessage;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import com.google.protobuf.Timestamp;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import automation.Energy.*;

/**
 * This class implements the gRPC service for handling energy-related operations.
 * It provides streaming of energy usage data and the ability to optimize energy for devices.
 * @author dcmed
 */
public class EnergyServiceImpl extends EnergyServiceGrpc.EnergyServiceImplBase {
    private static final Logger logger = Logger.getLogger(EnergyServiceImpl.class.getName());

    // API key used for authentication
    private final String API_KEY = "A7xL9mB2YzW0pTqE5vN6CdJsRuKfHgXoPiM1Q4ZlDbtV3nScAy";

    /**
     * Streams energy usage data for a given device over time.
     * Simulates energy usage readings at 1-second intervals.
     */
    @Override
    public void streamEnergyUsage(StreamEnergyUsageRequest request, StreamObserver<EnergyUsageResponse> responseObserver) {

        // Validate API key
        if (request.getApiKey().isEmpty() || !request.getApiKey().equals(API_KEY)) {
            responseObserver.onError(Status.PERMISSION_DENIED
                    .withDescription("API KEY is not valid.")
                    .asRuntimeException());
            return;
        }

        String deviceId = request.getDeviceId();
        logger.info("Started streaming energy usage for device: " + deviceId);

        try {
            // Send 5 simulated energy usage readings
            for (int i = 0; i < 5; i++) {
                long currentMillis = System.currentTimeMillis();
                Timestamp timestamp = Timestamp.newBuilder()
                        .setSeconds(TimeUnit.MILLISECONDS.toSeconds(currentMillis))
                        .setNanos((int)(currentMillis % 1000) * 1000000)
                        .build();

                EnergyUsageResponse response = EnergyUsageResponse.newBuilder()
                        .setUsage(i * 10) // Simulated usage value
                        .setTimestamp(String.valueOf(timestamp)) // Timestamp of the reading
                        .build();

                responseObserver.onNext(response);
                Thread.sleep(1000); // Wait 1 second between readings
            }

            responseObserver.onCompleted();
            logger.info("Energy usage streaming completed successfully.");

        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Energy usage streaming interrupted", e);
            responseObserver.onError(new StatusRuntimeException(
                    Status.CANCELLED.withDescription("Streaming canceled by client").withCause(e)));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during energy usage streaming", e);
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL.withDescription("Error during streaming").withCause(e)));
        }
    }

    /**
     * Performs an energy optimization routine for a specific device.
     * Sends a success response with a message.
     */
    @Override
    public void optimizeEnergy(OptimizeEnergyRequest request, StreamObserver<OptimizeEnergyResponse> responseObserver) {

        try {
            logger.info("OptimizeEnergy called for device: " + request.getDeviceId());

            OptimizeEnergyResponse response = OptimizeEnergyResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Energy optimization completed successfully for device " + request.getDeviceId())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("Energy optimization completed successfully.");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during energy optimization", e);
            responseObserver.onError(new StatusRuntimeException(
                    Status.INTERNAL.withDescription("Error during optimization").withCause(e)));
        }
    }
}
