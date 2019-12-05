package ru.cft.focusstart.sakharova.task3.view;

import lombok.Getter;
import ru.cft.focusstart.sakharova.task3.controller.Controller;
import ru.cft.focusstart.sakharova.task3.view.iconsmanager.IconsManager;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

class Display {
    private static final String TIMER_FORMAT = "mm:ss";
    private static final String START_TIME_VALUE = "00:00";

    private static final int DISPLAY_ELEMENT_WIDTH = 50;
    private static final int DISPLAY_ELEMENT_HEIGHT = 30;
    private static final Dimension displayElementSize = new Dimension(DISPLAY_ELEMENT_WIDTH, DISPLAY_ELEMENT_HEIGHT);
    private final SimpleDateFormat simpleDateFormat;

    private JButton restartButton;
    private JLabel timer;
    private JLabel remainingBombs;
    @Getter
    private JPanel displayPanel;
    private Controller controller;

    Display(Controller controller) {
        this.controller = controller;
        displayPanel = new JPanel();
        createDisplay();
        this.simpleDateFormat = new SimpleDateFormat(TIMER_FORMAT);
    }

    private void createDisplay() {
        JLabel timerIcon = new JLabel();
        timerIcon.setIcon(IconsManager.getTimer());
        timerIcon.setPreferredSize(new Dimension(30, 30));

        timer = new JLabel();
        timer.setPreferredSize(displayElementSize);
        timer.setHorizontalAlignment(SwingConstants.CENTER);

        restartButton = new JButton(IconsManager.getBaseRestartButtonIcon());
        restartButton.setContentAreaFilled(false);
        restartButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        restartButton.setPreferredSize(displayElementSize);
        restartButton.setHorizontalAlignment(SwingConstants.CENTER);
        restartButton.setFocusPainted(false);
        restartButton.addActionListener(controller.createListenerForRestartButton());

        JLabel remainingBombsIcon = new JLabel();
        remainingBombsIcon.setIcon(IconsManager.getRemainingBombs());
        remainingBombsIcon.setPreferredSize(displayElementSize);
        remainingBombsIcon.setHorizontalAlignment(SwingConstants.CENTER);

        remainingBombs = new JLabel();
        remainingBombs.setPreferredSize(displayElementSize);
        remainingBombs.setHorizontalAlignment(SwingConstants.CENTER);

        displayPanel.add(timerIcon);
        displayPanel.add(timer);
        displayPanel.add(restartButton);
        displayPanel.add(remainingBombs);
        displayPanel.add(remainingBombsIcon);

        displayPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        displayPanel.add(remainingBombsIcon, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        displayPanel.add(remainingBombs, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        displayPanel.add(restartButton, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        displayPanel.add(timer, gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        displayPanel.add(timerIcon, gbc);
    }

    void updateRestartButtonIcon(boolean isVictory) {
        Icon icon = isVictory ? IconsManager.getWinnerRestartButton() : IconsManager.getDeadRestartButton();
        restartButton.setIcon(icon);
    }

    void setBaseRestartButtonIcon() {
        restartButton.setIcon(IconsManager.getBaseRestartButtonIcon());
    }

    void showRemainingBombsNumbers(int numberOfFlags) {
        remainingBombs.setText(String.valueOf(numberOfFlags));
    }

    void modifyTimer(long timeSpent) {
        timer.setText(simpleDateFormat.format(timeSpent));
    }

    void setTimerStartValue() {
        timer.setText(START_TIME_VALUE);
    }
}
