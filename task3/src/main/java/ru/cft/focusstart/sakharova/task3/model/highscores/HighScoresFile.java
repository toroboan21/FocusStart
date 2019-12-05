package ru.cft.focusstart.sakharova.task3.model.highscores;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.common.Score;

import java.io.*;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
public class HighScoresFile implements HighScoresStorage {

    private static final String HIGH_SCORES_FILE_PATH = "./highScores.dat";
    private static final String ANONYMOUS_NAME = "Аноним";

    private EnumMap<DifficultyMode, Score> highScores;
    private long highScore;

    public HighScoresFile() {
        highScores = new EnumMap<>(DifficultyMode.class);
        loadHighScoresFile();
    }

    private void loadHighScoresFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(HIGH_SCORES_FILE_PATH))) {
            Object objectFromFile = inputStream.readObject();

            if (objectFromFile instanceof EnumMap) {
                EnumMap mapFromFile = (EnumMap) objectFromFile;

                highScores.put(DifficultyMode.BEGINNER, (Score) mapFromFile.get(DifficultyMode.BEGINNER));
                highScores.put(DifficultyMode.INTERMEDIATE, (Score) mapFromFile.get(DifficultyMode.INTERMEDIATE));
                highScores.put(DifficultyMode.EXPERT, (Score) mapFromFile.get(DifficultyMode.EXPERT));
            } else {
                createDefaultHighScoresTable();
            }
        } catch (IOException | ClassNotFoundException e) {
            log.warn("Ошибка в процессе обращения к файлу рекордов", e);
            createDefaultHighScoresTable();
        }
    }

    private void updateHighScoresFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(HIGH_SCORES_FILE_PATH))) {
            outputStream.writeObject(highScores);
        } catch (IOException e) {
            log.warn("Ошибка в процессе обращения к файлу рекордов", e);
        }
    }

    private void createDefaultHighScoresTable() {
        highScores.put(DifficultyMode.BEGINNER, new Score(ANONYMOUS_NAME, 999L));
        highScores.put(DifficultyMode.INTERMEDIATE, new Score(ANONYMOUS_NAME, 999L));
        highScores.put(DifficultyMode.EXPERT, new Score(ANONYMOUS_NAME, 999L));
    }

    @Override
    public Map<DifficultyMode, Score> getHighScores() {
        return highScores;
    }

    @Override
    public boolean isHighScore(long timeSpent, DifficultyMode difficultyMode) {
        Score currentHighScore = highScores.get(difficultyMode);
        if (currentHighScore.getTime() > timeSpent) {
            highScore = timeSpent;
            return true;
        }
        return false;
    }

    @Override
    public void writeNewHighScore(String userName, DifficultyMode difficultyMode) {
        Score newHighScore = new Score(userName, this.highScore);
        highScores.put(difficultyMode, newHighScore);
        updateHighScoresFile();
    }

    @Override
    public void resetHighScores() {
        createDefaultHighScoresTable();
        updateHighScoresFile();
    }

    @Override
    public void saveBeforeExit() {
        updateHighScoresFile();
    }
}
