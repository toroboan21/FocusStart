package ru.cft.focusstart.sakharova.task3.view;

import lombok.Getter;
import ru.cft.focusstart.sakharova.task3.common.CellContent;
import ru.cft.focusstart.sakharova.task3.common.CellState;
import ru.cft.focusstart.sakharova.task3.view.iconsmanager.IconsManager;

import javax.swing.*;
import java.awt.*;


class Minefield {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    private JButton[][] minesButtons;
    @Getter
    private final JPanel playingField;
    private final ListenerCreator listenerCreator;

    Minefield(int rowsNumber, int columnsNumber, ListenerCreator listenerCreator) {
        this.listenerCreator = listenerCreator;
        playingField = new JPanel();
        createField(rowsNumber, columnsNumber);
    }

    private void createField(int rowsNumber, int columnsNumber) {
        Icon icon = IconsManager.getStateIcon(CellState.CLOSED);
        playingField.setLayout(new GridLayout(rowsNumber, columnsNumber));
        minesButtons = new JButton[rowsNumber][columnsNumber];
        for (int x = 0; x < rowsNumber; x++) {
            for (int y = 0; y < columnsNumber; y++) {
                JButton button = new JButton();

                button.setIcon(icon);
                button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                button.setContentAreaFilled(false);
                button.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                button.addMouseListener(listenerCreator.createListenerForPlayingField(x, y));
                button.setFocusPainted(false);

                playingField.add(button);
                minesButtons[x][y] = button;
            }
        }
    }

    private void setNewIconAndBlockButton(int x, int y, Icon icon) {
        JButton button = minesButtons[x][y];
        button.setIcon(icon);
        blockButton(button);
    }

    private void blockButton(JButton button) {
        Icon icon = button.getIcon();
        button.setEnabled(false);
        button.setIcon(icon);
        button.setDisabledIcon(icon);
    }

    void showCellContent(int x, int y, CellContent content) {
        setNewIconAndBlockButton(x, y, IconsManager.getContentIcon(content));
    }

    void updateCellState(int x, int y, CellState state) {
        setNewIconAndBlockButton(x, y, IconsManager.getStateIcon(state));
    }

    void blockMinefieldButtons() {
        for (JButton[] buttons : minesButtons) {
            for (JButton button : buttons) {
                blockButton(button);
            }
        }
    }

    void restartGameWithSameDifficulty() {
        for (JButton[] buttons : minesButtons) {
            for (JButton button : buttons) {
                button.setEnabled(true);
                button.setIcon(IconsManager.getStateIcon(CellState.CLOSED));
            }
        }
    }

    void restartGameWithNewDifficulty(int rowsNumber, int columnsNumber) {
        playingField.removeAll();
        createField(rowsNumber, columnsNumber);
    }
}
