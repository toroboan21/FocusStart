package ru.cft.focusstart.sakharova;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class Main {
    private static final int INPUT_PARAMETER = 300000000;
    private static final int THREAD_NUMBER = 20;
    private static final int INTERVAL_START = 1;

    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>(THREAD_NUMBER);

        int intervalLength = INPUT_PARAMETER / THREAD_NUMBER;
        int lastNumberModifier = intervalLength - 1;

        for (int i = INTERVAL_START, j = 1; j <= THREAD_NUMBER; i += intervalLength, j++) {
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
