package ru.cft.focusstart.sakharova.table.builder;

import lombok.RequiredArgsConstructor;
import ru.cft.focusstart.sakharova.table.formatter.Formatter;
import ru.cft.focusstart.sakharova.table.parameters.TableParameters;
import ru.cft.focusstart.sakharova.table.writer.ConsoleWriter;

@RequiredArgsConstructor
public class TableBuilder {

    private final ConsoleWriter writer;
    private final Formatter formatter;

    public void buildTable(TableParameters tableParameters) {
        int tableSize = tableParameters.getTableSize();
        int maxNumber = getMaxNumber(tableSize);
        int cellDigitsNumber = countDigits(maxNumber);
        String horizontalSeparator = buildHorizontalSeparator(tableSize, cellDigitsNumber);
        String cellFormat = tableParameters.getCellFormat(cellDigitsNumber);
        buildHeadOfTable(tableSize, cellDigitsNumber, cellFormat, horizontalSeparator);
        buildTableBody(tableSize, cellFormat, horizontalSeparator);
    }

    private int getMaxNumber(int tableSize) {
        return tableSize * tableSize;
    }

    private int countDigits(int maxNumber) {
        return String.valueOf(maxNumber).length();
    }

    private String buildHorizontalSeparator(int tableSize, int cellDigitsNumber) {
        int lineLength = countHorizontalSeparatorLength(tableSize, cellDigitsNumber);
        var separatorBuilder = new StringBuilder(lineLength);

        for (int i = 1; i <= TableParameters.FIRST_CELL_LENGTH; i++) {
            separatorBuilder.append(TableParameters.HORIZONTAL_SEPARATOR);
        }

        for (int column = 1; column <= tableSize; column++) {
            separatorBuilder.append(TableParameters.ROW_SEPARATOR);
            for (int i = 0; i < cellDigitsNumber; i++) {
                separatorBuilder.append(TableParameters.HORIZONTAL_SEPARATOR);
            }
        }
        return separatorBuilder.toString();
    }

    private int countHorizontalSeparatorLength(int tableSize, int cellDigitsNumber) {
        return TableParameters.FIRST_CELL_LENGTH + (tableSize * TableParameters.ROW_SEPARATOR_LENGTH) +
                (cellDigitsNumber * tableSize);
    }

    private void buildHeadOfTable(int tableSize, int cellDigitsNumber, String cellFormat,
                                  String horizontalSeparator) {
        var firstCellBuilder = new StringBuilder(cellDigitsNumber);

        for (int i = 1; i <= TableParameters.FIRST_CELL_LENGTH; i++) {
            firstCellBuilder.append(TableParameters.HEAD_FIRST_CELL_SYMBOL);
        }
        writer.write(firstCellBuilder.toString());

        for (int column = 1; column <= tableSize; column++) {
            writer.write(TableParameters.VERTICAL_SEPARATOR);
            writer.write(formatter.format(cellFormat, column));

        }
        writer.lineBreak();
        writer.writeWithLineBreak(horizontalSeparator);
    }

    private void buildTableBody(int tableSize, String cellFormat, String horizontalSeparator) {
        for (int line = 1; line <= tableSize; line++) {
            writer.write(formatter.format(TableParameters.FIRST_ROW_FORMAT, line));
            for (int column = 1; column <= tableSize; column++) {
                int result = column * line;
                writer.write(TableParameters.VERTICAL_SEPARATOR);
                writer.write(formatter.format(cellFormat, result));
            }
            writer.lineBreak();
            writer.writeWithLineBreak(horizontalSeparator);
        }
    }
}
