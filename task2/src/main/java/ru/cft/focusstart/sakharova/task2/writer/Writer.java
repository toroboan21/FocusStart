package ru.cft.focusstart.sakharova.task2.writer;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.task2.args.ProgramArgs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Writer {

    public void write(ProgramArgs programArgs, String description) throws IOException {
        if (programArgs.isFileOutput()) {
            writeInFile(description, programArgs.getOutputPath());
        } else {
            printInConsole(description);
        }
    }

    private void printInConsole(String description) {
        System.out.println(description);
        log.info("Выходные данные выведены в консоль.");
    }

    private void writeInFile(String description, String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, StandardCharsets.UTF_8))) {
            writer.write(description);
            log.info("Выходные данные записаны в файл {}", path);
        }
    }
}
