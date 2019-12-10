package ru.cft.focusstart.sakharova.task3.view.menu;

import ru.cft.focusstart.sakharova.task3.view.ListenerCreator;

import javax.swing.*;
import java.awt.*;

public class CustomMode {
    private static final int COLUMNS_NUMBER_IN_TEXT_FIELDS = 3;

    private final JTextField rowsNumberText;
    private final JTextField columnsNumberText;
    private final JTextField minesNumberText;

    private final JFrame customModeFrame;
    private final GridBagConstraints constraints;

    public CustomMode(ListenerCreator listenerCreator) {
        customModeFrame = new JFrame();

        JLabel minefieldHeight = new JLabel("Высота: ");
        rowsNumberText = new JTextField(COLUMNS_NUMBER_IN_TEXT_FIELDS);

        JLabel minefieldWidth = new JLabel("Ширина: ");
        columnsNumberText = new JTextField(COLUMNS_NUMBER_IN_TEXT_FIELDS);

        JLabel minesCount = new JLabel("Число мин: ");
        minesNumberText = new JTextField(COLUMNS_NUMBER_IN_TEXT_FIELDS);

        JButton okButton = new JButton("Принять");

        okButton.addActionListener(listenerCreator.createListenerForCustomSettings
                (rowsNumberText, columnsNumberText, minesNumberText));

        GridBagLayout layout = new GridBagLayout();

        customModeFrame.setLayout(layout);

        constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(10, 10, 0, 0);
        customModeFrame.add(minefieldHeight, constraints);
        customModeFrame.add(columnsNumberText, constraints);

        setGridToRemainderWidth();
        constraints.ipadx = 36;
        customModeFrame.add(okButton, constraints);

        constraints.gridwidth = 1;
        constraints.ipadx = 0;
        customModeFrame.add(minefieldWidth, constraints);

        setGridToRemainderWidth();
        customModeFrame.add(rowsNumberText, constraints);

        constraints.gridwidth = 1;
        customModeFrame.add(minesCount, constraints);

        setGridToRemainderWidth();
        customModeFrame.add(minesNumberText, constraints);

        customModeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void setGridToRemainderWidth() {
        constraints.gridwidth = GridBagConstraints.REMAINDER;
    }

    public void show(int rowsNumber, int columnsNumber, int minesNumber) {
        this.rowsNumberText.setText(String.valueOf(rowsNumber));
        this.columnsNumberText.setText(String.valueOf(columnsNumber));
        this.minesNumberText.setText(String.valueOf(minesNumber));

        customModeFrame.pack();
        customModeFrame.setLocationRelativeTo(null);
        customModeFrame.setResizable(false);
        customModeFrame.setVisible(true);
    }

    public void hide() {
        customModeFrame.setVisible(false);
        customModeFrame.dispose();
    }
}
