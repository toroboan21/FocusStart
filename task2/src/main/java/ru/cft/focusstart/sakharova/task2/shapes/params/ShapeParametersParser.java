package ru.cft.focusstart.sakharova.task2.shapes.params;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import ru.cft.focusstart.sakharova.task2.main.Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class ShapeParametersParser {

    private static final String SHAPE_PARAMS_SEPARATOR = " ";

    public static ShapeParameters parse(String inputPath) throws IOException {
        try (BufferedReader reader = new BufferedReader((new FileReader(inputPath, StandardCharsets.UTF_8)))) {
            String shapeType = reader.readLine();
            checkLineIsNotEmpty(shapeType, "тип фигуры");
            String shapeParameters = reader.readLine();
            checkLineIsNotEmpty(shapeParameters, "параметры фигуры");
            String[] paramsArray = shapeParameters.split(SHAPE_PARAMS_SEPARATOR);
            return new ShapeParameters(shapeType.toUpperCase(), paramsArray);
        }
    }

    private static void checkLineIsNotEmpty(String line, String lineDescription) {
        if (StringUtils.isBlank(line)) {
            throw new IllegalArgumentException("Строка входного файла, содержащая " + lineDescription +
                    ", пуста!" + Main.STOP_MESSAGE);
        }
    }
}
