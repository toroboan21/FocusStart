package ru.cft.focusstart.sakharova.task3.view.menu;

import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.common.Score;
import ru.cft.focusstart.sakharova.task3.controller.Controller;
import ru.cft.focusstart.sakharova.task3.view.iconsmanager.IconsManager;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class HighScores {
    private static final String NOTIFY_TEXT = "Вы справились быстрее остальных! Введите свое имя:";

    private JFrame highScoresFrame;
    private GridBagConstraints highScoresFrameConstraints;
    private JFrame notifyFrame;
    private JLabel beginnerModeName;
    private JLabel beginnerModeResult;
    private JLabel beginnerModeRecordsMan;
    private JLabel intermediateModeName;
    private JLabel intermediateModeResult;
    private JLabel intermediateModeRecordsMan;
    private JLabel expertModeName;
    private JLabel expertModeResult;
    private JLabel expertModeRecordsMan;

    private Controller controller;

    public HighScores(Controller controller) {
        this.controller = controller;
        highScoresFrameConstraints = new GridBagConstraints();
        setDefaultConstraintsValues();
        createHighScoresFrame();
        createNotifyFrame();
    }

    private void createNotifyFrame() {
        notifyFrame = new JFrame();

        JLabel notifyText = new JLabel(NOTIFY_TEXT);
        JTextField fieldForName = new JTextField();
        JButton notifyOkButton = new JButton("Принять");

        notifyOkButton.addActionListener(controller.createListenerForRecordsManNameApply(fieldForName));

        GridBagLayout layout = new GridBagLayout();
        notifyFrame.setLayout(layout);

        GridBagConstraints notifyConstraints = new GridBagConstraints();

        notifyConstraints.anchor = GridBagConstraints.NORTH;
        notifyConstraints.gridwidth = GridBagConstraints.REMAINDER;
        notifyConstraints.insets = new Insets(5, 0, 0, 0);
        notifyFrame.add(notifyText, notifyConstraints);

        notifyConstraints.fill = GridBagConstraints.NONE;
        notifyConstraints.ipadx = 80;
        notifyFrame.add(fieldForName, notifyConstraints);

        notifyConstraints.ipadx = 1;
        notifyFrame.add(notifyOkButton, notifyConstraints);

        notifyFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void createHighScoresFrame() {
        highScoresFrame = new JFrame();
        highScoresFrame.setIconImage(IconsManager.getHighScoresIcon().getImage());

        beginnerModeName = new JLabel();
        beginnerModeResult = new JLabel();
        beginnerModeRecordsMan = new JLabel();

        intermediateModeName = new JLabel();
        intermediateModeResult = new JLabel();
        intermediateModeRecordsMan = new JLabel();

        expertModeName = new JLabel();
        expertModeResult = new JLabel();
        expertModeRecordsMan = new JLabel();

        JButton resetHighScoresButton = new JButton("Сбросить результаты");
        resetHighScoresButton.addActionListener(controller.createListenerForResetHighScoresButton());

        GridBagLayout layout = new GridBagLayout();

        highScoresFrame.setLayout(layout);

        highScoresFrame.add(beginnerModeName, highScoresFrameConstraints);
        highScoresFrame.add(beginnerModeResult, highScoresFrameConstraints);

        setConstraintsForName();

        highScoresFrame.add(beginnerModeRecordsMan, highScoresFrameConstraints);

        setConstraintsForModeNameAndResult();

        highScoresFrame.add(intermediateModeName, highScoresFrameConstraints);
        highScoresFrame.add(intermediateModeResult, highScoresFrameConstraints);

        setConstraintsForName();

        highScoresFrame.add(intermediateModeRecordsMan, highScoresFrameConstraints);

        setConstraintsForModeNameAndResult();

        highScoresFrame.add(expertModeName, highScoresFrameConstraints);
        highScoresFrame.add(expertModeResult, highScoresFrameConstraints);

        setConstraintsForName();

        highScoresFrame.add(expertModeRecordsMan, highScoresFrameConstraints);

        setConstraintsForResetHighScoresButton();

        highScoresFrame.add(resetHighScoresButton, highScoresFrameConstraints);

        highScoresFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        highScoresFrame.pack();
        highScoresFrame.setLocationRelativeTo(null);
        highScoresFrame.setResizable(false);
    }

    private void setConstraintsForResetHighScoresButton() {
        highScoresFrameConstraints.gridwidth = 1;
        highScoresFrameConstraints.insets = new Insets(10, 10, 10, 0);
        highScoresFrameConstraints.gridwidth = GridBagConstraints.REMAINDER;
    }

    private void setConstraintsForName() {
        highScoresFrameConstraints.gridwidth = GridBagConstraints.REMAINDER;
        highScoresFrameConstraints.ipadx = 10;
    }

    private void setDefaultConstraintsValues() {
        highScoresFrameConstraints.anchor = GridBagConstraints.NORTHWEST;
        highScoresFrameConstraints.fill = GridBagConstraints.NONE;
        highScoresFrameConstraints.gridheight = 1;
        highScoresFrameConstraints.gridwidth = 1;
        highScoresFrameConstraints.gridx = GridBagConstraints.RELATIVE;
        highScoresFrameConstraints.gridy = GridBagConstraints.RELATIVE;
        highScoresFrameConstraints.insets = new Insets(10, 10, 0, 0);
    }

    private void setConstraintsForModeNameAndResult() {
        highScoresFrameConstraints.ipadx = 0;
        highScoresFrameConstraints.gridwidth = 1;
    }

    public void notifyPlayerAboutRecord() {
        notifyFrame.pack();
        notifyFrame.setLocationRelativeTo(null);
        notifyFrame.setResizable(false);
        notifyFrame.setVisible(true);
    }

    public void hideNotifyFrame() {
        notifyFrame.dispose();
    }

    public void showHighScoresFrame(Map<DifficultyMode, Score> highScores) {
        beginnerModeName.setText(DifficultyMode.BEGINNER.getName());
        Score beginnerHighScore = highScores.get(DifficultyMode.BEGINNER);
        beginnerModeResult.setText(String.valueOf(beginnerHighScore.getTime()));
        beginnerModeRecordsMan.setText(beginnerHighScore.getName());

        intermediateModeName.setText(DifficultyMode.INTERMEDIATE.getName());
        Score intermediateHighScore = highScores.get(DifficultyMode.INTERMEDIATE);
        intermediateModeResult.setText(String.valueOf(intermediateHighScore.getTime()));
        intermediateModeRecordsMan.setText(intermediateHighScore.getName());

        expertModeName.setText(DifficultyMode.EXPERT.getName());
        Score expertHighScore = highScores.get(DifficultyMode.EXPERT);
        expertModeResult.setText(String.valueOf(expertHighScore.getTime()));
        expertModeRecordsMan.setText(expertHighScore.getName());

        highScoresFrame.pack();
        highScoresFrame.setLocationRelativeTo(null);
        highScoresFrame.setVisible(true);
    }
}
