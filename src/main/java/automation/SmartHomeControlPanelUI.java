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

    // Unique API key for authentication on the server connection
    private final String API_KEY = "A7xL9mB2YzW0pTqE5vN6CdJsRuKfHgXoPiM1Q4ZlDbtV3nScAy";

    public SmartHomeControlPanelUI() {
        // gRPC Channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        automationStub = AutomationServiceGrpc.newStub(channel);
        energyStub = EnergyServiceGrpc.newStub(channel);
        securityStub = SecurityServiceGrpc.newStub(channel);

        setTitle("Smart Home Automation Panel");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // UI Components
        add(setSchedulePanel());
        add(deviceCommandPanel());
        add(energyStreamPanel());
        add(securityFeedPanel());
    }

    private JPanel setSchedulePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("1. Schedule Curtain Closing"));

        JTextField timeField = new JTextField(5);
        JComboBox<String> amPmBox = new JComboBox<>(new String[]{"AM", "PM"});
        JButton scheduleBtn = new JButton("Schedule");

        scheduleBtn.addActionListener(e -> {
            String inputTime = timeField.getText().trim();
            String selectedAmPm = (String) amPmBox.getSelectedItem();

            // Validate time format
            if (!inputTime.matches("([01]\\d|2[0-3]):[0-5]\\d")) {
                JOptionPane.showMessageDialog(null, "Invalid time. Please enter a valid time.");
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
                            "Curtain closing scheduled at " + inputTime);
                }

                @Override
                public void onError(Throwable t) {
                    JOptionPane.showMessageDialog(null, "Error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {}
            });
        });

        panel.add(new JLabel("Time (HH:MM):"));
        panel.add(timeField);
        panel.add(amPmBox);
        panel.add(scheduleBtn);
        return panel;
    }

    private JPanel deviceCommandPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("2. Send Commands to Devices"));

        JLabel lightLabel = new JLabel("Living Room Light:");
        String[] lightOptions = {"Turn On", "Turn Off"};
        JComboBox<String> lightCombo = new JComboBox<>(lightOptions);

        JLabel blindsLabel = new JLabel("Blinds:");
        String[] blindsOptions = {"Open", "Close"};
        JComboBox<String> blindsCombo = new JComboBox<>(blindsOptions);

        JButton sendCommandsBtn = new JButton("Execute Commands");

        sendCommandsBtn.addActionListener(e -> {
            String lightCommand = lightCombo.getSelectedItem().equals("Turn On") ? "ON" : "OFF";
            String blindsCommand = blindsCombo.getSelectedItem().equals("Open") ? "OPEN" : "CLOSE";

            StreamObserver<DeviceCommand> requestObserver = automationStub.sendDeviceCommands(
                    new StreamObserver<CommandSummaryResponse>() {
                        @Override
                        public void onNext(CommandSummaryResponse value) {
                            JOptionPane.showMessageDialog(null,
                                    "Commands executed successfully!\n");
                        }

                        @Override
                        public void onError(Throwable t) {
                            JOptionPane.showMessageDialog(null, "Error: " + t.getMessage());
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
        panel.setBorder(BorderFactory.createTitledBorder("3. Energy Consumption of New Devices"));

        JTextArea energyOutput = new JTextArea(5, 40);
        energyOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(energyOutput);
        JButton streamEnergyBtn = new JButton("Start Streaming");

        streamEnergyBtn.addActionListener(e -> {
            StreamEnergyUsageRequest request = (StreamEnergyUsageRequest) StreamEnergyUsageRequest.newBuilder()
                    .setDeviceId("light_sala")
                    .setApiKey(API_KEY)
                    .build();

            energyStub.streamEnergyUsage(request, new StreamObserver<EnergyUsageResponse>() {
                @Override
                public void onNext(EnergyUsageResponse value) {
                    SwingUtilities.invokeLater(() ->
                            energyOutput.append("Usage: " + value.getUsage() + "W at " +
                                    value.getTimestamp() ));
                }

                @Override
                public void onError(Throwable t) {
                    SwingUtilities.invokeLater(() ->
                            energyOutput.append("Error: " + t.getMessage() + "\n"));
                }

                @Override
                public void onCompleted() {
                    SwingUtilities.invokeLater(() ->
                            energyOutput.append("Streaming finished.\n"));
                }
            });
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(streamEnergyBtn, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel securityFeedPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("4. Security Monitoring (Motion)"));

        JTextArea securityLog = new JTextArea(5, 40);
        securityLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(securityLog);
        JButton startFeedBtn = new JButton("Start Motion Detection");

        startFeedBtn.addActionListener(e -> {
            StreamObserver<SecurityEvent> requestObserver = securityStub.liveSecurityFeed(
                    new StreamObserver<SecurityAlert>() {
                        @Override
                        public void onNext(SecurityAlert value) {
                            SwingUtilities.invokeLater(() ->
                                    securityLog.append("Alert: " + value.getMessage() +
                                            " [" + value.getAlertLevel() + "] \n" ));
                        }

                        @Override
                        public void onError(Throwable t) {
                            SwingUtilities.invokeLater(() ->
                                    securityLog.append("Error: " + t.getMessage() + "\n"));
                        }

                        @Override
                        public void onCompleted() {
                            SwingUtilities.invokeLater(() ->
                                    securityLog.append("Stream ended.\n"));
                        }
                    });

            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    for (int i = 0; i < 3; i++) {
                        Thread.sleep(2000);
                        SecurityEvent event = (SecurityEvent) SecurityEvent.newBuilder()
                                .setEventType("movement")
                                .setDetails("Movement detected in the living room")
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
