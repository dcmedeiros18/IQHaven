/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package distsys.iqhaven;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

/*
 * @author dcmed
 */

public class EnergyServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Define the port where the server will run
        // Port number 50051 is commonly used for gRPC services
        int port = 50051;  

        // Create the server and add the energy service implementation
        Server server;
        // Specify the port for the gRPC server
        server = ServerBuilder.forPort(port)
                // Register the energy service implementatio
                .addService(new EnergyServiceImpl())
                // Build the server instance
                .build();  

        // Log the server startup message to indicate that the server is running
        System.out.println("gRPC server started on port " + port);

        // Start the server asynchronously
        server.start();

        // Keep the server running and wait for termination (to handle graceful shutdown)
        // This prevents the program from exiting immediately and ensures that the server continues to run.
        server.awaitTermination();
    }
}
