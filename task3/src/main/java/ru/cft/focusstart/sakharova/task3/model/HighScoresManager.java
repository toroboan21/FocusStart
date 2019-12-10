package ru.cft.focusstart.sakharova.task3.model;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.common.Score;
import ru.cft.focusstart.sakharova.task3.storage.HighScoresStorage;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
class HighScoresManager {
    private static final String ANONYMOUS_NAME = "Аноним";

    private final Map<DifficultyMode, Score> highScores;
    private final HighScoresStorage highScoresStorage;

    @Setter
    private long currentBestTime;

    HighScoresManager(HighScoresStorage highScoresStorage) {
        highScores = new EnumMap<>(DifficultyMode.class);
        this.highScoresStorage = highScoresStorage;
        loadHighScores();
    }

    private void loadHighScores() {
        try {
            Object objectFromFile = highScoresStorage.loadHighScoresFile();
            if (objectFromFile instanceof EnumMap) {
                EnumMap<DifficultyMode, Score> mapFromFile = (EnumMap<DifficultyMode, Score>) objectFromFile;

                highScores.put(DifficultyMode.BEGINNER, mapFromFile.get(DifficultyMode.BEGINNER));
                highScores.put(DifficultyMode.INTERMEDIATE, mapFromFile.get(DifficultyMode.INTERMEDIATE));
                highScores.put(DifficultyMode.EXPERT, mapFromFile.get(DifficultyMode.EXPERT));
            } else {
                createDefaultHighScoresTable();
            }
        } catch (IOException | ClassNotFoundException e) {
            log.warn("Ошибка в процессе обращения к файлу рекордов", e);
            createDefaultHighScoresTable();
        }
    }

    private void createDefaultHighScoresTable() {
        highScores.put(DifficultyMode.BEGINNER, new Score(ANONYMOUS_NAME, 999L));
        highScores.put(DifficultyMode.INTERMEDIATE, new Score(ANONYMOUS_NAME, 999L));
        highScores.put(DifficultyMode.EXPERT, new Score(ANONYMOUS_NAME, 999L));
    }

    Map<DifficultyMode, Score> getHighScores() {
        return highScores;
    }

    boolean isHighScore(long timeSpent, DifficultyMode difficultyMode) {
        Score highScore = highScores.get(difficultyMode);
        return highScore.getTime() > timeSpent;
    }

    void createNewHighScore(String userName, DifficultyMode difficultyMode) {
        Score highScoreToSave = new Score(userName, this.currentBestTime);
        highScores.put(difficultyMode, highScoreToSave);
        highScoresStorage.updateHighScoresFile(highScores);
    }

    void resetHighScores() {
        createDefaultHighScoresTable();
        highScoresStorage.updateHighScoresFile(highScores);
    }

    void saveBeforeExit() {
        highScoresStorage.updateHighScoresFile(highScores);
    }
}
