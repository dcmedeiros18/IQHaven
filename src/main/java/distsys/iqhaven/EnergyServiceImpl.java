/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package distsys.iqhaven;

import energy.Energy.EnergyData;
import energy.Energy.EnergyDataSummaryResponse;
import energy.Energy.EnergyUpdateRequest;
import energy.Energy.EnergyUpdateResponse;
import energy.Energy.EnergyUsageResponse;
import energy.Energy.OptimizeEnergyRequest;
import energy.Energy.OptimizeEnergyResponse;
import energy.Energy.StreamEnergyUsageRequest;
import energy.EnergyServiceGrpc;
import io.grpc.stub.StreamObserver;

/*
 * 
 *@author dcmed
 */

public class EnergyServiceImpl extends EnergyServiceGrpc.EnergyServiceImplBase {

    // Unary RPC - Simple energy optimization request
    // This method is called when the client requests energy optimization advice.
    public void optimizeEnergy(OptimizeEnergyRequest request, StreamObserver<OptimizeEnergyResponse> responseObserver) {
        // Example logic to provide energy optimization advice
        String suggestion = "Consider using the washing machine or dryer during off-peak hours to save money.";
        
        // Creating the response to send back to the client with the optimization message
        OptimizeEnergyResponse response = OptimizeEnergyResponse.newBuilder()
                .setSuccess(true)  // Indicating that the optimization was successful
                .setMessage(suggestion)  // The energy-saving suggestion
                .build();
        
        // Sending the response back to the client
        responseObserver.onNext(response);
        responseObserver.onCompleted();  // Completing the response
    }

    // Server Streaming RPC - Continuously sends energy usage data
    // This method streams energy usage information back to the client over multiple messages.
    @Override
    public void streamEnergyUsage(StreamEnergyUsageRequest request, StreamObserver<EnergyUsageResponse> responseObserver) {
        // Simulating the continuous sending of energy usage data
        for (int i = 0; i < 5; i++) {
            // Creating a response with simulated energy usage data and a timestamp
            EnergyUsageResponse response = EnergyUsageResponse.newBuilder()
                    // Simulated energy usage
                    .setUsage(20.0 + i * 2)  
                    // Simulated timestamp
                    .setTimestamp("2025-04-09T12:00:0" + i + "Z")  
                    .build();
            // Sending the energy usage data to the client
            responseObserver.onNext(response);

            try {
                // Introducing a delay to simulate the continuous streaming of data
                // Sleep for 2 second
                Thread.sleep(2000);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Completing the streaming process after all responses have been sent
        responseObserver.onCompleted();
    }

    // Client Streaming RPC - Receives energy data from the client and sends back a summary
    // This method receives a stream of energy data from the client, processes it, and sends a summary response.
    @Override
    public StreamObserver<EnergyData> sendEnergyData(StreamObserver<EnergyDataSummaryResponse> responseObserver) {
        // Returning a StreamObserver to handle the incoming energy data
        return new StreamObserver<EnergyData>() {
            // Variable to keep track of the number of data points received
            int dataPoints = 0;

            @Override
            public void onNext(EnergyData value) {
                // Processing each energy data point received from the client
                dataPoints++;
                // Logging the received energy data (for debugging purposes)
                System.out.println("Received energy data: Device ID - " + value.getDeviceId() + " Consumption - " + value.getEnergyConsumption());
            }

            @Override
            public void onError(Throwable t) {
                // Handling errors that may occur during the data stream
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                // Creating and sending a summary response with the count of received data points
                EnergyDataSummaryResponse summaryResponse = EnergyDataSummaryResponse.newBuilder()
                        // Indicating that the operation was successful
                        .setSuccess(true)
                        // Sending the number of data points received
                        .setDataPointsReceived(dataPoints)  
                        .build();
                // Sending the summary response back to the client
                responseObserver.onNext(summaryResponse);
                // Completing the stream
                responseObserver.onCompleted();  
            }
        };
    }

    // Bidirectional Streaming RPC - Continuous two-way communication
    // This method allows bidirectional streaming, where both the client and server can send and receive messages.
    @Override
    public StreamObserver<EnergyUpdateRequest> monitorEnergy(StreamObserver<EnergyUpdateResponse> responseObserver) {
        // Returning a StreamObserver to handle incoming energy updates
        return new StreamObserver<EnergyUpdateRequest>() {
            @Override
            public void onNext(EnergyUpdateRequest value) {
                // Logic to handle energy updates
                String status = "Energy optimized for device " + value.getDeviceId();
                
                // Creating a response indicating the status of the energy update
                EnergyUpdateResponse response = EnergyUpdateResponse.newBuilder()
                        // Sending back the device ID
                        .setDeviceId(value.getDeviceId())
                        // The optimization status message
                        .setStatus(status)  
                        .build();
                
                // Sending the response back to the client
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                // Handling errors that may occur during the bidirectional stream
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                // Completing the bidirectional communication
                responseObserver.onCompleted();
            }
        };
    }
}
