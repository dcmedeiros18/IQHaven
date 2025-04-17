package automation;

/**
 * @author dcmed
 */

import io.grpc.*;
import io.grpc.Server;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import automation.Energy.*;

public class IQHavenServer {
    private static final Logger logger = Logger.getLogger(IQHavenServer.class.getName());
    private static final int PORT = 50051;
    private Server server;

    /**
     * Starts the gRPC server, adding the main services (automation, energy, and security).
     * Uses insecure credentials (no TLS) and includes a placeholder for authentication interceptor.
     */
    public void start() throws IOException {
        try {
            // gRPC server creation (no TLS, but with optional authentication interceptor)
            server = Grpc.newServerBuilderForPort(PORT, InsecureServerCredentials.create())
                    .addService(new AutomationServiceImpl())  // Automation service
                    .addService(new EnergyServiceImpl())      // Energy service
                    .addService(new SecurityServiceImpl())    // Security service
//                    .intercept(new AuthenticatorInterceptor()) // Authentication interceptor
                    .build()
                    .start();

            System.out.println("Server started on port: " + PORT);
            registerShutdownHook(); // Registers a shutdown hook to gracefully stop the server

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to start server", e);
            throw new IOException("Server startup failed", e);
        }
    }

    /**
     * Registers a shutdown hook to gracefully stop the server when the JVM is terminated.
     */
    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stop();  // Calls the stop method
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
    }

    /**
     * Stops the gRPC server, waiting a few seconds before forcing shutdown.
     */
    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown();
            if (!server.awaitTermination(3, TimeUnit.SECONDS)) {
                server.shutdownNow();
            }
        }
    }

    /**
     * Blocks the current thread until the server is terminated.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main method to start and block the server process.
     */
    public static void main(String[] args) {
        IQHavenServer server = new IQHavenServer();
        try {
            server.start(); // Starts the server
            server.blockUntilShutdown(); // Blocks until server is shut down
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server terminated with error", e);
            System.exit(1);
        }
    }
}
