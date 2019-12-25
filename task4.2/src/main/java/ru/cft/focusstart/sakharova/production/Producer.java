package ru.cft.focusstart.sakharova.production;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class Producer implements Runnable {
    private final Queue<Resource> storage;
    private final Object lock;
    private final int storageSize;
    private final int productionTime;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(productionTime);
                Resource resource = new Resource(UUID.randomUUID().toString());
                addResource(resource);
            } catch (InterruptedException e) {
                log.error("Ошибка при работе производителя №{}", Thread.currentThread().getId(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void addResource(Resource resource) throws InterruptedException {
        synchronized (lock) {
            while (storage.size() >= storageSize) {
                lock.wait();
            }
            storage.add(resource);

            log.info("Производитель № {} добавил на склад ресурс № {} . Заполненность склада - {}.",
                    Thread.currentThread().getId(), resource.getId(), storage.size());

            lock.notifyAll();
        }
    }
}
