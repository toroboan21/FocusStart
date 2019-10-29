package ru.cft.focusstart.sakharova.task1;

import ru.cft.focusstart.sakharova.task1.builder.TableBuilder;
import ru.cft.focusstart.sakharova.task1.parameters.ParametersBuilder;
import ru.cft.focusstart.sakharova.task1.writer.ConsoleWriter;

public class Main {

    public static void main(String[] args) {
        var writer = new ConsoleWriter();
        try {
            var parametersBuilder = new ParametersBuilder();
            var parameters = parametersBuilder.buildParameters();
            var tableBuilder = new TableBuilder(writer);
            tableBuilder.buildTable(parameters);
        } catch (IllegalArgumentException e) {
            writer.writeWithLineBreak(e.getMessage());
        }
    }
}
