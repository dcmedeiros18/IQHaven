/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.iqhaven;

import automation.Automation.*;
import automation.AutomationServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Implementação do serviço gRPC definido no .proto
 *@author dcmed
 */
public class NewClass extends AutomationServiceGrpc.AutomationServiceImplBase {
    
    // 1. Toggle Device - Unary
    @Override
    public void toggleDevice(ToggleDeviceRequest request, StreamObserver<ToggleDeviceResponse> responseObserver) {
        System.out.println("[LOG] toggleDevice chamado para dispositivo: " + request.getDeviceId() + ", ação: " + (request.getTurnOn() ? "LIGAR" : "DESLIGAR"));
        
        boolean success = controlDevice(request.getDeviceId(), request.getTurnOn());

        ToggleDeviceResponse response = ToggleDeviceResponse.newBuilder()
            .setSuccess(success)
            .setMessage(success ? "Dispositivo controlado" : "Falha")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // 2. Set Schedule - Unary
    @Override
    public void setSchedule(SetScheduleRequest request, StreamObserver<SetScheduleResponse> responseObserver) {
        System.out.println("[LOG] setSchedule chamado para " + request.getDeviceId() + " em " + request.getScheduleTime());
        
        // Lógica fictícia de agendamento
        boolean success = true; // Aqui você implementaria lógica real de agendamento

        SetScheduleResponse response = SetScheduleResponse.newBuilder()
            .setSuccess(success)
            .setMessage("Agendamento definido para " + request.getScheduleTime())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // 3. Stream Device Status - Server Streaming
    @Override
    public void streamDeviceStatus(StreamDeviceStatusRequest request, StreamObserver<DeviceStatusResponse> responseObserver) {
        System.out.println("[LOG] streamDeviceStatus iniciado para " + request.getDeviceId());
        
        try {
            for (int i = 0; i < 5; i++) {
                DeviceStatusResponse response = DeviceStatusResponse.newBuilder()
                    .setStatus("Status #" + (i + 1) + " para " + request.getDeviceId())
                    .setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .build();

                responseObserver.onNext(response);
                Thread.sleep(1000); // Simula tempo real
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            responseObserver.onCompleted();
            System.out.println("[LOG] streamDeviceStatus finalizado para " + request.getDeviceId());
        }
    }

    // 4. Send Device Commands - Client Streaming
    @Override
    public StreamObserver<DeviceCommand> sendDeviceCommands(StreamObserver<CommandSummaryResponse> responseObserver) {
        System.out.println("[LOG] sendDeviceCommands iniciado");
        
        return new StreamObserver<DeviceCommand>() {
            int count = 0;

            @Override
            public void onNext(DeviceCommand command) {
                System.out.println("Comando recebido: " + command.getCommand() + " para " + command.getDeviceId());
                count++;
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("[ERRO] sendDeviceCommands: " + t.getMessage());
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                CommandSummaryResponse response = CommandSummaryResponse.newBuilder()
                    .setSuccess(true)
                    .setCommandsReceived(count)
                    .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
                System.out.println("[LOG] sendDeviceCommands finalizado com " + count + " comandos");
            }
        };
    }

    // 5. Communicate With Device - Bidirectional Streaming
    @Override
    public StreamObserver<DeviceMessage> communicateWithDevice(StreamObserver<DeviceMessage> responseObserver) {
        System.out.println("[LOG] communicateWithDevice iniciado");
        
        return new StreamObserver<DeviceMessage>() {
            @Override
            public void onNext(DeviceMessage message) {
                System.out.println("[LOG] Mensagem recebida de " + message.getDeviceId() + ": " + message.getMessage());

                // Simula uma resposta automática do dispositivo
                DeviceMessage response = DeviceMessage.newBuilder()
                    .setDeviceId(message.getDeviceId())
                    .setMessage("Resposta para: " + message.getMessage())
                    .build();

                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                System.err.println("[ERRO] communicateWithDevice: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                System.out.println("[LOG] communicateWithDevice finalizado");
            }
        };
    }

    // Simula controle de dispositivo
    private boolean controlDevice(String deviceId, boolean turnOn) {
        System.out.println("Controlando " + deviceId + " para " + (turnOn ? "ligar" : "desligar"));
        return true;
    }
}
