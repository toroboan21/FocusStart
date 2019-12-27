package ru.cft.focusstart.sakharova.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Slf4j
@RequiredArgsConstructor
public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ServerHandler serverHandler;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private String authorizedUserName;
    private boolean isUserNameAuthorized;

    @Override
    public void run() {
        try (clientSocket) {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            authorizeUser();
            if (isUserNameAuthorized) {
                addUserToChat();
            } else {
                sendResponse(new Message(Message.MessageType.NAME_DECLINED));
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("Ошибка при работе с клиентом " + clientSocket.getRemoteSocketAddress() +
                    ". Сокет с клиентом будет закрыт.", e);
        } finally {
            if (isUserNameAuthorized) {
                removeAuthorizedUser();
            }
        }
    }

    private void authorizeUser() throws IOException, ClassNotFoundException {
        Message request = receiveRequest();
        Message.MessageType type = request.getType();
        log.info("Получен запрос из сокета клиента {} с типом {} .",
                clientSocket.getRemoteSocketAddress(), type);
        if (type == Message.MessageType.AUTHORIZATION) {
            String userName = request.getUserName();
            log.info("Получен запрос на добавление пользователя с ником {}.",
                    userName);
            checkUserName(userName);
        } else {
            log.error("Неверный тип запроса, полученный с адреса {} - {} . Ожидаемый тип запроса - {} . " +
                            "Соединение будет закрыто.", clientSocket.getRemoteSocketAddress(), type,
                    Message.MessageType.AUTHORIZATION);
        }
    }

    private void checkUserName(String userName) {
        if (serverHandler.isNameAvailable(userName)) {
            authorizedUserName = userName;
            isUserNameAuthorized = true;
            log.info("Сервер авторизовал пользователя с ником {}.", userName);
        } else {
            log.info("Сервер не авторизовал пользователя с ником {}. Такой пользователь уже есть в чате.",
                    userName);
        }
    }

    private void addUserToChat() throws IOException, ClassNotFoundException {
        sendResponse(new Message(Message.MessageType.NAME_APPROVED));
        serverHandler.addUser(authorizedUserName, this);
        serverHandler.sendBroadcastResponse(new Message(Message.MessageType.USER_ADDED, authorizedUserName));
        sendListOfOnlineUsersToNewUser();
        startClientListener();
    }

    private void sendListOfOnlineUsersToNewUser() throws IOException {
        for (String userName : serverHandler.getUsersOnline()) {
            if (!userName.equals(authorizedUserName)) {
                Message message = new Message(Message.MessageType.USER_ADDED, userName);
                sendResponse(message);
            }
        }
        log.info("Информация о пользователях онлайн отправлена пользователю {} .", authorizedUserName);
    }

    private void startClientListener() throws IOException, ClassNotFoundException {
        while (!Thread.currentThread().isInterrupted()) {
            Message request = receiveRequest();
            Message.MessageType type = request.getType();
            if (type == Message.MessageType.CHAT_MESSAGE) {
                serverHandler.sendBroadcastResponse(new Message(Message.MessageType.CHAT_MESSAGE,
                        request.getData(), authorizedUserName, request.getDateTime()));
            } else if (type == Message.MessageType.DISCONNECT) {
                log.info("Получен запрос на закрытие соединения: пользователь - {} .Обработчик будет остановлен",
                        authorizedUserName);
                break;
            } else {
                log.error("Неверный тип запроса, полученный с адреса {} - {} . Ожидаемые типы запроса - {}, {}.",
                        clientSocket.getRemoteSocketAddress(), type,
                        Message.MessageType.CHAT_MESSAGE, Message.MessageType.DISCONNECT);
            }
        }
    }

    private void removeAuthorizedUser() {
        serverHandler.removeUser(authorizedUserName);
        log.info("Соединение с пользователем {} закрыто.", authorizedUserName);
        isUserNameAuthorized = false;
        serverHandler.sendBroadcastResponse(new Message(Message.MessageType.USER_REMOVED, authorizedUserName));
    }

    void sendResponse(Message message) throws IOException {
        outputStream.writeObject(message);
    }

    private Message receiveRequest() throws IOException, ClassNotFoundException {
        return (Message) inputStream.readObject();
    }

    String getAuthorizedUserName() {
        return authorizedUserName;
    }
}
