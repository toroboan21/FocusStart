package ru.cft.focusstart.sakharova.task3.model;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.common.Score;
import ru.cft.focusstart.sakharova.task3.common.StandardDifficultyModes;
import ru.cft.focusstart.sakharova.task3.storage.HighScoresStorage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
class HighScoresManager {
    private static final String ANONYMOUS_NAME = "Аноним";

    private final Map<String, Score> highScores;
    private final HighScoresStorage highScoresStorage;

    @Setter
    private long currentBestTime;

    HighScoresManager(HighScoresStorage highScoresStorage) {
        highScores = new HashMap<>();
        this.highScoresStorage = highScoresStorage;
        loadHighScores();
    }

    @SuppressWarnings("unchecked")
    private void loadHighScores() {
        try {
            Object objectFromFile = highScoresStorage.loadHighScoresFile();
            if (objectFromFile instanceof HashMap) {
                Map<String, Score> mapFromFile = (HashMap<String, Score>) objectFromFile;

                highScores.put(StandardDifficultyModes.BEGINNER.getDifficultyMode().getName(),
                        mapFromFile.get(StandardDifficultyModes.BEGINNER.getDifficultyMode().getName()));
                highScores.put(StandardDifficultyModes.INTERMEDIATE.getDifficultyMode().getName(),
                        mapFromFile.get(StandardDifficultyModes.INTERMEDIATE.getDifficultyMode().getName()));
                highScores.put(StandardDifficultyModes.EXPERT.getDifficultyMode().getName(),
                        mapFromFile.get(StandardDifficultyModes.EXPERT.getDifficultyMode().getName()));
            } else {
                createDefaultHighScoresTable();
            }
        } catch (IOException | ClassNotFoundException e) {
            log.warn("Ошибка в процессе обращения к файлу рекордов", e);
            createDefaultHighScoresTable();
        }
    }

    private void createDefaultHighScoresTable() {
        highScores.put(StandardDifficultyModes.BEGINNER.getDifficultyMode().getName(),
                new Score(ANONYMOUS_NAME, 999L));
        highScores.put(StandardDifficultyModes.INTERMEDIATE.getDifficultyMode().getName(),
                new Score(ANONYMOUS_NAME, 999L));
        highScores.put(StandardDifficultyModes.EXPERT.getDifficultyMode().getName(),
                new Score(ANONYMOUS_NAME, 999L));
    }

    Map<String, Score> getHighScores() {
        return highScores;
    }

    boolean isHighScore(long timeSpent, DifficultyMode difficultyMode) {
        Score highScore = highScores.get(difficultyMode.getName());
        return highScore.getTime() > timeSpent;
    }

    void createNewHighScore(String userName, DifficultyMode difficultyMode) {
        Score highScoreToSave = new Score(userName, this.currentBestTime);
        highScores.put(difficultyMode.getName(), highScoreToSave);
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
