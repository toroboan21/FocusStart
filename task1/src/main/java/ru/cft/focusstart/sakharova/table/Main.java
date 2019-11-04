package ru.cft.focusstart.sakharova.table;

import ru.cft.focusstart.sakharova.table.builder.TableBuilder;
import ru.cft.focusstart.sakharova.table.formatter.Formatter;
import ru.cft.focusstart.sakharova.table.parameters.ParametersBuilder;
import ru.cft.focusstart.sakharova.table.writer.ConsoleWriter;

public class Main {

    public static void main(String[] args) {
        var writer = new ConsoleWriter();
        try {
            var parameters = ParametersBuilder.buildParameters();
            var formatter = new Formatter();
            var tableBuilder = new TableBuilder(writer, formatter);
            tableBuilder.buildTable(parameters);
        } catch (IllegalArgumentException e) {
            writer.writeWithLineBreak(e.getMessage());
        }
    }
}
