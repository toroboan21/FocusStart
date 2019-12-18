package ru.cft.focusstart.sakharova.task3.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class Score implements Serializable {
    private final String name;
    private final long time;
}
