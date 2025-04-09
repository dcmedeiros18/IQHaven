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
 * @author dcmed
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
            .addService(new AutomationServiceImpl())  // Adding the service to handle automation-related requests
            .build()
            .start();
        
        // Log the service registration details and the port the server is running on
        System.out.println("Service registered: " + AutomationServiceImpl.class.getSimpleName());
        System.out.println("Automation Server started on port " + port);
        
        // Add a shutdown hook to gracefully stop the server when the application is terminated
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down the server...");
            AutomationServer.this.stop();
            System.err.println("Server shut down.");
        }));
    }

    /**
     * Stops the server if it is running.
     */
    private void stop() {
        // Check if the server is initialized and then shutdown the server
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Blocks the current thread and waits until the server shuts down.
     * This method ensures that the server keeps running until it is manually stopped.
     * 
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void blockUntilShutdown() throws InterruptedException {
        // Block the main thread and keep it running until the server terminates
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main method to start the server.
     * 
     * @param args command-line arguments (not used in this case)
     * @throws IOException if an error occurs while starting the server
     * @throws InterruptedException if the thread is interrupted while waiting for the server to shut down
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // Instantiate the server and start it on port 50051
        final AutomationServer server = new AutomationServer();
        server.start(50051);
        
        // Block until the server shuts down
        server.blockUntilShutdown();
    }
}
