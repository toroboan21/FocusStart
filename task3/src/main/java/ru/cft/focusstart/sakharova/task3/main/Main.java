package ru.cft.focusstart.sakharova.task3.main;

import ru.cft.focusstart.sakharova.task3.controller.Controller;
import ru.cft.focusstart.sakharova.task3.model.GameManager;
import ru.cft.focusstart.sakharova.task3.model.Model;
import ru.cft.focusstart.sakharova.task3.storage.HighScoresFile;
import ru.cft.focusstart.sakharova.task3.view.MinesweeperSwingView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Model model = new GameManager(new HighScoresFile());
        Controller controller = new Controller(model);
        MinesweeperSwingView view = new MinesweeperSwingView(controller);
        model.setMinesweeperView(view);
        model.prepareGame();
        SwingUtilities.invokeLater(model::initMinesweeperView);
    }
}
