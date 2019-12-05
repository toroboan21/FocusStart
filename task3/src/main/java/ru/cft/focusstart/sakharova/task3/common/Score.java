package ru.cft.focusstart.sakharova.task3.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class Score implements Serializable {
    private String name;
    private long time;
}
