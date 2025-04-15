package automation;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;


import java.io.IOException;

public class IQHavenServer {

    private Server server;

    public void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new IQHavenServiceImpl())
                .build()
                .start();

        System.out.println("IQHavenServer started. Listening on port " + port);

        // Adiciona shutdown hook para encerrar o servidor corretamente
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server...");
            IQHavenServer.this.stop();
            System.err.println("Server shut down.");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // Espera até que o servidor seja encerrado manualmente
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    // Método main para rodar o servidor
    public static void main(String[] args) throws IOException, InterruptedException {
        final IQHavenServer server = new IQHavenServer();
        server.start();
        server.blockUntilShutdown();
    }
}
