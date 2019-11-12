package ru.cft.focusstart.sakharova.task2.shapes;

import java.util.List;

class Triangle extends Shape {
    private static final String NAME = "Треугольник";

    private final double sideALength;
    private final double sideBLength;
    private final double sideCLength;

    Triangle(double sideALength, double sideBLength, double sideCLength) {
        checkIsNegative(sideALength, sideBLength, sideCLength);
        isTriangleExists(sideALength, sideBLength, sideCLength);
        this.sideALength = sideALength;
        this.sideBLength = sideBLength;
        this.sideCLength = sideCLength;
    }

    private static void isTriangleExists(double sideALength, double sideBLength, double sideCLength) {
        boolean isTriangleExists = ((sideBLength + sideCLength - sideALength > EPSILON) &&
                (sideBLength + sideALength - sideCLength > EPSILON) &&
                (sideALength + sideCLength - sideBLength > EPSILON));
        if (!isTriangleExists) {
            throw new IllegalArgumentException("Треугольника с переданными длинами сторон не существует!");
        }
    }

    @Override
    public double calculatePerimeter() {
        return sideALength + sideBLength + sideCLength;
    }

    @Override
    public double calculateArea() {
        double halfOfPerimeter = calculatePerimeter() / 2;
        return Math.sqrt(halfOfPerimeter * (halfOfPerimeter - sideALength) * (halfOfPerimeter - sideBLength) *
                (halfOfPerimeter - sideCLength));
    }

    @Override
    public String getName() {
        return NAME;
    }

    private double calculateAngle(double oppositeSide, double firstOtherSide, double secondOtherSide) {
        return Math.toDegrees(Math.acos((Math.pow(firstOtherSide, 2) + Math.pow(secondOtherSide, 2) -
                Math.pow(oppositeSide, 2)) / (2 * firstOtherSide * secondOtherSide)));
    }

    private List<Double> calculateOppositeAngles() {
        return List.of(
                calculateAngle(sideALength, sideBLength, sideCLength),
                calculateAngle(sideBLength, sideALength, sideCLength),
                calculateAngle(sideCLength, sideALength, sideBLength));
    }

    @Override
    public String getDescription() {
        List<Double> oppositeAngles = calculateOppositeAngles();
        return super.getDescription() +
                "Длина стороны а: " + sideALength + MEASUREMENT_UNIT + System.lineSeparator() +
                "Длина стороны b: " + sideBLength + MEASUREMENT_UNIT + System.lineSeparator() +
                "Длина стороны c: " + sideCLength + MEASUREMENT_UNIT + System.lineSeparator() +
                "Противолежащий угол стороны а: " + oppositeAngles.get(0) + MEASUREMENT_UNIT_FOR_ANGLE +
                System.lineSeparator() +
                "Противолежащий угол стороны b: " + oppositeAngles.get(1) + MEASUREMENT_UNIT_FOR_ANGLE +
                System.lineSeparator() +
                "Противолежащий угол стороны c: " + oppositeAngles.get(2) + MEASUREMENT_UNIT_FOR_ANGLE +
                System.lineSeparator();
    }
}
