package ru.cft.focusstart.sakharova.task2.shapes;

class Circle extends Shape {
    private double radius;

    Circle(double radius) {
        checkIsNegative(radius);
        this.radius = radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public String getName() {
        return "Круг";
    }

    private double calculateDiameter() {
        return radius * 2;
    }

    @Override
    public String getDescription() {
        return super.getDescription() +
                "Радиус: " + radius + MEASUREMENT_UNIT + System.lineSeparator() +
                "Диаметр: " + calculateDiameter() + MEASUREMENT_UNIT + System.lineSeparator();
    }
}
