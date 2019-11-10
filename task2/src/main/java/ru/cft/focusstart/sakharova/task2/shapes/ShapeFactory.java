package ru.cft.focusstart.sakharova.task2.shapes;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focusstart.sakharova.task2.shapes.params.ShapeParameters;

@Slf4j
public class ShapeFactory {

    public Shape create(ShapeParameters shapeParameters) {
        ShapeType shapeType = defineShapeType(shapeParameters.getType());
        checkParametersNumber(shapeType.getParametersNumber(), shapeParameters.getParameters());
        try {
            switch (shapeType) {
                case CIRCLE:
                    return createCircle(shapeParameters.getParameters());
                case RECTANGLE:
                    return createRectangle(shapeParameters.getParameters());
                case TRIANGLE:
                    return createTriangle(shapeParameters.getParameters());
                default:
                    throw new IllegalArgumentException("Неверно введен тип фигуры!");
            }
        } catch (NumberFormatException e) {
            log.error("Ошибка преобразования в double.");
            throw new IllegalArgumentException("Неверно введены параметры фигуры! " +
                    "Должны быть переданы дробные или целые числа. " +
                    "В качестве десятичного разделителя нужно использовать точку!");
        }
    }

    private ShapeType defineShapeType(String type) {
        try {
            return ShapeType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неверно введен тип фигуры!", e);
        }
    }

    private void checkParametersNumber(int parametersNumber, String[] params) {
        if (parametersNumber != params.length) {
            throw new IllegalArgumentException("Введено неверное количество параметров!");
        }
    }

    private Shape createCircle(String[] parameters) {
        double radius = Double.parseDouble(parameters[0]);
        return new Circle(radius);
    }

    private Shape createRectangle(String[] parameters) {
        double sideALength = Double.parseDouble(parameters[0]);
        double sideBLength = Double.parseDouble(parameters[1]);
        return new Rectangle(sideALength, sideBLength);
    }

    private Shape createTriangle(String[] parameters) {
        double sideALength = Double.parseDouble(parameters[0]);
        double sideBLength = Double.parseDouble(parameters[1]);
        double sideCLength = Double.parseDouble(parameters[2]);
        return new Triangle(sideALength, sideBLength, sideCLength);
    }
}
