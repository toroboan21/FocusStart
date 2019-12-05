package ru.cft.focusstart.sakharova.task3.model.highscores;

import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.common.Score;

import java.util.Map;

public interface HighScoresStorage {

    Map<DifficultyMode, Score> getHighScores();

    boolean isHighScore(long timeSpent, DifficultyMode difficultyMode);

    void writeNewHighScore(String userName, DifficultyMode difficultyMode);

    void resetHighScores();

    void saveBeforeExit();
}
