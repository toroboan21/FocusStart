package ru.cft.focusstart.sakharova.task2.shapes;

class Rectangle extends Shape {
    private double length;
    private double width;

    Rectangle(double sideALength, double sideBLength) {
        checkIsNegative(sideALength, sideBLength);
        length = Math.max(sideALength, sideBLength);
        width = length == sideALength ? sideBLength : sideALength;
    }

    @Override
    public double calculatePerimeter() {
        return (length + width) * 2;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    @Override
    public String getName() {
        return "Прямоугольник";
    }

    private double calculateDiagonalLength() {
        return Math.sqrt(Math.pow(length, 2) + Math.pow(width, 2));
    }

    @Override
    public String getDescription() {
        return super.getDescription() +
                "Длина диагонали: " + calculateDiagonalLength() + MEASUREMENT_UNIT + System.lineSeparator() +
                "Длина: " + length + MEASUREMENT_UNIT + System.lineSeparator() +
                "Ширина: " + width + MEASUREMENT_UNIT + System.lineSeparator();
    }
}
