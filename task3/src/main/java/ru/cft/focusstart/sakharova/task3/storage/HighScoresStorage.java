package ru.cft.focusstart.sakharova.task3.storage;

import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.common.Score;

import java.io.IOException;
import java.util.Map;

public interface HighScoresStorage {

    Object loadHighScoresFile() throws IOException, ClassNotFoundException;

    void updateHighScoresFile(Map<DifficultyMode, Score> highScores);
}
