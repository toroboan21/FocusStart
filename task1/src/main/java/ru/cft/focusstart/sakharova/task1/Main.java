package ru.cft.focusstart.sakharova.task1;

import ru.cft.focusstart.sakharova.task1.builder.TableBuilder;
import ru.cft.focusstart.sakharova.task1.formatter.Formatter;
import ru.cft.focusstart.sakharova.task1.parameters.ParametersBuilder;
import ru.cft.focusstart.sakharova.task1.writer.ConsoleWriter;

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
