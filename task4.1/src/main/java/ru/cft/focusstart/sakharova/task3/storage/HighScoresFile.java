package ru.cft.focusstart.sakharova.task3.storage;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.task3.common.Score;

import java.io.*;
import java.util.Map;

@Slf4j
public class HighScoresFile implements HighScoresStorage {

    private static final String HIGH_SCORES_FILE_PATH = "highScores.dat";

    @Override
    public Object loadHighScoresFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(HIGH_SCORES_FILE_PATH))) {
            return inputStream.readObject();
        }
    }

    @Override
    public void updateHighScoresFile(Map<String, Score> highScores) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(HIGH_SCORES_FILE_PATH))) {
            outputStream.writeObject(highScores);
        } catch (IOException e) {
            log.warn("Ошибка в процессе обращения к файлу рекордов", e);
        }
    }
}
