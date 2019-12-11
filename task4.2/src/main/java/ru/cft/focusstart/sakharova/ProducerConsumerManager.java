package ru.cft.focusstart.sakharova;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProducerConsumerManager {
    private final int productionTime;
    private final int consumptionTime;
    private final int producerThreadsNumber;
    private final int consumerThreadsNumber;
    private final int storageSize;

    private final Object lock = new Object();

    private final List<Thread> threads;
    private final List<Resource> storage;

    public ProducerConsumerManager(int productionTime, int consumptionTime, int producerThreadsNumber,
                                   int consumerThreadsNumber, int storageSize) {
        this.productionTime = productionTime;
        this.consumptionTime = consumptionTime;
        this.producerThreadsNumber = producerThreadsNumber;
        this.consumerThreadsNumber = consumerThreadsNumber;
        this.storageSize = storageSize;

        threads = new ArrayList<>(producerThreadsNumber + consumerThreadsNumber);
        storage = new ArrayList<>(storageSize);
    }

    private void createProducerThreads() {
        for (int i = 1; i <= producerThreadsNumber; i++) {
            Thread producer = new Thread(new Producer(this, productionTime));
            threads.add(producer);
            producer.start();
        }
    }

    private void createConsumerThreads() {
        for (int i = 1; i <= consumerThreadsNumber; i++) {
            Thread consumer = new Thread(new Consumer(this, consumptionTime));
            threads.add(consumer);
            consumer.start();
        }
    }

    public void startThreads() {
        createConsumerThreads();
        createProducerThreads();
    }

    public void stopThreads() {
        threads.forEach(Thread::interrupt);
    }

    void addResource(Resource resource) throws InterruptedException {
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

    void removeResource() throws InterruptedException {
        synchronized (lock) {
            while (storage.isEmpty()) {
                lock.wait();
            }
            Resource resource = storage.remove(0);

            log.info("Потребитель № {} забрал со склада ресурс № {} . Заполненность склада - {}.",
                    Thread.currentThread().getId(), resource.getId(), storage.size());

            lock.notifyAll();
        }
    }
}
