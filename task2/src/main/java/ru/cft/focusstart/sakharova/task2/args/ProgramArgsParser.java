package ru.cft.focusstart.sakharova.task2.args;

import com.beust.jcommander.JCommander;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProgramArgsParser {

    public static ProgramArgs parse(String[] args) {
        var programArgs = new ProgramArgs();
        var jCommander = JCommander.newBuilder().
                addObject(programArgs).build();
        jCommander.setProgramName("Shapes");
        jCommander.parse(args);
        return programArgs;
    }
}
