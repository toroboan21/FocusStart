package ru.cft.focusstart.sakharova.task3.model;

import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.common.Observer;

public interface Model {

    void init();

    void startGame(int x, int y);

    void openCell(int x, int y);

    void restartGameWithNewDifficulty(DifficultyMode difficultyMode);

    void restartGame();

    void restartGameWithCustomSettings(int numberOfRows, int numberOfColumns, int numberOfMines);

    void setFlag(int x, int y);

    void setObserver(Observer observer);

    void initObserver();

    void openNotFlaggedNeighbours(int x, int y);

    void showCustomSettings();

    void showHighScores();

    void writeNewHighScore(String text);

    void resetHighScores();

    void hideCustomSettingsInput();

    void exitGame();

    void setTime(long timeSpent);

}
