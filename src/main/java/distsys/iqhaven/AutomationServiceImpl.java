/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package distsys.iqhaven;

import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;
import distsys.iqhaven.service.*;

/**
 *
 * @author dcmed
 */
public class AutomationServiceImpl extends AutomationServiceGrpc.AutomationServiceImplBase {
    
    @Override
    public void toggleDevice(ToggleDeviceRequest request, StreamObserver<ToggleDeviceResponse> responseObserver) {
        // Implementação do Unary RPC
        boolean success = controlDevice(request.getDeviceId(), request.getTurnOn());
        
        ToggleDeviceResponse response = ToggleDeviceResponse.newBuilder()
            .setSuccess(success)
            .setMessage(success ? "Dispositivo controlado" : "Falha")
            .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
    // Outros métodos de serviço aqui...
    
    private boolean controlDevice(String deviceId, boolean turnOn) {
        // Lógica real para controlar dispositivos
        return true; // Simulação
    }
}