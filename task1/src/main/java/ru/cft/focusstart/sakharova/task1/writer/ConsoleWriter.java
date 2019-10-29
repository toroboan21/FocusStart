package ru.cft.focusstart.sakharova.task1.writer;

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

    public void writeWithFormat(String format, Object... lines) {
        System.out.printf(format, lines);
    }
}
