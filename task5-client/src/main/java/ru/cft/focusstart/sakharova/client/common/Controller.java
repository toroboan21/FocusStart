package ru.cft.focusstart.sakharova.client.common;

public interface Controller {

    void connect(String serverAddress, int serverPort);

    void reconnect();

    void authenticate(String newUserName);

    void cancelAuthentication();

    void sendChatMessage(String message);

    void disconnect();

    void processClientCloseRequest();
}