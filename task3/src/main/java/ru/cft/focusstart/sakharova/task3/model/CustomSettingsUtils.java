package ru.cft.focusstart.sakharova.task3.model;

import lombok.experimental.UtilityClass;

@UtilityClass
class CustomSettingsUtils {
    private static final int MIN_ROWS_NUMBER = 9;
    private static final int MAX_ROWS_NUMBER = 24;

    private static final int MIN_COLUMNS_NUMBER = 9;
    private static final int MAX_COLUMNS_NUMBER = 30;

    private static final int MIN_MINES_NUMBER = 10;

    static int fitRowsNumber(int rowsNumber) {
        if (rowsNumber < MIN_ROWS_NUMBER) {
            return MIN_ROWS_NUMBER;
        }
        if (rowsNumber > MAX_ROWS_NUMBER) {
            return MAX_ROWS_NUMBER;
        }
        return rowsNumber;
    }

    static int fitColumnsNumber(int columnsNumber) {
        if (columnsNumber < MIN_COLUMNS_NUMBER) {
            return MIN_COLUMNS_NUMBER;
        }
        if (columnsNumber > MAX_COLUMNS_NUMBER) {
            return MAX_COLUMNS_NUMBER;
        }
        return columnsNumber;
    }

    static int fitMinesNumber(int rowsNumber, int columnNumbers, int minesNumber) {
        if (minesNumber < MIN_MINES_NUMBER) {
            return MIN_MINES_NUMBER;
        }

        int maxMinesNumber = countMaxMinesNumber(rowsNumber, columnNumbers);

        if (minesNumber > maxMinesNumber) {
            return maxMinesNumber;
        }

        return minesNumber;
    }

    private static int countMaxMinesNumber(int rowsNumber, int columnNumbers) {
        return (rowsNumber - 1) * (columnNumbers - 1);
    }
}
