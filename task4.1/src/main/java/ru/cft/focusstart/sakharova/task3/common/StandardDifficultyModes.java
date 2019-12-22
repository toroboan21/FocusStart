package ru.cft.focusstart.sakharova.task3.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StandardDifficultyModes {
    BEGINNER(new DifficultyMode(9, 9, 10,
            false, "Новичок")),
    INTERMEDIATE(new DifficultyMode(16, 16, 40,
            false, "Любитель")),
    EXPERT(new DifficultyMode(16, 30, 99,
            false, "Профессионал")),
    ;

    private final DifficultyMode difficultyMode;

    public static StandardDifficultyModes getDefault() {
        return BEGINNER;
    }
}
