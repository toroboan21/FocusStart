package ru.cft.focusstart.sakharova.task3.view;

import ru.cft.focusstart.sakharova.task3.common.*;
import ru.cft.focusstart.sakharova.task3.controller.Controller;
import ru.cft.focusstart.sakharova.task3.view.iconsmanager.IconsManager;
import ru.cft.focusstart.sakharova.task3.view.menu.CustomMode;
import ru.cft.focusstart.sakharova.task3.view.menu.HighScores;
import ru.cft.focusstart.sakharova.task3.view.menu.Menu;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class SwingView implements Observer {

    private JFrame mainFrame;
    private Display display;
    private Minefield minefield;
    private HighScores highScoresFrame;
    private CustomMode customModeFrame;

    private final Controller controller;

    public SwingView(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void init(int rowsNumber, int columnsNumber) {
        mainFrame = new JFrame("Minesweeper");
        mainFrame.setIconImage(IconsManager.getGameIcon().getImage());
        mainFrame.setLayout(new BorderLayout());

        createMenu();
        createMinefield(rowsNumber, columnsNumber);
        createDisplay();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);

        display.setTimerStartValue();
    }

    @Override
    public void processGameStart() {
        controller.startTimer();
    }

    private void createMinefield(int rowsNumber, int columnsNumber) {
        minefield = new Minefield(rowsNumber, columnsNumber, controller);
        mainFrame.add(minefield.getPlayingField(), BorderLayout.CENTER);
    }

    private void createMenu() {
        Menu menu = new Menu(controller);
        mainFrame.setJMenuBar(menu.getMenuBar());
        customModeFrame = new CustomMode(controller);
        highScoresFrame = new HighScores(controller);
    }

    private void createDisplay() {
        display = new Display(controller);
        mainFrame.add(display.getDisplayPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void updateCellState(int x, int y, CellState state) {
        minefield.updateCellState(x, y, state);
    }

    @Override
    public void finishGame(boolean isVictory) {
        minefield.blockMinefieldButtons();
        controller.stopTimer();
        display.updateRestartButtonIcon(isVictory);
    }

    @Override
    public void restartGameWithNewDifficulty(int rowsNumber, int columnsNumber) {
        minefield.restartGameWithNewDifficulty(rowsNumber, columnsNumber);
        controller.restartTimer();
        setBaseRestartButton();
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
    }

    private void setBaseRestartButton() {
        display.setBaseRestartButtonIcon();
    }

    @Override
    public void restartGame() {
        minefield.restartGameWithSameDifficulty();
        controller.restartTimer();
        setBaseRestartButton();
    }

    @Override
    public void showCellContent(int x, int y, CellContent content) {
        minefield.showCellContent(x, y, content);
    }


    @Override
    public void modifyTimer(long timeSpent) {
        display.modifyTimer(timeSpent);
    }

    @Override
    public void showRemainingBombsNumber(int flagsNumber) {
        display.showRemainingBombsNumbers(flagsNumber);
    }

    @Override
    public void notifyPlayerAboutRecord() {
        highScoresFrame.notifyPlayerAboutRecord();
    }

    @Override
    public void showCustomSettings(int rowsNumber, int columnsNumber, int minesNumber) {
        customModeFrame.show(rowsNumber, columnsNumber, minesNumber);
    }

    @Override
    public void showHighScores(Map<DifficultyMode, Score> highScores) {
        highScoresFrame.showHighScoresFrame(highScores);
    }

    @Override
    public void hideHighScoreNotification() {
        highScoresFrame.hideNotifyFrame();
    }

    @Override
    public void hideCustomSettingsInput() {
        customModeFrame.hide();
    }

    @Override
    public void exitGame() {
        controller.startTimer();
        mainFrame.dispose();
        System.exit(0);
    }
}
