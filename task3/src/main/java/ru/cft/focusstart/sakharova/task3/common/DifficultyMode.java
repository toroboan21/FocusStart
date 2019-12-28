package ru.cft.focusstart.sakharova.task3.common;

import lombok.Getter;

@Getter
public class DifficultyMode {

    public static final String CUSTOM_MODE_NAME = "Особый";

    private final int rowsNumber;
    private final int columnsNumber;
    private final int minesNumber;
    private final boolean isCustomMode;
    private final String name;

    public DifficultyMode(int rowsNumber, int columnsNumber, int minesNumber, boolean isCustomMode, String name) {
        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
        this.minesNumber = minesNumber;
        this.isCustomMode = isCustomMode;
        this.name = name;
    }
}
