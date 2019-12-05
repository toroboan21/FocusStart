package ru.cft.focusstart.sakharova.task3.model;

import lombok.Getter;
import ru.cft.focusstart.sakharova.task3.common.CellContent;
import ru.cft.focusstart.sakharova.task3.common.CellState;
import ru.cft.focusstart.sakharova.task3.common.Observer;

import java.util.ArrayList;
import java.util.List;

@Getter
class MinesMap {
    private List<Cell> minedCells;
    private final Observer observer;

    MinesMap(int minesNumber, Observer observer) {
        minedCells = new ArrayList<>(minesNumber);
        this.observer = observer;
    }

    void addMine(Cell mine) {
        minedCells.add(mine);
    }

    void openNotFlaggedMinesAfterDetonation(Cell detonatedCell) {
        detonatedCell.setCellContent(CellContent.DETONATED);
        minedCells.stream().filter(mine -> mine.getCellState() != CellState.FLAGGED).forEach(mine -> {
            mine.setCellState(CellState.OPENED);
            observer.showCellContent(mine.getX(), mine.getY(), mine.getCellContent());
        });
    }
}
