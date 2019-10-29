package ru.cft.focusstart.sakharova.task1.parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public
class TableParameters {

    public static final String VERTICAL_SEPARATOR = "|";
    public static final String HORIZONTAL_SEPARATOR = "-";
    public static final String ROW_SEPARATOR = "+";
    public static final String FIRST_ROW_FORMAT = "%2d";
    public static final String HEAD_FIRST_CELL_SYMBOL = " ";

    public static final int FIRST_CELL_LENGTH = 2;
    public static final int ROW_SEPARATOR_LENGTH = ROW_SEPARATOR.length();

    private int tableSize;

    public String getCellFormat(int cellDigitsNumber) {
        return "%" + cellDigitsNumber + "d";
    }
}
