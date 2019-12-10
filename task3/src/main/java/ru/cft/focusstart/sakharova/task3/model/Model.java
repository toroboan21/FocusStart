package ru.cft.focusstart.sakharova.task3.model;

import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.common.MinesweeperView;
import ru.cft.focusstart.sakharova.task3.common.Score;

import java.util.Map;

public interface Model {

    void prepareGame();

    void startGame(int x, int y);

    void openCell(int x, int y);

    void restartGameWithNewDifficulty(DifficultyMode difficultyMode);

    void restartGame();

    void restartGameWithCustomSettings(int numberOfRows, int numberOfColumns, int numberOfMines);

    void setFlag(int x, int y);

    void setMinesweeperView(MinesweeperView minesweeperView);

    void initMinesweeperView();

    void openNotFlaggedNeighbours(int x, int y);

    Map<DifficultyMode, Score> getHighScores();

    void writeNewHighScore(String text);

    void resetHighScores();

    void exitGame();

    void setTime(long timeSpent);

    DifficultyMode getCurrentDifficultyMode();
}
