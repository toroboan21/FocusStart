package ru.cft.focusstart.sakharova.task3.controller;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.model.Model;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@Slf4j
public class Controller {
    private final Model model;
    private final Timer timer;

    public Controller(Model model) {
        this.model = model;
        this.timer = new Timer(model);
    }

    public MouseListener createListenerForPlayingField(int x, int y) {
        return new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        model.openCell(x, y);
                        break;
                    case MouseEvent.BUTTON2:
                        model.openNotFlaggedNeighbours(x, y);
                        break;
                    case MouseEvent.BUTTON3:
                        model.setFlag(x, y);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public ActionListener createListenerForRestartButton() {
        return e -> model.restartGame();
    }

    public ActionListener createListenerForMenuButton() {
        return e -> {
            JMenuItem button = (JMenuItem) e.getSource();

            switch (MenuCommands.valueOf(button.getActionCommand())) {
                case RESTART:
                    model.restartGame();
                    break;

                case SWITCH_TO_BEGINNER_MODE:
                    model.restartGameWithNewDifficulty(DifficultyMode.BEGINNER);
                    break;

                case SWITCH_TO_INTERMEDIATE_MODE:
                    model.restartGameWithNewDifficulty(DifficultyMode.INTERMEDIATE);
                    break;

                case SWITCH_TO_EXPERT_MODE:
                    model.restartGameWithNewDifficulty(DifficultyMode.EXPERT);
                    break;

                case SWITCH_TO_CUSTOM_MODE:
                    model.showCustomSettings();
                    break;

                case HIGH_SCORES:
                    model.showHighScores();
                    break;

                case EXIT:
                    model.exitGame();
                    break;

                default:
                    break;
            }
        };
    }

    public ActionListener createListenerForResetHighScoresButton() {
        return e -> model.resetHighScores();
    }

    public ActionListener createListenerForRecordsManNameApply(JTextField fieldForName) {
        return e -> model.writeNewHighScore(fieldForName.getText());
    }

    public ActionListener createListenerForCustomSettings
            (JTextField textRowsNumber, JTextField textColumnsNumber, JTextField textMinesNumber) {
        return e -> {
            try {
                int customRowsNumber = Integer.parseInt(textRowsNumber.getText());
                int customColumnsNumber = Integer.parseInt(textColumnsNumber.getText());
                int customMinesNumber = Integer.parseInt(textMinesNumber.getText());

                model.restartGameWithCustomSettings(customRowsNumber, customColumnsNumber, customMinesNumber);
            } catch (NumberFormatException ex) {
                log.warn("Введены некорректные размеры поля", e);
                model.restartGame();
            }
            model.hideCustomSettingsInput();
        };
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
}
