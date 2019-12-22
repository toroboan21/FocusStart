package ru.cft.focusstart.sakharova.task3.view.menu;

import ru.cft.focusstart.sakharova.task3.common.Score;
import ru.cft.focusstart.sakharova.task3.common.StandardDifficultyModes;
import ru.cft.focusstart.sakharova.task3.view.ListenerCreator;
import ru.cft.focusstart.sakharova.task3.view.iconsmanager.IconsManager;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class HighScoresMenu {
    private static final String NOTIFY_TEXT = "Вы справились быстрее остальных! Введите свое имя:";

    private final GridBagConstraints highScoresFrameConstraints;
    private final ListenerCreator listenerCreator;

    private JFrame highScoresFrame;
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

    public HighScoresMenu(ListenerCreator listenerCreator) {
        this.listenerCreator = listenerCreator;
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

        notifyOkButton.addActionListener(listenerCreator.createListenerForRecordsManNameApply(fieldForName));

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
        resetHighScoresButton.addActionListener(listenerCreator.createListenerForResetHighScoresButton());

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

    public void showHighScoresFrame(Map<String, Score> highScores) {
        beginnerModeName.setText(StandardDifficultyModes.BEGINNER.getDifficultyMode().getName());
        Score beginnerHighScore = highScores.get(StandardDifficultyModes.BEGINNER.getDifficultyMode().getName());
        beginnerModeResult.setText(String.valueOf(beginnerHighScore.getTime()));
        beginnerModeRecordsMan.setText(beginnerHighScore.getName());

        intermediateModeName.setText(StandardDifficultyModes.INTERMEDIATE.getDifficultyMode().getName());
        Score intermediateHighScore = highScores.get(StandardDifficultyModes.INTERMEDIATE.getDifficultyMode().getName());
        intermediateModeResult.setText(String.valueOf(intermediateHighScore.getTime()));
        intermediateModeRecordsMan.setText(intermediateHighScore.getName());

        expertModeName.setText(StandardDifficultyModes.EXPERT.getDifficultyMode().getName());
        Score expertHighScore = highScores.get(StandardDifficultyModes.EXPERT.getDifficultyMode().getName());
        expertModeResult.setText(String.valueOf(expertHighScore.getTime()));
        expertModeRecordsMan.setText(expertHighScore.getName());

        highScoresFrame.pack();
        highScoresFrame.setLocationRelativeTo(null);
        highScoresFrame.setVisible(true);
    }
}
