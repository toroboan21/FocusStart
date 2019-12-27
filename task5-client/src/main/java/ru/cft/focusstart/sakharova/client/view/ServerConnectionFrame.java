package ru.cft.focusstart.sakharova.client.view;

import org.apache.commons.lang3.StringUtils;
import ru.cft.focusstart.sakharova.client.common.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ServerConnectionFrame implements ChatModalFrame {

    private static final String SERVER_ADDRESS_VALIDATION_MESSAGE = "Адрес сервера не может быть пустым!";
    private static final String SERVER_PORT_ONLY_NUMBERS_ALLOWED_MESSAGE = "Порт может содержать только целые цифры!";

    private final SwingChatView swingChatView;
    private final Controller controller;
    private final JDialog serverConnectionDialog;
    private final JPanel dialogPanel;
    private final JPanel contentPanel;
    private final JLabel addressAndPortRequestLabel;
    private final JLabel serverAddressLabel;
    private final JLabel serverPortLabel;
    private final JTextField serverAddressTextField;
    private final JTextField serverPortTextField;
    private final JButton okButton;
    private final JButton cancelButton;

    ServerConnectionFrame(SwingChatView swingChatView, Controller controller) {
        this.swingChatView = swingChatView;
        this.controller = controller;
        serverConnectionDialog = new JDialog(swingChatView.getMainFrame());
        dialogPanel = new JPanel();
        contentPanel = new JPanel();
        addressAndPortRequestLabel = new JLabel();
        serverAddressLabel = new JLabel();
        serverAddressTextField = new JTextField();
        serverPortLabel = new JLabel();
        serverPortTextField = new JTextField();
        okButton = new JButton();
        cancelButton = new JButton();
    }

    @Override
    public void init() {
        Container contentPane = serverConnectionDialog.getContentPane();
        contentPane.setLayout(new BorderLayout());

        GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
                contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(contentPanelLayout.createParallelGroup()
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                                .addComponent(okButton, GroupLayout.PREFERRED_SIZE,
                                                        123, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE,
                                                        124, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 54, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.TRAILING,
                                                contentPanelLayout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(addressAndPortRequestLabel,
                                                                GroupLayout.PREFERRED_SIZE, 286,
                                                                GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.TRAILING,
                                                contentPanelLayout.createSequentialGroup()
                                                        .addGroup(contentPanelLayout.createParallelGroup()
                                                                .addComponent(serverAddressLabel)
                                                                .addComponent(serverPortLabel))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(contentPanelLayout.createParallelGroup()
                                                                .addGroup(contentPanelLayout.createSequentialGroup()
                                                                        .addComponent(serverAddressTextField,
                                                                                GroupLayout.PREFERRED_SIZE, 211,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(32, 54, Short.MAX_VALUE))
                                                                .addGroup(contentPanelLayout.createSequentialGroup()
                                                                        .addComponent(serverPortTextField,
                                                                                GroupLayout.PREFERRED_SIZE, 77,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(0, 0, Short.MAX_VALUE)))))
                                .addContainerGap())
        );

        contentPanelLayout.setVerticalGroup(
                contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(addressAndPortRequestLabel, GroupLayout.PREFERRED_SIZE,
                                        51, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(serverAddressTextField, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(serverAddressLabel))
                                .addGap(27, 27, 27)
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(serverPortTextField, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(serverPortLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(okButton)
                                        .addComponent(cancelButton))
                                .addGap(16, 16, 16))
        );

        dialogPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        dialogPanel.setLayout(new BorderLayout());

        addressAndPortRequestLabel.setText("Введите адрес и порт сервера:");
        addressAndPortRequestLabel.setHorizontalAlignment(SwingConstants.CENTER);

        serverAddressLabel.setText("Адрес сервера:");
        serverPortLabel.setText("Порт:");

        dialogPanel.add(contentPanel, BorderLayout.SOUTH);
        contentPane.add(dialogPanel, BorderLayout.CENTER);

        okButton.setText("OK");
        okButton.addActionListener(createActionListenerForOkButton());

        cancelButton.setText("Отмена");
        cancelButton.addActionListener(e -> serverConnectionDialog.dispose());

        serverConnectionDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        swingChatView.prepareDialogFrame(serverConnectionDialog);
    }

    private ActionListener createActionListenerForOkButton() {
        return e -> {
            String serverAddress = serverAddressTextField.getText();
            if (StringUtils.isBlank(serverAddress)) {
                initErrorFrame(SERVER_ADDRESS_VALIDATION_MESSAGE);
                return;
            }
            try {
                int serverPort = Integer.parseInt(serverPortTextField.getText());
                serverConnectionDialog.dispose();
                controller.connect(serverAddress, serverPort);
            } catch (NumberFormatException ex) {
                initErrorFrame(SERVER_PORT_ONLY_NUMBERS_ALLOWED_MESSAGE);
            }
        };
    }

    private void initErrorFrame(String message) {
        ErrorFrame errorFrame = new ErrorFrame(swingChatView, message);
        errorFrame.show();
    }

    @Override
    public void show() {
        serverConnectionDialog.setVisible(true);
    }
}