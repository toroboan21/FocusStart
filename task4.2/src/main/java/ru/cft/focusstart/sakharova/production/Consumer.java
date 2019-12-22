package ru.cft.focusstart.sakharova.production;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;

@Slf4j
@RequiredArgsConstructor
public class Consumer implements Runnable {
    private final Queue<Resource> storage;
    private final Object lock;
    private final int consumptionTime;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(consumptionTime);
                removeResource();
            } catch (InterruptedException e) {
                log.error("Ошибка при работе потребителя №{}", Thread.currentThread().getId(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void removeResource() throws InterruptedException {
        synchronized (lock) {
            while (storage.isEmpty()) {
                lock.wait();
            }
            Resource resource = storage.poll();

            log.info("Потребитель № {} забрал со склада ресурс № {} . Заполненность склада - {}.",
                    Thread.currentThread().getId(), resource.getId(), storage.size());

            lock.notifyAll();
        }
    }
}
