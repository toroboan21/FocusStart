package ru.cft.focusstart.sakharova.task3.common;

import ru.cft.focusstart.sakharova.task3.view.View;

import java.util.Map;

public interface MinesweeperView extends View {

    void startGame();

    void showCellContent(int x, int y, CellContent content);

    void finishGame(boolean isVictory);

    void restartGame();

    void restartGameWithNewDifficulty(int rowsNumber, int columnsNumber);

    void updateCellState(int x, int y, CellState state);

    void modifyTimer(long timeSpent);

    void showRemainingBombsNumber(int flagsNumber);

    void notifyPlayerAboutRecord();

    void showCustomSettings(DifficultyMode difficultyMode);

    void showHighScores(Map<DifficultyMode, Score> highScores);

    void hideHighScoreNotification();

    void hideCustomSettingsInput();

    void exitGame();
}
