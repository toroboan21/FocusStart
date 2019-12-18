package ru.cft.focusstart.sakharova.task3.controller;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.common.Score;
import ru.cft.focusstart.sakharova.task3.common.StandardDifficultyModes;
import ru.cft.focusstart.sakharova.task3.model.Model;

import java.util.Map;

@Slf4j
public class Controller {
    private final Model model;
    private final Timer timer;

    public Controller(Model model) {
        this.model = model;
        this.timer = new Timer(model);
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void restartTimer() {
        timer.reset();
    }

    public void openCell(int x, int y) {
        model.openCell(x, y);
    }

    public void openNotFlaggedNeighbours(int x, int y) {
        model.openNotFlaggedNeighbours(x, y);
    }

    public void setFlag(int x, int y) {
        model.setFlag(x, y);
    }

    public void restartGame() {
        model.restartGame();
    }

    public void restartGameWithNewDifficulty(StandardDifficultyModes difficultyMode) {
        model.restartGameWithNewDifficulty(difficultyMode);
    }

    public void processCustomSettings
            (String textRowsNumber, String textColumnsNumber, String textMinesNumber) {
        try {
            int customRowsNumber = Integer.parseInt(textRowsNumber);
            int customColumnsNumber = Integer.parseInt(textColumnsNumber);
            int customMinesNumber = Integer.parseInt(textMinesNumber);
            model.restartGameWithCustomSettings(customRowsNumber, customColumnsNumber, customMinesNumber);
        } catch (NumberFormatException e) {
            log.warn("Введены некорректные размеры поля", e);
            model.restartGame();
        }
    }

    public Map<String, Score> getHighScores() {
        return model.getHighScores();
    }

    public void exitGame() {
        model.exitGame();
    }

    public void resetHighScores() {
        model.resetHighScores();
    }

    public void writeNewHighScore(String userName) {
        model.writeNewHighScore(userName);
    }

    public DifficultyMode getDifficultyMode() {
        return model.getCurrentDifficultyMode();
    }
}
