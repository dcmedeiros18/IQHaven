package distsys.iqhaven;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * This class starts a gRPC server for the security service.
 * It listens on port 50051 and uses SecurityServiceImpl to handle requests.
 */
public class SecurityServer {

    public static void main(String[] args) {
        Server server = null;

        try {
            // Build the server and attach the service implementation
            server = ServerBuilder.forPort(50051)
                    .addService(new SecurityServiceImpl())  // Add your service here
                    .build();

            // Start the server
            server.start();
            System.out.println("Security Server started on port 50051");

            // Add a shutdown hook to safely close the server when the app is closed
            Server finalServer = server;  // Make 'server' effectively final for lambda use
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down Security Server...");
                if (finalServer != null) {
                    try {
                        finalServer.shutdown();
                        System.out.println("Server shut down successfully.");
                    } catch (Exception ex) {
                        System.err.println("Error during shutdown: " + ex.getMessage());
                    }
                }
            }));

            // Wait until the server is terminated (keeps running)
            server.awaitTermination();

        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
