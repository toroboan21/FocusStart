package ru.cft.focusstart.sakharova.task2.args;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class ProgramArgsValidator {

    public static void validate(ProgramArgs programArgs) {
        if (StringUtils.isBlank(programArgs.getInputPath())) {
            throw new IllegalArgumentException("Не указан путь к входному файлу!");
        }
        if (programArgs.isFileOutput() && StringUtils.isBlank(programArgs.getOutputPath())) {
            throw new IllegalArgumentException("Не указан путь к выходному файлу!");
        }
    }
}
