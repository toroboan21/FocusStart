package ru.cft.focusstart.sakharova.task2.shapes;

public abstract class Shape {
    static final double EPSILON = 1e-15;
    static final String MEASUREMENT_UNIT = "см";
    static final String MEASUREMENT_UNIT_FOR_ANGLE = "°";

    void checkIsNegative(double... parameters) {
        for (double parameter : parameters) {
            if (parameter < EPSILON) {
                throw new IllegalArgumentException("Параметры фигуры не могут быть орицательными числами!");
            }
        }
    }

    public abstract double calculatePerimeter();

    public abstract double calculateArea();

    public abstract String getName();

    public String getDescription() {
        return "Тип фигуры: " + getName() + System.lineSeparator() +
                "Площадь: " + calculateArea() + MEASUREMENT_UNIT + System.lineSeparator() +
                "Периметр: " + calculatePerimeter() + MEASUREMENT_UNIT + System.lineSeparator();
    }
}
