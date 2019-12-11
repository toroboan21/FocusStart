package ru.cft.focusstart.sakharova.main;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.ProducerConsumerManager;
import ru.cft.focusstart.sakharova.PropertyParser;

import java.io.IOException;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            var parser = new PropertyParser();
            var manager = new ProducerConsumerManager(parser.getProductionTime(), parser.getConsumptionTime(),
                    parser.getProducerThreadsNumber(), parser.getConsumerThreadsNumber(), parser.getStorageSize());
            manager.startThreads();
        } catch (IOException e) {
            log.error("Ошибка ввода-вывода при обращении к Property-файлу.", e);
        } catch (NumberFormatException e) {
            log.error("Значения параметров Property-файла могут быть только целыми числами", e);
        }
    }
}
