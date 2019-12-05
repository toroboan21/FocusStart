package ru.cft.focusstart.sakharova.task3.controller;

import lombok.RequiredArgsConstructor;
import ru.cft.focusstart.sakharova.task3.model.Model;

@RequiredArgsConstructor
class Timer {

    private static final int SLEEP_TIME = 300;

    private long timeSpent;
    private final Model model;
    private Thread timerThread;

    void start() {
        timerThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    timeSpent = System.currentTimeMillis() - startTime;

                    model.setTime(timeSpent);

                    Thread.sleep(SLEEP_TIME);
                }
            } catch (InterruptedException ignore) {
                Thread.currentThread().interrupt();
            }
        });
        timerThread.start();
    }

    private void setStartValue() {
        timeSpent = 0L;
        model.setTime(timeSpent);
    }

    void stop() {
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    void reset() {
        stop();
        setStartValue();
    }
}
