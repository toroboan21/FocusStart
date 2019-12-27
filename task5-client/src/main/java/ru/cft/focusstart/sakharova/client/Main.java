package ru.cft.focusstart.sakharova.client;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.client.common.ChatView;
import ru.cft.focusstart.sakharova.client.common.Controller;
import ru.cft.focusstart.sakharova.client.common.Model;
import ru.cft.focusstart.sakharova.client.controller.ChatController;
import ru.cft.focusstart.sakharova.client.model.ChatClient;
import ru.cft.focusstart.sakharova.client.view.SwingChatView;

import javax.swing.*;

@Slf4j
public class Main {

    public static void main(String[] args) {
        Model model = new ChatClient();
        Controller controller = new ChatController(model);
        ChatView chatView = new SwingChatView(controller);
        model.setChatView(chatView);
        SwingUtilities.invokeLater(model::initChatView);
    }
}