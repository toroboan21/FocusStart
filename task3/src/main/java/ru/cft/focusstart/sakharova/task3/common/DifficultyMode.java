package ru.cft.focusstart.sakharova.task3.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DifficultyMode {
    BEGINNER(9, 9, 10, "Новичок"),
    INTERMEDIATE(16, 16, 40, "Любитель"),
    EXPERT(16, 30, 99, "Профессионал"),
    CUSTOM(0, 0, 0, "Особый"),
    ;

    private final int rowsNumber;
    private final int columnsNumber;
    private final int minesNumber;
    private final String name;
}
