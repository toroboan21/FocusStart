package ru.cft.focusstart.sakharova.task3.main;

import ru.cft.focusstart.sakharova.task3.common.Observer;
import ru.cft.focusstart.sakharova.task3.controller.Controller;
import ru.cft.focusstart.sakharova.task3.model.GameManager;
import ru.cft.focusstart.sakharova.task3.model.Model;
import ru.cft.focusstart.sakharova.task3.model.highscores.HighScoresFile;
import ru.cft.focusstart.sakharova.task3.view.SwingView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Model model = new GameManager(new HighScoresFile());
        Controller controller = new Controller(model);
        Observer frame = new SwingView(controller);
        model.setObserver(frame);
        model.init();
        SwingUtilities.invokeLater(model::initObserver);
    }
}
