package ru.cft.focusstart.sakharova.client.model;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.client.common.ChatView;
import ru.cft.focusstart.sakharova.client.common.Model;
import ru.cft.focusstart.sakharova.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ChatClient implements Model {

    private final Object lock = new Object();

    private ChatView view;
    private Socket socket;
    private ExecutorService serverListener;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private String userName;
    private boolean isUserNameWasAuthorized;
    private boolean isConnectionAlive;
    private String host;
    private int port;

    @Override
    public void setChatView(ChatView view) {
        this.view = view;
    }

    @Override
    public void initChatView() {
        view.init();
    }

    @Override
    public void connect(String host, int port) {
        setConnection(host, port);
    }

    @Override
    public void reconnect() {
        setConnection(host, port);
    }

    private void setConnection(String host, int port) {
        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            saveConnectionParams(host, port);
            isConnectionAlive = true;
            if (isUserNameWasAuthorized) {
                authenticate(userName);
            } else {
                view.requestUserName();
            }
        } catch (IOException e) {
            log.error("Ошибка при установке соединения : ", e);
            if (isConnectionCloseRequired()) {
                closeConnection();
            }
            view.showMessageAboutConnectionFailure(isUserNameWasAuthorized);
        }
    }

    private void saveConnectionParams(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void authenticate(String userName) {
        try {
            this.userName = userName;
            initServerListener();
            sendRequest(new Message(Message.MessageType.AUTHORIZATION, userName));
        } catch (IOException e) {
            processConnectionProblems(e);
        }
    }

    private void sendRequest(Message message) throws IOException {
        outputStream.writeObject(message);
    }

    private void initServerListener() {
        serverListener = Executors.newSingleThreadExecutor();
        serverListener.submit(new ServerListener(inputStream, this, view));
    }

    void processSuccessAuthentication() {
        isUserNameWasAuthorized = true;
        view.processSuccessAuthentication(userName);
    }

    @Override
    public void cancelAuthentication() {
        if (isConnectionCloseRequired()) {
            closeConnection();
        }
    }

    @Override
    public void sendChatMessage(String text) {
        try {
            sendRequest(new Message(Message.MessageType.CHAT_MESSAGE, text, ZonedDateTime.now()));
        } catch (IOException e) {
            processConnectionProblems(e);
        }
    }

    void processConnectionProblems(Exception e) {
        if (isConnectionCloseRequired()) {
            log.error("Соединение с сервером потеряно : ", e);
            closeConnection();
            view.offerReconnection();
        }
    }

    @Override
    public void disconnect() {
        if (isConnectionCloseRequired()) {
            sendDisconnectRequest();
            closeConnection();
        }
    }

    private boolean isConnectionCloseRequired() {
        synchronized (lock) {
            if (isConnectionAlive) {
                isConnectionAlive = false;
                return true;
            }
            return false;
        }
    }

    private void closeConnection() {
        if (serverListener != null) {
            serverListener.shutdownNow();
        }
        try {
            socket.close();
        } catch (IOException e) {
            log.error("Ошибка при закрытии сетевого соединения : ", e);
        }
    }

    private void sendDisconnectRequest() {
        try {
            isUserNameWasAuthorized = false;
            sendRequest(new Message(Message.MessageType.DISCONNECT));
        } catch (IOException e) {
            log.error("Ошибка при отправке запроса о завершении соединения на сервер : ", e);
        }
    }

    @Override
    public void processClientCloseRequest() {
        disconnect();
        view.dispose();
    }
}