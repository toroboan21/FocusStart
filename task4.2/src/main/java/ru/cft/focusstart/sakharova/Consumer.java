package ru.cft.focusstart.sakharova;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Consumer implements Runnable {
    private final ProducerConsumerManager manager;
    private final int consumptionTime;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(consumptionTime);
                manager.removeResource();
            } catch (InterruptedException e) {
                log.error("Ошибка при работе потребителя №{}", Thread.currentThread().getId(), e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
