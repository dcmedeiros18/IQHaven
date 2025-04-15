package automation;

import io.grpc.stub.StreamObserver;
import automation.Energy.*;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class EnergyServiceImpl extends EnergyServiceGrpc.EnergyServiceImplBase {

    @Override
    public void optimizeEnergy(OptimizeEnergyRequest request,
                               StreamObserver<OptimizeEnergyResponse> responseObserver) {
        String deviceId = request.getDeviceId();
        String suggestion = request.getSuggestion();

        // Lógica de otimização simulada
        String optimizationResult = "Optimized " + deviceId + " using " + suggestion;

        OptimizeEnergyResponse response = OptimizeEnergyResponse.newBuilder()
                .setSuccess(true)
                .setMessage(optimizationResult)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void streamEnergyUsage(StreamEnergyUsageRequest request,
                                  StreamObserver<EnergyUsageResponse> responseObserver) {
        String deviceId = request.getDeviceId();

        try {
            // Simula o envio de 5 leituras com intervalo de 1 segundo
            for (int i = 1; i <= 5; i++) {
                double usage = 1.0 + (Math.random() * 5.0); // Valores entre 1.0 e 6.0 kWh

                EnergyUsageResponse response = EnergyUsageResponse.newBuilder()
                        .setUsage(usage)
                        .setTimestamp(Instant.now().toString())
                        .build();

                responseObserver.onNext(response);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Stream interrupted: " + e.getMessage());
            responseObserver.onError(e);
            return;
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<EnergyData> sendEnergyData(
            StreamObserver<EnergyDataSummaryResponse> responseObserver) {

        AtomicInteger dataPoints = new AtomicInteger(0);
        double[] totalConsumption = new double[1]; // Array para simular referência mutável

        return new StreamObserver<EnergyData>() {
            @Override
            public void onNext(EnergyData data) {
                int count = dataPoints.incrementAndGet();
                double consumption = data.getEnergyConsumption();
                totalConsumption[0] += consumption;

                System.out.printf("Received data point #%d: %.2f kWh from %s%n",
                        count, consumption, data.getDeviceId());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error in energy data stream: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                int points = dataPoints.get();
                double average = points > 0 ? totalConsumption[0] / points : 0.0;

                EnergyDataSummaryResponse.Builder builder = EnergyDataSummaryResponse.newBuilder()
                        .setSuccess(true)
                        .setDataPointsReceived(points);

                // Verifica se o campo average_consumption existe no protobuf
                try {
                    builder.getClass().getMethod("setAverageConsumption", double.class);
                    builder.setAverageConsumption(average);
                } catch (NoSuchMethodException e) {
                    System.out.println("Average consumption field not available in proto");
                }

                responseObserver.onNext(builder.build());
                responseObserver.onCompleted();

                System.out.printf("Completed. Total points: %d, Avg: %.2f kWh%n", points, average);
            }
        };
    }

    @Override
    public StreamObserver<EnergyUpdateRequest> monitorEnergy(
            StreamObserver<EnergyUpdateResponse> responseObserver) {

        return new StreamObserver<EnergyUpdateRequest>() {
            @Override
            public void onNext(EnergyUpdateRequest request) {
                String deviceId = request.getDeviceId();
                String action = request.getAction();

                // Simula processamento e retorna confirmação
                String status = "Processed " + action + " for " + deviceId;

                EnergyUpdateResponse response = EnergyUpdateResponse.newBuilder()
                        .setDeviceId(deviceId)
                        .setStatus(status)
                        .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error in monitorEnergy: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("MonitorEnergy stream completed by client");
                responseObserver.onCompleted();
            }
        };
    }
}