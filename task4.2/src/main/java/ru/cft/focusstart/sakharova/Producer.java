package ru.cft.focusstart.sakharova;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class Producer implements Runnable {

    private final ProducerConsumerManager manager;
    private final int productionTime;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(productionTime);
                Resource resource = new Resource(UUID.randomUUID().toString());
                manager.addResource(resource);
            } catch (InterruptedException e) {
                log.error("Ошибка при работе производителя №{}", Thread.currentThread().getId(), e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
