package automation;

import automation.*;
import automation.Automation.*;
import automation.Energy.*;
import automation.Security.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;

import automation.AutomationServiceGrpc.*;
import automation.EnergyServiceGrpc.*;
import automation.SecurityServiceGrpc.*;

public class SmartHomeControlPanelUI extends JFrame {

    private AutomationServiceStub automationStub;
    private EnergyServiceStub energyStub;
    private SecurityServiceStub securityStub;

    public SmartHomeControlPanelUI() {
        // gRPC Channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        automationStub = AutomationServiceGrpc.newStub(channel);
        energyStub = EnergyServiceGrpc.newStub(channel);
        securityStub = SecurityServiceGrpc.newStub(channel);

        setTitle("Painel de Automação Residencial");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // Componentes
        add(setSchedulePanel());
        add(deviceCommandPanel());
        add(energyStreamPanel());
        add(securityFeedPanel());
    }

    private JPanel setSchedulePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("1. Agendar Fechamento da Cortina"));

        JTextField timeField = new JTextField(10);
        JButton scheduleBtn = new JButton("Agendar");

        scheduleBtn.addActionListener(e -> {
            SetScheduleRequest request = SetScheduleRequest.newBuilder()
                    .setDeviceId("blinds_sala")
                    .setScheduleTime(timeField.getText())
                    .setTurnOn(false) // fechar
                    .build();

            automationStub.setSchedule(request, new StreamObserver<SetScheduleResponse>() {
                @Override
                public void onNext(SetScheduleResponse value) {
                    JOptionPane.showMessageDialog(null, value.getMessage());
                }

                @Override
                public void onError(Throwable t) {
                    JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());
                }

                @Override
                public void onCompleted() {}
            });
        });

        panel.add(new JLabel("Horário (HH:MM):"));
        panel.add(timeField);
        panel.add(scheduleBtn);
        return panel;
    }

    private JPanel deviceCommandPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("2. Enviar Comandos para Dispositivos"));

        JButton sendCommandsBtn = new JButton("Executar Comandos");

        sendCommandsBtn.addActionListener(e -> {
            StreamObserver<DeviceCommand> requestObserver = automationStub.sendDeviceCommands(
                    new StreamObserver<CommandSummaryResponse>() {
                        @Override
                        public void onNext(CommandSummaryResponse value) {
                            JOptionPane.showMessageDialog(null, "Comandos enviados: " + value.getCommandsReceived());
                        }

                        @Override
                        public void onError(Throwable t) {
                            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());
                        }

                        @Override
                        public void onCompleted() {}
                    });

            // Exemplos de comandos
            requestObserver.onNext(DeviceCommand.newBuilder()
                    .setDeviceId("light_sala")
                    .setCommand("ON").build());

            requestObserver.onNext(DeviceCommand.newBuilder()
                    .setDeviceId("blinds_sala")
                    .setCommand("CLOSE").build());

            requestObserver.onCompleted();
        });

        panel.add(sendCommandsBtn);
        return panel;
    }

    private JPanel energyStreamPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("3. Consumo de Energia"));

        JTextArea energyOutput = new JTextArea(5, 40);
        energyOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(energyOutput);
        JButton streamEnergyBtn = new JButton("Iniciar Streaming");

        streamEnergyBtn.addActionListener(e -> {
            energyOutput.setText("Iniciando monitoramento...\n");

            StreamEnergyUsageRequest request = StreamEnergyUsageRequest.newBuilder()
                    .setDeviceId("light_sala")
                    .build();

            energyStub.streamEnergyUsage(request, new StreamObserver<EnergyUsageResponse>() {
                @Override
                public void onNext(EnergyUsageResponse value) {
                    SwingUtilities.invokeLater(() -> energyOutput.append("Uso: " + value.getUsage() + " at " + value.getTimestamp() + "\n"));
                }

                @Override
                public void onError(Throwable t) {
                    SwingUtilities.invokeLater(() -> energyOutput.append("Erro: " + t.getMessage() + "\n"));
                }

                @Override
                public void onCompleted() {
                    SwingUtilities.invokeLater(() -> energyOutput.append("Streaming encerrado.\n"));
                }
            });
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(streamEnergyBtn, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel securityFeedPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("4. Monitoramento de Segurança (Movimento)"));

        JTextArea securityLog = new JTextArea(5, 40);
        securityLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(securityLog);
        JButton startFeedBtn = new JButton("Iniciar Detecção de Movimento");

        startFeedBtn.addActionListener(e -> {
            StreamObserver<SecurityEvent> requestObserver = securityStub.liveSecurityFeed(
                    new StreamObserver<SecurityAlert>() {
                        @Override
                        public void onNext(SecurityAlert value) {
                            SwingUtilities.invokeLater(() -> securityLog.append("Alerta: " + value.getMessage() + " [" + value.getAlertLevel() + "]\n"));
                        }

                        @Override
                        public void onError(Throwable t) {
                            SwingUtilities.invokeLater(() -> securityLog.append("Erro: " + t.getMessage() + "\n"));
                        }

                        @Override
                        public void onCompleted() {
                            SwingUtilities.invokeLater(() -> securityLog.append("Stream finalizado.\n"));
                        }
                    });

            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    // Simula movimento detectado a cada 2 segundos
                    for (int i = 0; i < 3; i++) {
                        Thread.sleep(2000);
                        SecurityEvent event = SecurityEvent.newBuilder()
                                .setEventType("movement")
                                .setDetails("Movimento detectado na sala")
                                .build();
                        requestObserver.onNext(event);
                    }
                    requestObserver.onCompleted();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(startFeedBtn, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SmartHomeControlPanelUI app = new SmartHomeControlPanelUI();
            app.setVisible(true);
        });
    }
}
