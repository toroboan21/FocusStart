package ru.cft.focusstart.sakharova.production;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
public class ProducerConsumerManager {
    private final int productionTime;
    private final int consumptionTime;
    private final int producerThreadsNumber;
    private final int consumerThreadsNumber;
    private final int storageSize;

    private final Object lock = new Object();

    private final List<Thread> threads;
    private final Queue<Resource> storage;

    public ProducerConsumerManager(int productionTime, int consumptionTime, int producerThreadsNumber,
                                   int consumerThreadsNumber, int storageSize) {
        this.productionTime = productionTime;
        this.consumptionTime = consumptionTime;
        this.producerThreadsNumber = producerThreadsNumber;
        this.consumerThreadsNumber = consumerThreadsNumber;
        this.storageSize = storageSize;

        threads = new ArrayList<>(producerThreadsNumber + consumerThreadsNumber);
        storage = new LinkedList<>();
    }

    private void createProducerThreads() {
        for (int i = 1; i <= producerThreadsNumber; i++) {
            Thread producer = new Thread(new Producer(storage, lock, storageSize, productionTime));
            threads.add(producer);
            producer.start();
        }
    }

    private void createConsumerThreads() {
        for (int i = 1; i <= consumerThreadsNumber; i++) {
            Thread consumer = new Thread(new Consumer(storage, lock, consumptionTime));
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
}
