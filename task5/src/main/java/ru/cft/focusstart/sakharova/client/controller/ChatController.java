package ru.cft.focusstart.sakharova.client.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.client.common.Controller;
import ru.cft.focusstart.sakharova.client.common.Model;

@Slf4j
@AllArgsConstructor
public class ChatController implements Controller {

    private final Model model;

    @Override
    public void connect(String serverAddress, int serverPort) {
        model.connect(serverAddress, serverPort);
    }

    @Override
    public void reconnect() {
        model.reconnect();
    }

    @Override
    public void authenticate(String newUserName) {
        model.authenticate(newUserName);
    }

    @Override
    public void cancelAuthentication() {
        model.cancelAuthentication();
    }

    @Override
    public void sendChatMessage(String message) {
        model.sendChatMessage(message);
    }

    @Override
    public void disconnect() {
        model.disconnect();
    }

    @Override
    public void processClientCloseRequest() {
        model.processClientCloseRequest();
    }
}