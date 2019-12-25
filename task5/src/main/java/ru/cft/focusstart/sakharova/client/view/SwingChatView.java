package ru.cft.focusstart.sakharova.client.view;

import ru.cft.focusstart.sakharova.client.common.ChatView;
import ru.cft.focusstart.sakharova.client.common.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SwingChatView implements ChatView {

    private final Controller controller;
    private final JFrame mainFrame;
    private final JLabel userNameDescriptionLabel;
    private final JLabel onlineUsersLabel;
    private final JScrollPane onlineUsersScrollPane;
    private final JList<String> onlineUsersList;
    private final DefaultListModel<String> onlineUsersListModel;
    private final JScrollPane mainChatScrollPane;
    private final JTextArea mainChatTextArea;
    private final JScrollPane sendMessageAreaScrollPane;
    private final JTextArea sendMessageTextArea;
    private final JButton sendMessageButton;
    private final JButton connectButton;
    private final JLabel userNameLabel;
    private final JButton disconnectButton;

    private GroupLayout mainFrameContentPaneLayout;

    public SwingChatView(Controller controller) {
        this.controller = controller;
        mainFrame = new JFrame();
        userNameDescriptionLabel = new JLabel();
        onlineUsersLabel = new JLabel();
        onlineUsersScrollPane = new JScrollPane();
        onlineUsersListModel = new DefaultListModel<>();
        onlineUsersList = new JList<>(onlineUsersListModel);
        mainChatScrollPane = new JScrollPane();
        mainChatTextArea = new JTextArea();
        sendMessageAreaScrollPane = new JScrollPane();
        sendMessageTextArea = new JTextArea();
        sendMessageButton = new JButton();
        connectButton = new JButton();
        userNameLabel = new JLabel();
        disconnectButton = new JButton();
    }

    @Override
    public void init() {
        initComponents();
        mainFrame.setVisible(true);
        var serverConnectionFrame = createServerConnectionDialog();
        serverConnectionFrame.init();
        serverConnectionFrame.show();
    }

    private void initComponents() {
        Container mainFrameContentPane = mainFrame.getContentPane();
        mainFrameContentPaneLayout = new GroupLayout(mainFrameContentPane);
        mainFrameContentPane.setLayout(mainFrameContentPaneLayout);

        initHorizontalGroup();
        initVerticalGroup();

        userNameDescriptionLabel.setText("Ваш ник: ");
        userNameDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userNameLabel.setFont(userNameLabel.getFont().deriveFont(14f));

        onlineUsersLabel.setText("Пользователи онлайн");
        onlineUsersLabel.setHorizontalAlignment(SwingConstants.CENTER);

        onlineUsersScrollPane.setViewportView(onlineUsersList);
        onlineUsersList.setFont(onlineUsersList.getFont().deriveFont(14f));

        mainChatTextArea.setEditable(false);
        mainChatScrollPane.setViewportView(mainChatTextArea);
        mainChatTextArea.setFont(mainChatTextArea.getFont().deriveFont(14f));

        sendMessageAreaScrollPane.setViewportView(sendMessageTextArea);

        connectButton.setText("Присоединиться");
        connectButton.addActionListener(e -> {
            ServerConnectionFrame serverConnectionFrame = createServerConnectionDialog();
            serverConnectionFrame.init();
            serverConnectionFrame.show();
        });

        disconnectButton.setText("Отключиться");
        disconnectButton.addActionListener(e -> disconnect());
        disconnectButton.setEnabled(false);

        sendMessageButton.setText("Отправить сообщение");
        sendMessageButton.addActionListener(e -> {
            controller.sendChatMessage(sendMessageTextArea.getText());
            sendMessageTextArea.setText(null);
        });

        sendMessageTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //meh
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    sendMessageButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //meh
            }
        });

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                controller.processClientCloseRequest();
            }
        });

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(mainFrame.getOwner());
        mainFrame.setResizable(false);
    }

    void prepareDialogFrame(JDialog dialog) {
        dialog.pack();
        dialog.setModal(true);
        dialog.setLocationRelativeTo(dialog.getOwner());
        dialog.setResizable(false);
    }

    JFrame getMainFrame() {
        return mainFrame;
    }

    ServerConnectionFrame createServerConnectionDialog() {
        return new ServerConnectionFrame(this, controller);
    }

    @Override
    public void requestUserName() {
        var userAuthorizationFrame = new UserAuthorizationFrame(this, controller);
        userAuthorizationFrame.init();
        userAuthorizationFrame.show();
    }

    @Override
    public void processNameDeclined() {
        ErrorFrame errorFrame = new ErrorFrame(this, "Данный ник уже занят!");
        errorFrame.show();
        requestUserName();
    }

    @Override
    public void processSuccessAuthentication(String authorizedUserName) {
        userNameLabel.setText(authorizedUserName);
        connectButton.setEnabled(false);
        disconnectButton.setEnabled(true);
    }

    @Override
    public void showChatMessage(String message) {
        mainChatTextArea.append(message + System.lineSeparator());
    }

    @Override
    public void showNewUserAdditionMessage(String userName) {
        onlineUsersListModel.addElement(userName);
        showChatMessage("Пользователь " + userName + " присоединился к чату!");
    }

    @Override
    public void showUserRemovingMessage(String userName) {
        onlineUsersListModel.removeElement(userName);
        showChatMessage("Пользователь " + userName + " покинул чат!");
    }

    @Override
    public void dispose() {
        mainFrame.dispose();
        System.exit(0);
    }

    @Override
    public void showMessageAboutConnectionFailure(boolean isUserNameWasAuthorized) {
        if (isUserNameWasAuthorized) {
            offerReconnection();
        } else {
            var connectionProblemFrame = new ConnectionProblemFrame(this, controller);
            connectionProblemFrame.initConnectionFailureVersion();
            connectionProblemFrame.show();
        }
    }

    @Override
    public void offerReconnection() {
        processDisconnect();
        var connectionProblemFrame = new ConnectionProblemFrame(this, controller);
        connectionProblemFrame.initConnectionLostVersion();
        connectionProblemFrame.show();
    }

    void disconnect() {
        processDisconnect();
        controller.disconnect();
    }

    private void processDisconnect() {
        userNameLabel.setText(null);
        mainChatTextArea.setText(null);
        sendMessageTextArea.setText(null);
        onlineUsersListModel.removeAllElements();
        connectButton.setEnabled(true);
        disconnectButton.setEnabled(false);
    }

    private void initHorizontalGroup() {
        mainFrameContentPaneLayout.setHorizontalGroup(
                mainFrameContentPaneLayout.createParallelGroup()
                        .addGroup(mainFrameContentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainFrameContentPaneLayout.createParallelGroup()
                                        .addGroup(mainFrameContentPaneLayout.createSequentialGroup()
                                                .addGroup(mainFrameContentPaneLayout.createParallelGroup()
                                                        .addGroup(mainFrameContentPaneLayout.createSequentialGroup()
                                                                .addComponent(sendMessageAreaScrollPane,
                                                                        GroupLayout.PREFERRED_SIZE, 478,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addGap(24, 24, 24)
                                                                .addComponent(sendMessageButton,
                                                                        GroupLayout.PREFERRED_SIZE, 167,
                                                                        GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(mainChatScrollPane,
                                                                GroupLayout.PREFERRED_SIZE, 669,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addGap(10, 10, 10))
                                        .addGroup(mainFrameContentPaneLayout.createSequentialGroup()
                                                .addComponent(userNameDescriptionLabel, GroupLayout.PREFERRED_SIZE,
                                                        95, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(userNameLabel, GroupLayout.PREFERRED_SIZE,
                                                        85, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(connectButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(disconnectButton)
                                                .addGap(44, 44, 44)))
                                .addGroup(mainFrameContentPaneLayout.createParallelGroup()
                                        .addComponent(onlineUsersScrollPane, GroupLayout.PREFERRED_SIZE,
                                                137, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(onlineUsersLabel, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(16, 16, 16))
        );
    }

    private void initVerticalGroup() {
        mainFrameContentPaneLayout.setVerticalGroup(
                mainFrameContentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, mainFrameContentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainFrameContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(userNameDescriptionLabel,
                                                GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(userNameLabel,
                                                GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(disconnectButton)
                                        .addComponent(connectButton, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(onlineUsersLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainFrameContentPaneLayout.createParallelGroup()
                                        .addGroup(mainFrameContentPaneLayout.createSequentialGroup()
                                                .addComponent(mainChatScrollPane,
                                                        GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addGroup(mainFrameContentPaneLayout.createParallelGroup()
                                                        .addComponent(sendMessageButton,
                                                                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                        .addGroup(mainFrameContentPaneLayout.createSequentialGroup()
                                                                .addComponent(sendMessageAreaScrollPane,
                                                                        GroupLayout.PREFERRED_SIZE, 81,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addComponent(onlineUsersScrollPane, GroupLayout.DEFAULT_SIZE,
                                                494, Short.MAX_VALUE))
                                .addGap(27, 27, 27))
        );
    }
}
