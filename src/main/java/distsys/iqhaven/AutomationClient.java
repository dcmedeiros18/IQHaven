/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.iqhaven;

/**
 *
 * @author dcmed
 */
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import distsys.iqhaven.service.*;

public class AutomationClient {
    private final AutomationServiceGrpc.AutomationServiceBlockingStub blockingStub;
    private final AutomationServiceGrpc.AutomationServiceStub asyncStub;

    public AutomationClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext()
            .build();
        
        blockingStub = AutomationServiceGrpc.newBlockingStub(channel);
        asyncStub = AutomationServiceGrpc.newStub(channel);
    }

    public void toggleDevice(String deviceId, boolean turnOn) {
        ToggleDeviceRequest request = ToggleDeviceRequest.newBuilder()
            .setDeviceId(deviceId)
            .setTurnOn(turnOn)
            .build();
        
        ToggleDeviceResponse response = blockingStub.toggleDevice(request);
        System.out.println("Resposta: " + response.getMessage());
    }

    // Outros métodos clientes aqui...
    
    public static void main(String[] args) {
        AutomationClient client = new AutomationClient("localhost", 50051);
        client.toggleDevice("luz-sala", true);
    }
}