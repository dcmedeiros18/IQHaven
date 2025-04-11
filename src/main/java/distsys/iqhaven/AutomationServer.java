/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.iqhaven;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

/**
 * This class represents the Automation gRPC Server that listens for incoming requests
 * and processes them using the AutomationServiceImpl class. It handles the lifecycle of
 * the server, including starting, stopping, and waiting for shutdown.
 * The server listens on a specified port and provides services related to device automation.
 * Includes logging and graceful shutdown with diagnostics.
 * 
 * Updated for improved logging and safe shutdown handling.
 * 
 * Author: dcmed
 */
public class AutomationServer {
    private Server server;

    /**
     * Starts the gRPC server on a specified port.
     *
     * @param port the port on which the server should listen for incoming requests
     * @throws IOException if an error occurs while starting the server
     */
    public void start(int port) throws IOException {
        // Create a new server and add the AutomationServiceImpl service to handle requests
        server = ServerBuilder.forPort(port)
            .addService(new AutomationServiceImpl()) // Registering the service implementation
            .build()
            .start();

        // Log service registration and confirmation of server startup
        System.out.println("[SERVER] Service registered: " + AutomationServiceImpl.class.getSimpleName());
        System.out.println("[SERVER] Automation Server started on port " + port);

        // Add shutdown hook for graceful termination
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("[SERVER] JVM shutdown detected, initiating server shutdown...");
            AutomationServer.this.stop();
            System.err.println("[SERVER] Server shutdown completed.");
        }));
    }

    /**
     * Stops the server if it is running.
     * Ensures a clean shutdown by terminating ongoing calls and releasing resources.
     */
    private void stop() {
        if (server != null) {
            System.out.println("[SERVER] Attempting to shut down...");
            server.shutdown(); // Graceful shutdown
            try {
                if (!server.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                    System.out.println("[SERVER] Forcing shutdown...");
                    server.shutdownNow(); // Force shutdown if not completed
                }
            } catch (InterruptedException e) {
                System.err.println("[SERVER ERROR] Shutdown interrupted: " + e.getMessage());
                server.shutdownNow();
            }
        }
    }

    /**
     * Blocks the current thread and waits until the server shuts down.
     * Keeps the server running until manually stopped or system is terminated.
     *
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main method to start the server.
     *
     * @param args command-line arguments (not used)
     * @throws IOException if an error occurs while starting the server
     * @throws InterruptedException if the thread is interrupted while waiting for the server to shut down
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final AutomationServer server = new AutomationServer();
        System.out.println("[SERVER] Initializing...");
        server.start(50051); // Start the server on the designated port
        server.blockUntilShutdown(); // Keep running until shutdown is requested
    }
}
