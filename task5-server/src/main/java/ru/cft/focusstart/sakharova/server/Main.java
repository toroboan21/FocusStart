package ru.cft.focusstart.sakharova.server;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.server.properties.PropertyParser;

import java.io.IOException;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            PropertyParser parser = new PropertyParser();
            ServerHandler handler = new ServerHandler(parser);
            handler.startServer();
        } catch (IOException e) {
            log.error("Ошибка ввода-вывода при обращении к Property-файлу.", e);
        } catch (NumberFormatException e) {
            log.error("Значения параметров Property-файла могут быть только целыми числами", e);
        }
    }
}