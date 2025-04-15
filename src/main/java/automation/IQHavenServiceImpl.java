package automation;

import io.grpc.stub.StreamObserver;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IQHavenServiceImpl extends AutomationServiceGrpc.AutomationServiceImplBase {

    // 1. Unary RPC
    @Override
    public void toggleDevice(Automation.ToggleDeviceRequest request, StreamObserver<Automation.ToggleDeviceResponse> responseObserver) {
        String status = request.getTurnOn() ? "ON" : "OFF";
        String message = "Device " + request.getDeviceId() + " toggled to " + status;

        Automation.ToggleDeviceResponse response = Automation.ToggleDeviceResponse.newBuilder()
                .setSuccess(true)
                .setMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // 2. Server Streaming RPC
    @Override
    public void streamDeviceStatus(Automation.StreamDeviceStatusRequest request, StreamObserver<Automation.DeviceStatusResponse> responseObserver) {
        for (int i = 1; i <= 5; i++) {
            Automation.DeviceStatusResponse status = Automation.DeviceStatusResponse.newBuilder()
                    .setStatus("Status update #" + i + " for device " + request.getDeviceId())
                    .setTimestamp(LocalDateTime.now().toString())
                    .build();
            responseObserver.onNext(status);
            try {
                Thread.sleep(1000); // Simula atraso de envio
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseObserver.onCompleted();
    }

    // 3. Client Streaming RPC
    @Override
    public StreamObserver<Automation.DeviceCommand> sendDeviceCommands(final StreamObserver<Automation.CommandSummaryResponse> responseObserver) {
        return new StreamObserver<Automation.DeviceCommand>() {
            int count = 0;

            @Override
            public void onNext(Automation.DeviceCommand command) {
                System.out.println("Recebido comando: " + command.getCommand() + " para " + command.getDeviceId());
                count++;
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Erro no client streaming: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                Automation.CommandSummaryResponse response = Automation.CommandSummaryResponse.newBuilder()
                        .setSuccess(true)
                        .setCommandsReceived(count)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    // 4. Bidirectional Streaming RPC
    @Override
    public StreamObserver<Automation.DeviceMessage> communicateWithDevice(final StreamObserver<Automation.DeviceMessage> responseObserver) {
        return new StreamObserver<Automation.DeviceMessage>() {
            @Override
            public void onNext(Automation.DeviceMessage message) {
                System.out.println("Mensagem recebida do dispositivo " + message.getDeviceId() + ": " + message.getMessage());

                // Responde de volta
                Automation.DeviceMessage response = Automation.DeviceMessage.newBuilder()
                        .setDeviceId(message.getDeviceId())
                        .setMessage("ACK: " + message.getMessage())
                        .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Erro no bidirectional streaming: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
