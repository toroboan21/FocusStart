package ru.cft.focusstart.sakharova.table.writer;

public class ConsoleWriter {

    public void write(String line) {
        System.out.print(line);
    }

    public void lineBreak() {
        System.out.print(System.lineSeparator());
    }

    public void writeWithLineBreak(String line) {
        System.out.println(line);
    }
}
