package ru.cft.focusstart.sakharova.task3.model;

import ru.cft.focusstart.sakharova.task3.common.CellContent;
import ru.cft.focusstart.sakharova.task3.common.CellState;
import ru.cft.focusstart.sakharova.task3.common.Observer;

import java.util.ArrayList;
import java.util.List;

class FlagsMap {
    private int flagsNumber;
    private List<Cell> flaggedCells;
    private final Observer observer;

    FlagsMap(int flagsNumber, Observer observer) {
        this.flagsNumber = flagsNumber;
        flaggedCells = new ArrayList<>();
        this.observer = observer;
    }

    void setFlag(Cell currentCell) {
        switch (currentCell.getCellState()) {
            case CLOSED:
                currentCell.setCellState(CellState.FLAGGED);
                flaggedCells.add(currentCell);
                --flagsNumber;
                break;
            case FLAGGED:
                currentCell.setCellState(CellState.CLOSED);
                flaggedCells.remove(currentCell);
                ++flagsNumber;
                break;
            default:
                return;
        }
        updateFlagsOnView(currentCell);
    }

    private void updateFlagsOnView(Cell cell) {
        observer.showRemainingBombsNumber(this.flagsNumber);
        observer.updateCellState(cell.getX(), cell.getY(), cell.getCellState());
    }

    void markAllMinesAfterVictory(List<Cell> minedCells) {
        minedCells.stream().filter(minedCell -> minedCell.getCellState() != CellState.FLAGGED).forEach(minedCell -> {
            minedCell.setCellState(CellState.FLAGGED);
            flagsNumber--;
            updateFlagsOnView(minedCell);
        });
    }

    void openMistakenFlags() {
        flaggedCells.stream().filter(flaggedCell -> flaggedCell.getCellContent() != CellContent.MINE).forEach(flaggedCell -> {
            flaggedCell.setCellContent(CellContent.MISTAKE);
            flaggedCell.setCellState(CellState.OPENED);
            observer.showCellContent(flaggedCell.getX(), flaggedCell.getY(), flaggedCell.getCellContent());
        });
    }

    void showRemainingBombsNumber(int flagsNumber) {
        this.flagsNumber = flagsNumber;
        observer.showRemainingBombsNumber(flagsNumber);
    }
}
