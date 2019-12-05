package ru.cft.focusstart.sakharova.task3.common;

import java.util.Map;

public interface Observer {
    void init(int rowsNumber, int columnsNumber);

    void processGameStart();

    void showCellContent(int x, int y, CellContent content);

    void finishGame(boolean isVictory);

    void restartGame();

    void restartGameWithNewDifficulty(int rowsNumber, int columnsNumber);

    void updateCellState(int x, int y, CellState state);

    void modifyTimer(long timeSpent);

    void showRemainingBombsNumber(int flagsNumber);

    void notifyPlayerAboutRecord();

    void showCustomSettings(int rowsNumber, int columnsNumber, int minesNumber);

    void showHighScores(Map<DifficultyMode, Score> highScores);

    void hideHighScoreNotification();

    void hideCustomSettingsInput();

    void exitGame();
}
