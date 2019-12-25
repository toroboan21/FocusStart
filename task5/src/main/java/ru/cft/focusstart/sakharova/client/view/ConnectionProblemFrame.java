package ru.cft.focusstart.sakharova.client.view;

import ru.cft.focusstart.sakharova.client.common.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConnectionProblemFrame implements ChatModalFrame {

    private static final String CONNECTION_FAILURE_MESSAGE =
            "Ошибка подключения к серверу. Сервер недоступен, либо адрес сервера введен некорректно.";
    private static final String CONNECTION_LOST_MESSAGE = "Подключение к серверу потеряно. Сервер недоступен.";

    private final SwingChatView swingChatView;
    private final Controller controller;
    private final JDialog connectionFailureDialog;
    private final JLabel connectionFailureLabel;
    private final JButton reconnectButton;
    private final JButton exitButton;

    ConnectionProblemFrame(SwingChatView swingChatView, Controller controller) {
        this.swingChatView = swingChatView;
        this.controller = controller;
        connectionFailureDialog = new JDialog(swingChatView.getMainFrame());
        connectionFailureLabel = new JLabel();
        reconnectButton = new JButton();
        exitButton = new JButton();
    }

    void initConnectionFailureVersion() {
        init();
        connectionFailureLabel.setText(CONNECTION_FAILURE_MESSAGE);
        reconnectButton.addActionListener(e -> {
            connectionFailureDialog.dispose();
            var serverConnectionFrame = swingChatView.createServerConnectionDialog();
            serverConnectionFrame.init();
            serverConnectionFrame.show();
        });
    }

    void initConnectionLostVersion() {
        init();
        connectionFailureLabel.setText(CONNECTION_LOST_MESSAGE);
        reconnectButton.addActionListener(e -> {
            connectionFailureDialog.dispose();
            controller.reconnect();
        });
    }

    @Override
    public void init() {
        Container contentPane = connectionFailureDialog.getContentPane();

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(connectionFailureLabel, GroupLayout.DEFAULT_SIZE,
                                        579, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(reconnectButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                                .addComponent(exitButton, GroupLayout.PREFERRED_SIZE,
                                        229, GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(connectionFailureLabel, GroupLayout.PREFERRED_SIZE,
                                        53, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(reconnectButton, GroupLayout.PREFERRED_SIZE,
                                                84, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(exitButton, GroupLayout.PREFERRED_SIZE,
                                                84, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(65, Short.MAX_VALUE))
        );

        reconnectButton.setText("Попробовать подключиться снова");

        exitButton.setText("Отключиться");
        exitButton.addActionListener(e -> {
            connectionFailureDialog.dispose();
            swingChatView.disconnect();
        });

        connectionFailureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        connectionFailureDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                controller.disconnect();
            }
        });

        swingChatView.prepareDialogFrame(connectionFailureDialog);
    }

    @Override
    public void show() {
        connectionFailureDialog.setVisible(true);
    }
}