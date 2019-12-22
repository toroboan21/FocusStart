package ru.cft.focusstart.sakharova.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
@AllArgsConstructor
public class Task implements Callable<Double> {
    private final int firstNumber;
    private final int lastNumber;

    private double calculateFunction(int x) {
        return Math.pow(Math.cos(Math.pow(Math.sin(x), 2)), 2);
    }

    @Override
    public Double call() {
        long threadId = Thread.currentThread().getId();

        log.info("Поток №{} начал вычисление функции для интервала от {} до {}.",
                threadId, firstNumber, lastNumber);
        double result = 0.0;

        for (int i = firstNumber; i <= lastNumber; i++) {
            result += calculateFunction(i);
        }
        log.info("Поток №{} закончил вычисление функции для интервала от {} до {}. Результат = {}",
                threadId, firstNumber, lastNumber, result);
        return result;
    }
}
