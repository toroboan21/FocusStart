package ru.cft.focusstart.sakharova.task3.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.cft.focusstart.sakharova.task3.common.CellContent;
import ru.cft.focusstart.sakharova.task3.common.CellState;

@Getter
@EqualsAndHashCode
class Cell {
    private int x;
    private int y;

    @Setter
    private CellState cellState;
    @Setter
    private CellContent cellContent;

    @EqualsAndHashCode.Exclude
    private int minesAroundNumber;

    Cell(int x, int y, CellState cellState, CellContent cellContent) {
        this.x = x;
        this.y = y;
        this.cellState = cellState;
        this.cellContent = cellContent;
    }

    void increaseMinesAroundNumber() {
        minesAroundNumber++;
    }
}
