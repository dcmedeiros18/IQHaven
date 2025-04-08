/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.iqhaven;

/**
 *
 * @author dcmed
 */

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class AutomationServer {
    private Server server;

    public void start(int port) throws IOException {
        server = ServerBuilder.forPort(port)
            .addService(new AutomationServiceImpl())
            .build()
            .start();
        
        System.out.println("Servidor de Automação iniciado na porta " + port);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Desligando servidor...");
            AutomationServer.this.stop();
            System.err.println("Servidor desligado.");
        }));
    }

    private void stop() {
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
        final AutomationServer server = new AutomationServer();
        server.start(50051);
        server.blockUntilShutdown();
    }
}