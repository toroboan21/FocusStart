package ru.cft.focusstart.sakharova.task2.args;

import com.beust.jcommander.Parameter;
import lombok.Getter;

@Getter
public class ProgramArgs {

    @Parameter(names = {"-i"}, description = "Путь к входному файлу. Обязательный параметр!",
            help = true, order = 0, required = true)
    private String inputPath;

    @Parameter(names = {"-f"}, description = "Режим для вывода в файл. При выборе данного параметра " +
            "необходимо указать путь к выходному файлу", help = true, order = 1)
    private boolean fileOutput;

    @Parameter(names = {"-o"}, description = "Путь к выходному файлу",
            help = true, order = 2)
    private String outputPath;
}
