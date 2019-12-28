package ru.cft.focusstart.sakharova.task3.controller;

import ru.cft.focusstart.sakharova.task3.model.Model;

import java.util.concurrent.atomic.AtomicLong;

class Timer {

    private static final int SLEEP_TIME = 300;

    private final Model model;
    private final AtomicLong timeSpent;

    private Thread timerThread;

    Timer(Model model) {
        this.model = model;
        timeSpent = new AtomicLong();
    }

    void start() {
        timerThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    timeSpent.set(System.currentTimeMillis() - startTime);

                    model.setTime(timeSpent.get());

                    Thread.sleep(SLEEP_TIME);
                }
            } catch (InterruptedException ignore) {
                Thread.currentThread().interrupt();
            }
        });
        timerThread.start();
    }

    private void setStartValue() {
        timeSpent.set(0L);
        model.setTime(timeSpent.get());
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
