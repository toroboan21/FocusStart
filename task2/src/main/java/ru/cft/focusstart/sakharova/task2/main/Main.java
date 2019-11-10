package ru.cft.focusstart.sakharova.task2.main;

import com.beust.jcommander.ParameterException;
import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.task2.args.ProgramArgsParser;
import ru.cft.focusstart.sakharova.task2.args.ProgramArgsValidator;
import ru.cft.focusstart.sakharova.task2.shapes.Shape;
import ru.cft.focusstart.sakharova.task2.shapes.ShapeFactory;
import ru.cft.focusstart.sakharova.task2.shapes.params.ShapeParametersParser;
import ru.cft.focusstart.sakharova.task2.writer.Writer;

import java.io.IOException;

@Slf4j
public class Main {
    public static final String STOP_MESSAGE = "Работа программы будет завершена.";

    public static void main(String[] args) {
        try {
            var programArgs = ProgramArgsParser.parse(args);
            ProgramArgsValidator.validate(programArgs);
            log.info("Параметры фигуры будут взяты из файла {}", programArgs.getInputPath());
            var shapeParameters = ShapeParametersParser.parse(programArgs.getInputPath());
            var shapeFactory = new ShapeFactory();
            Shape shape = shapeFactory.create(shapeParameters);
            Writer writer = new Writer();
            writer.write(programArgs, shape.getDescription());
            System.out.println("Работа программы успешно завершена!");
            log.info("Работа программы была завершена успешно.", programArgs.getInputPath());
        } catch (ParameterException e) {
            log.error("Ошибка при обработке параметров:", e);
            System.out.println("Ошибка при вводе параметров! Список параметров:");
            e.usage();
        } catch (IllegalArgumentException | IOException e) {
            log.error("Ошибка при работе программы:", e);
            System.out.println("Ошибка при работе программы! " + e.getMessage());
            System.out.println(STOP_MESSAGE);
        }
    }
}
