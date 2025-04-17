package automation;

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

    public void start() throws IOException {
        try {
            // Criação do servidor gRPC (sem TLS, mas com interceptador de autenticação)
            server = Grpc.newServerBuilderForPort(PORT, InsecureServerCredentials.create())
                    .addService(new AutomationServiceImpl())  // Serviço de automação
                    .addService(new EnergyServiceImpl())      // Serviço de energia
                    .addService(new SecurityServiceImpl())    // Serviço de segurança
//                    .intercept(new AuthenticatorInterceptor()) // Interceptador de autenticação
                    .build()
                    .start();

            System.out.println("Server started on port: " + PORT);
            registerShutdownHook(); // Método para registrar o shutdown do servidor

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to start server", e);
            throw new IOException("Server startup failed", e);
        }
    }

    // Método de interceptação para autenticação via header
//    private static class AuthenticatorInterceptor implements ServerInterceptor {
//        private static final String VALID_TOKEN = "teste123"; // Token fixo (simples para teste)
//
//        @Override
//        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
//                ServerCall<ReqT, RespT> call,
//                Metadata headers,
//                ServerCallHandler<ReqT, RespT> next) {
//
//            // Obtém o token do header "x-auth-token" (corrigido para corresponder ao cliente)
//            String token = headers.get(Metadata.Key.of("x-auth-token", Metadata.ASCII_STRING_MARSHALLER));
//
////            if (token == null || !token.equals(VALID_TOKEN)) {
////                // Se o token estiver ausente ou for inválido, rejeita a requisição
////                call.close(Status.UNAUTHENTICATED.withDescription("Token inválido ou ausente"), headers);
////                return new ServerCall.Listener<ReqT>() {}; // Retorna um listener vazio (bloqueia a chamada)
////            }
//            return next.startCall(call, headers); // Se o token for válido, prossegue com a chamada
//        }
//    }

    // Método para registrar o shutdown do servidor
    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stop();  // Chama o método de parada do servidor
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
    }

    // Método para parar o servidor
    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown();
            if (!server.awaitTermination(3, TimeUnit.SECONDS)) {
                server.shutdownNow();
            }
        }
    }

    // Método para bloquear até que o servidor seja fechado
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) {
        IQHavenServer server = new IQHavenServer();
        try {
            server.start(); // Inicia o servidor
            server.blockUntilShutdown(); // Bloqueia até o servidor ser fechado
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Server terminated with error", e);
            System.exit(1);
        }
    }
}
