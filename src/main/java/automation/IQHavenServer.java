package automation;

import io.grpc.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.TlsServerCredentials;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IQHavenServer {
    private static final Logger logger = Logger.getLogger(IQHavenServer.class.getName());
    private static final int PORT = 50051;
    private Server server;

    public void start() throws IOException {
        try {
            // Create server credentials with TLS
            ServerCredentials credentials = TlsServerCredentials.create(
                    new File("server.crt"),  // Server certificate
                    new File("server.pem")   // Private key
            );

            // Create authentication interceptor
            ServerInterceptor authInterceptor = new ServerInterceptor() {
                @Override
                public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
                        ServerCall<ReqT, RespT> call,
                        Metadata headers,
                        ServerCallHandler<ReqT, RespT> next) {

                    String token = headers.get(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER));

                    if (token == null || !token.equals("Bearer my-secret-token")) {
                        call.close(Status.UNAUTHENTICATED.withDescription("Invalid or missing token"), headers);
                        return new ServerCall.Listener<ReqT>() {};
                    }

                    return next.startCall(call, headers);
                }
            };

            // Build the server with security and services
            server = ServerBuilder.forPort(PORT)
                    .addService(new AutomationServiceImpl())
                    .addService(new EnergyServiceImpl())
                    .addService(new SecurityServiceImpl())
                    .intercept(authInterceptor)
                    .useTransportSecurity(
                            new File("server.crt"),
                            new File("server.pem")
                    )
                    .build()
                    .start();

            logger.log(Level.INFO, "IQHavenServer started. Listening on port {0}", PORT);
            logger.info("Services registered:\n" +
                    "- AutomationService\n" +
                    "- EnergyService\n" +
                    "- SecurityService");

            registerShutdownHook();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start server on port " + PORT, e);
            throw e;
        }
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down gRPC server...");
            try {
                IQHavenServer.this.stop();
                logger.info("Server shut down successfully.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error during server shutdown", e);
            }
        }));
    }

    public void stop() throws InterruptedException {
        if (server != null && !server.isShutdown()) {
            server.shutdown();
            try {
                if (!server.awaitTermination(3, TimeUnit.SECONDS)) {
                    server.shutdownNow();
                }
            } catch (InterruptedException e) {
                server.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) {
        IQHavenServer server = new IQHavenServer();
        try {
            server.start();
            server.blockUntilShutdown();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Server failed to start", e);
            System.exit(1);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Server was interrupted", e);
            Thread.currentThread().interrupt();
            System.exit(1);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected server error", e);
            System.exit(1);
        }
    }
}