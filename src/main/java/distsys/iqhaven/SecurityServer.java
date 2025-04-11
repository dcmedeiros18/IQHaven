/*
 * This file defines and starts a gRPC server for handling security-related services.
 * The server is configured to listen on port 50051 and uses the SecurityServiceImpl service implementation.
 */

package distsys.iqhaven;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * Entry point for the gRPC Security Server application.
 * 
 * This class sets up and starts a gRPC server on a specific port (50051),
 * registering the service implementation that will handle incoming gRPC calls.
 * 
 * @author dcmed
 */

public class SecurityServer {
    public static void main(String[] args) throws Exception {
        // Build the server to listen on port 50051 and add the service implementation
        Server server = ServerBuilder.forPort(50051)
                // Register the service handler
                .addService(new SecurityServiceImpl())  
                .build();

        // Inform that the server has started successfully
        System.out.println("Security Server started on port 50051");

        // Start the server to begin accepting requests
        server.start();

        // Wait for the server to be terminated (blocks the main thread)
        server.awaitTermination();
    }
}
