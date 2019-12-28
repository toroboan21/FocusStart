package ru.cft.focusstart.sakharova.task3.view;

import ru.cft.focusstart.sakharova.task3.common.*;
import ru.cft.focusstart.sakharova.task3.controller.Controller;
import ru.cft.focusstart.sakharova.task3.view.iconsmanager.IconsManager;
import ru.cft.focusstart.sakharova.task3.view.menu.CustomMode;
import ru.cft.focusstart.sakharova.task3.view.menu.HighScoresMenu;
import ru.cft.focusstart.sakharova.task3.view.menu.Menu;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MinesweeperSwingView implements MinesweeperView {

    private JFrame mainFrame;
    private Display display;
    private Minefield minefield;
    private HighScoresMenu highScoresMenu;
    private CustomMode customModeFrame;

    private final Controller controller;
    private final ListenerCreator listenerCreator;

    public MinesweeperSwingView(Controller controller) {
        this.controller = controller;
        listenerCreator = new ListenerCreator(controller, this);
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
    public void startGame() {
        controller.startTimer();
    }

    private void createMinefield(int rowsNumber, int columnsNumber) {
        minefield = new Minefield(rowsNumber, columnsNumber, listenerCreator);
        mainFrame.add(minefield.getPlayingField(), BorderLayout.CENTER);
    }

    private void createMenu() {
        Menu menu = new Menu(listenerCreator);
        mainFrame.setJMenuBar(menu.getMenuBar());
        customModeFrame = new CustomMode(listenerCreator);
        highScoresMenu = new HighScoresMenu(listenerCreator);
    }

    private void createDisplay() {
        display = new Display(listenerCreator);
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
        highScoresMenu.notifyPlayerAboutRecord();
    }

    @Override
    public void showCustomSettings(DifficultyMode difficultyMode) {
        customModeFrame.show
                (difficultyMode.getRowsNumber(), difficultyMode.getColumnsNumber(), difficultyMode.getMinesNumber());
    }

    @Override
    public void showHighScores(Map<String, Score> highScores) {
        highScoresMenu.showHighScoresFrame(highScores);
    }

    @Override
    public void hideHighScoreNotification() {
        highScoresMenu.hideNotifyFrame();
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
