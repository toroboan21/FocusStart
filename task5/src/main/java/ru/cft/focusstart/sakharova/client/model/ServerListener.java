package ru.cft.focusstart.sakharova.client.model;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.client.common.ChatView;
import ru.cft.focusstart.sakharova.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

@Slf4j
public class ServerListener implements Runnable {

    private final ObjectInputStream inputStream;
    private final ChatClient chatClient;
    private final ChatView view;
    private final MessageFormatter formatter;

    ServerListener(ObjectInputStream inputStream, ChatClient chatClient, ChatView view) {
        this.inputStream = inputStream;
        this.chatClient = chatClient;
        this.view = view;
        this.formatter = new MessageFormatter();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message response = receiveMessageFromServer();
                processServerResponse(response);
            }
        } catch (IOException | ClassNotFoundException e) {
            chatClient.processConnectionProblems(e);
        }
    }

    private Message receiveMessageFromServer() throws IOException, ClassNotFoundException {
        return (Message) inputStream.readObject();
    }

    private void processServerResponse(Message response) {
        Message.MessageType type = response.getType();
        switch (type) {
            case NAME_APPROVED:
                chatClient.processSuccessAuthentication();
                break;
            case NAME_DECLINED:
                view.processNameDeclined();
                break;
            case CHAT_MESSAGE:
                view.showChatMessage(formatter.formatChatMessage(response));
                break;
            case USER_ADDED:
                view.showNewUserAdditionMessage(response.getUserName());
                break;
            case USER_REMOVED:
                view.showUserRemovingMessage(response.getUserName());
                break;
            default:
                log.error("Неверный тип ответа, полученный от сервера - {} . " + "Ожидаемые типы ответа - {}, {}, {}.",
                        type, Message.MessageType.CHAT_MESSAGE, Message.MessageType.USER_ADDED,
                        Message.MessageType.USER_REMOVED);
        }
    }
}
