package ru.cft.focusstart.sakharova;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class Main {
    private static final int INTERVAL_START = 1;
    private static final int INTERVAL_FINISH = 300_000_000;
    private static final int THREAD_NUMBER = 20;

    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>(THREAD_NUMBER);

        int intervalLength = INTERVAL_FINISH / THREAD_NUMBER;
        int lastNumberModifier = intervalLength - 1;

        for (int i = INTERVAL_START, threadCounter = 1; threadCounter <= THREAD_NUMBER;
             i += intervalLength, threadCounter++) {
            int lastNumber = i + lastNumberModifier;
            tasks.add(new Task(i, lastNumber));
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_NUMBER);
        try {
            List<Future<Double>> futures = threadPool.invokeAll(tasks);
            double result = 0.0;
            for (Future<Double> future : futures) {
                result += future.get();
            }
            log.info("Результат работы программы: {}", result);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Ошибка в работе программы: ", e);
            Thread.currentThread().interrupt();
        } finally {
            threadPool.shutdown();
        }
    }
}
