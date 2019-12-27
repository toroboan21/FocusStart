package ru.cft.focusstart.sakharova.server;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.common.Message;
import ru.cft.focusstart.sakharova.server.properties.PropertyParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
class ServerHandler {

    private final ExecutorService threadPool;
    private final int portNumber;
    private final Map<String, ClientHandler> connectionsMap;

    ServerHandler(PropertyParser parser) {
        this.threadPool = Executors.newFixedThreadPool(parser.getNumberOfThreads());
        this.portNumber = parser.getPortNumber();
        connectionsMap = new ConcurrentHashMap<>();
    }

    void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            log.info("Сервер запущен");
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                log.info("Сервер принял входящее соединение с адреса {}", clientSocket.getRemoteSocketAddress());
                threadPool.submit(new ClientHandler(clientSocket, this));
            }
        } catch (IOException e) {
            log.error("Ошибка при работе сервера : ", e.getMessage());
        } finally {
            stopServer();
        }
    }

    void sendBroadcastResponse(Message response) {
        for (ClientHandler handler : connectionsMap.values()) {
            try {
                handler.sendResponse(response);
                log.info("Пользователю {} отправлено сообщение.", handler.getAuthorizedUserName());
            } catch (IOException e) {
                log.error("Ошибка при отправке сообщения пользователю " + handler.getAuthorizedUserName() + " :", e);
            }
        }
    }

    boolean isNameAvailable(String userName) {
        return !connectionsMap.containsKey(userName);
    }

    void addUser(String userName, ClientHandler handler) {
        connectionsMap.put(userName, handler);
    }

    void removeUser(String nameOfNewUser) {
        connectionsMap.remove(nameOfNewUser);
    }

    Set<String> getUsersOnline() {
        return connectionsMap.keySet();
    }

    private void stopServer() {
        threadPool.shutdown();
        log.info("Сервер остановлен.");
    }
}
