package ru.cft.focusstart.sakharova.task1.builder;

import lombok.AllArgsConstructor;
import ru.cft.focusstart.sakharova.task1.parameters.TableParameters;
import ru.cft.focusstart.sakharova.task1.writer.ConsoleWriter;

import static ru.cft.focusstart.sakharova.task1.parameters.TableParameters.*;

@AllArgsConstructor
public class TableBuilder {

    private ConsoleWriter writer;

    public void buildTable(TableParameters tableParameters) {
        int tableSize = tableParameters.getTableSize();
        int cellDigitsNumber = countDigitsNumber(tableSize);
        String horizontalSeparator = buildHorizontalSeparator(tableSize, cellDigitsNumber);
        String cellFormat = tableParameters.getCellFormat(cellDigitsNumber);
        buildHeadOfTable(tableSize, cellDigitsNumber, cellFormat, horizontalSeparator);
        buildTableBody(tableSize, cellFormat, horizontalSeparator);
    }

    private int countDigitsNumber(int tableSize) {
        int maxNumber = tableSize * tableSize;
        return String.valueOf(maxNumber).length();
    }

    private String buildHorizontalSeparator(int tableSize, int cellDigitsNumber) {
        int lineLength = countHorizontalSeparatorLength(tableSize, cellDigitsNumber);
        var separatorBuilder = new StringBuilder(lineLength);

        for (int i = 1; i <= FIRST_CELL_LENGTH; i++) {
            separatorBuilder.append(HORIZONTAL_SEPARATOR);
        }

        for (int column = 1; column <= tableSize; column++) {
            separatorBuilder.append(ROW_SEPARATOR);
            for (int i = 0; i < cellDigitsNumber; i++) {
                separatorBuilder.append(HORIZONTAL_SEPARATOR);
            }
        }
        return separatorBuilder.toString();
    }

    private int countHorizontalSeparatorLength(int tableSize, int cellDigitsNumber) {
        return FIRST_CELL_LENGTH + (tableSize * ROW_SEPARATOR_LENGTH) +
                (cellDigitsNumber * tableSize);
    }

    private void buildHeadOfTable(int tableSize, int cellDigitsNumber, String cellFormat,
                                  String horizontalSeparator) {
        var firstCellBuilder = new StringBuilder(cellDigitsNumber);

        for (int i = 1; i <= FIRST_CELL_LENGTH; i++) {
            firstCellBuilder.append(HEAD_FIRST_CELL_SYMBOL);
        }
        writer.write(firstCellBuilder.toString());

        for (int column = 1; column <= tableSize; column++) {
            writer.write(VERTICAL_SEPARATOR);
            writer.writeWithFormat(cellFormat, column);
        }
        writer.lineBreak();
        writer.writeWithLineBreak(horizontalSeparator);
    }

    private void buildTableBody(int tableSize, String cellFormat, String horizontalSeparator) {
        for (int line = 1; line <= tableSize; line++) {
            writer.writeWithFormat(FIRST_ROW_FORMAT, line);
            for (int column = 1; column <= tableSize; column++) {
                int result = column * line;
                writer.write(VERTICAL_SEPARATOR);
                writer.writeWithFormat(cellFormat, result);
            }
            writer.lineBreak();
            writer.writeWithLineBreak(horizontalSeparator);
        }
    }
}
