package automation;



import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class IQHavenServer {
    private Server server;
    private final int port;

    public IQHavenServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new IQHavenServiceImpl())
                .build()
                .start();

        System.out.println("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            IQHavenServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        IQHavenServer server = new IQHavenServer(50051);
        server.start();
        server.blockUntilShutdown();
    }
}