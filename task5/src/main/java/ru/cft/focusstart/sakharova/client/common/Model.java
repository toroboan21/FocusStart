package ru.cft.focusstart.sakharova.client.common;

public interface Model {

    void setChatView(ChatView observer);

    void initChatView();

    void connect(String serverAddress, int serverPort);

    void reconnect();

    void authenticate(String userName);

    void cancelAuthentication();

    void sendChatMessage(String text);

    void disconnect();

    void processClientCloseRequest();
}