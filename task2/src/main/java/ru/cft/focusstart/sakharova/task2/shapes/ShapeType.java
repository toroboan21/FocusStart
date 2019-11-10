package ru.cft.focusstart.sakharova.task2.shapes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShapeType {
    CIRCLE(1),
    RECTANGLE(2),
    TRIANGLE(3);

    private final int parametersNumber;
}
