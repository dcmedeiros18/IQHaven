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

import static automation.Automation.DeviceCommand.newBuilder;

public class SmartHomeControlPanelUI extends JFrame {
    private AutomationServiceStub automationStub;
    private EnergyServiceStub energyStub;
    private SecurityServiceStub securityStub;

    // unique api key for authentication on the server connection
    private final String API_KEY = "A7xL9mB2YzW0pTqE5vN6CdJsRuKfHgXoPiM1Q4ZlDbtV3nScAy";

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

        JTextField timeField = new JTextField(5);
        JComboBox<String> amPmBox = new JComboBox<>(new String[]{"AM", "PM"});
        JButton scheduleBtn = new JButton("Agendar");

        scheduleBtn.addActionListener(e -> {
            String inputTime = timeField.getText().trim();
            String selectedAmPm = (String) amPmBox.getSelectedItem();


            if (!inputTime.matches("([01]\\d|2[0-3]):[0-5]\\d")) {
                JOptionPane.showMessageDialog(null, "Horário inválido, entre com um horário válido");
                return;
            }

            SetScheduleRequest request = (SetScheduleRequest) SetScheduleRequest.newBuilder()
                    .setDeviceId("blinds_sala")
                    .setScheduleTime(inputTime)
                    .setTurnOn(false)
                    .setApiKey(API_KEY)
                    .build();

            automationStub.setSchedule(request, new StreamObserver<SetScheduleResponse>() {
                @Override
                public void onNext(SetScheduleResponse value) {
                    JOptionPane.showMessageDialog(null,
                            "Fechamento de cortinas agendado para: " + inputTime);
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
        panel.add(amPmBox);
        panel.add(scheduleBtn);
        return panel;
    }

    private JPanel deviceCommandPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("2. Enviar Comandos para Dispositivos"));

        JLabel lightLabel = new JLabel("Lâmpada da Sala:");
        String[] lightOptions = {"Ligar", "Desligar"};
        JComboBox<String> lightCombo = new JComboBox<>(lightOptions);

        JLabel blindsLabel = new JLabel("Cortinas:");
        String[] blindsOptions = {"Abrir", "Fechar"};
        JComboBox<String> blindsCombo = new JComboBox<>(blindsOptions);

        JButton sendCommandsBtn = new JButton("Executar Comandos");

        sendCommandsBtn.addActionListener(e -> {
            String lightCommand = lightCombo.getSelectedItem().equals("Ligar") ? "ON" : "OFF";
            String blindsCommand = blindsCombo.getSelectedItem().equals("Abrir") ? "OPEN" : "CLOSE";


            StreamObserver<DeviceCommand> requestObserver = automationStub.sendDeviceCommands(
                    new StreamObserver<CommandSummaryResponse>() {
                        @Override
                        public void onNext(CommandSummaryResponse value) {
                           JOptionPane.showMessageDialog(null,
                                    "Comandos executados com sucesso!\n");
                        }

                        @Override
                        public void onError(Throwable t) {
                            JOptionPane.showMessageDialog(null, "Erro: " + t.getMessage());
                        }

                        @Override
                        public void onCompleted() {}
                    });

            // First command
            requestObserver.onNext(newBuilder()
                    .setDeviceId("light_sala")
                    .setCommand(lightCommand)
                    .setApiKey(API_KEY)
                    .build());

// Second command
            requestObserver.onNext(newBuilder()
                    .setDeviceId("blinds_sala")
                    .setCommand(blindsCommand)
                    .setApiKey(API_KEY)
                    .build());
            requestObserver.onCompleted();
        });

        panel.add(lightLabel);
        panel.add(lightCombo);
        panel.add(blindsLabel);
        panel.add(blindsCombo);
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


            StreamEnergyUsageRequest request = (StreamEnergyUsageRequest) StreamEnergyUsageRequest.newBuilder()
                    .setDeviceId("light_sala")
                    .setApiKey(API_KEY)
                    .build();

            energyStub.streamEnergyUsage(request, new StreamObserver<EnergyUsageResponse>() {
                @Override
                public void onNext(EnergyUsageResponse value) {
                    SwingUtilities.invokeLater(() ->
                           energyOutput.append("Uso: " + value.getUsage() + "W at " +
                                   value.getTimestamp() ));
                }

                @Override
                public void onError(Throwable t) {
                    SwingUtilities.invokeLater(() ->
                            energyOutput.append("Erro: " + t.getMessage() + "\n"));
                }

                @Override
                public void onCompleted() {
                    SwingUtilities.invokeLater(() ->
                            energyOutput.append("Streaming encerrado.\n"));
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
                            SwingUtilities.invokeLater(() ->
                                    securityLog.append("Alerta: " + value.getMessage() +
                                            " [" + value.getAlertLevel() + "] \n" ));
                        }

                        @Override
                        public void onError(Throwable t) {
                            SwingUtilities.invokeLater(() ->
                                    securityLog.append("Erro: " + t.getMessage() + "\n"));
                        }

                        @Override
                        public void onCompleted() {
                            SwingUtilities.invokeLater(() ->
                                    securityLog.append("Stream finalizado.\n"));
                        }
                    });

            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    for (int i = 0; i < 3; i++) {
                        Thread.sleep(2000);
                        SecurityEvent event = (SecurityEvent) SecurityEvent.newBuilder()
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