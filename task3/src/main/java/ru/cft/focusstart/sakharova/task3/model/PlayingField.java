package ru.cft.focusstart.sakharova.task3.model;

import lombok.Getter;
import ru.cft.focusstart.sakharova.task3.common.CellContent;
import ru.cft.focusstart.sakharova.task3.common.CellState;
import ru.cft.focusstart.sakharova.task3.common.MinesweeperView;

import java.util.*;

class PlayingField {
    private final MinesweeperView minesweeperView;
    @Getter
    private final List<Cell> minedCells;
    private final List<Cell> flaggedCells;
    private final Cell[][] cells;

    private Map<Integer, CellContent> cellContentMap;
    @Getter
    private int rowsNumber;
    @Getter
    private int columnsNumber;
    @Getter
    private int minesNumber;
    private int flagsNumber;


    PlayingField(int rowsNumber, int columnsNumber, int minesNumber, MinesweeperView minesweeperView) {
        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
        this.minesNumber = minesNumber;
        this.minesweeperView = minesweeperView;
        this.flagsNumber = minesNumber;
        flaggedCells = new ArrayList<>();
        cells = new Cell[rowsNumber][columnsNumber];
        minedCells = new ArrayList<>(minesNumber);
        fillCellContentMap();
    }

    private void fillCellContentMap() {
        cellContentMap = new HashMap<>();
        cellContentMap.put(0, CellContent.EMPTY);
        cellContentMap.put(1, CellContent.ONE_MINE_NEARBY);
        cellContentMap.put(2, CellContent.TWO_MINE_NEARBY);
        cellContentMap.put(3, CellContent.THREE_MINE_NEARBY);
        cellContentMap.put(4, CellContent.FOUR_MINE_NEARBY);
        cellContentMap.put(5, CellContent.FIVE_MINE_NEARBY);
        cellContentMap.put(6, CellContent.SIX_MINE_NEARBY);
        cellContentMap.put(7, CellContent.SEVEN_MINE_NEARBY);
        cellContentMap.put(8, CellContent.EIGHT_MINE_NEARBY);
    }

    private CellContent getContentByMinesNumbers(int minesAroundNumber) {
        return cellContentMap.get(minesAroundNumber);
    }

    void init() {
        for (int x = 0; x < rowsNumber; x++) {
            for (int y = 0; y < columnsNumber; y++) {
                cells[x][y] = new Cell(x, y, CellState.CLOSED, CellContent.EMPTY);
            }
        }
    }

    Cell getCell(int x, int y) {
        return cells[x][y];
    }

    private boolean isPlayingFieldContainsThisCell(int x, int y) {
        return x >= 0 && x < rowsNumber && y >= 0 && y < columnsNumber;
    }

    void addMine(Cell minedCell) {
        minedCells.add(minedCell);
        increaseMinesNumberAroundCell(minedCell);
    }

    void setRemainingBombsNumber(int flagsNumber) {
        this.flagsNumber = flagsNumber;
        minesweeperView.showRemainingBombsNumber(flagsNumber);
    }

    private void increaseMinesNumberAroundCell(Cell cell) {
        List<Cell> neighbours = getCellNeighbours(cell);
        neighbours.stream()
                .filter(neighbour -> neighbour.getCellContent() != CellContent.MINE)
                .forEach(neighbour -> {
                    neighbour.increaseMinesAroundNumber();
                    neighbour.setCellContent(getContentByMinesNumbers(neighbour.getMinesAroundNumber()));
                });
    }

    List<Cell> getCellNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();

        int rowNumber = cell.getX();
        int columnNumber = cell.getY();

        for (int x = rowNumber - 1; x <= rowNumber + 1; ++x) {
            for (int y = columnNumber - 1; y <= columnNumber + 1; ++y) {
                if (isPlayingFieldContainsThisCell(x, y)) {
                    if (x == rowNumber && y == columnNumber) {
                        continue;
                    }
                    neighbours.add(cells[x][y]);
                }
            }
        }
        return neighbours;
    }

    List<Cell> getCellsListCopy() {
        List<Cell> cellsListCopy = new ArrayList<>(rowsNumber * columnsNumber);
        for (int x = 0; x < rowsNumber; x++) {
            cellsListCopy.addAll(Arrays.asList(cells[x]));
        }
        return cellsListCopy;
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
        updateFlags(currentCell);
    }

    private void updateFlags(Cell cell) {
        minesweeperView.showRemainingBombsNumber(this.flagsNumber);
        minesweeperView.updateCellState(cell.getX(), cell.getY(), cell.getCellState());
    }

    void markAllMinesAfterVictory(List<Cell> minedCells) {
        minedCells.stream()
                .filter(minedCell -> minedCell.getCellState() != CellState.FLAGGED)
                .forEach(minedCell -> {
                    minedCell.setCellState(CellState.FLAGGED);
                    flagsNumber--;
                    updateFlags(minedCell);
                });
    }


    void processGameStop(Cell detonatedCell) {
        openNotFlaggedMinesAfterDetonation(detonatedCell);
        openMistakenFlags();
    }

    private void openMistakenFlags() {
        flaggedCells.stream()
                .filter(flaggedCell -> flaggedCell.getCellContent() != CellContent.MINE)
                .forEach(flaggedCell -> {
                    flaggedCell.setCellContent(CellContent.MISTAKE);
                    flaggedCell.setCellState(CellState.OPENED);
                    minesweeperView.showCellContent(flaggedCell.getX(), flaggedCell.getY(), flaggedCell.getCellContent());
                });
    }

    private void openNotFlaggedMinesAfterDetonation(Cell detonatedCell) {
        detonatedCell.setCellContent(CellContent.DETONATED);
        minedCells.stream()
                .filter(mine -> mine.getCellState() != CellState.FLAGGED)
                .forEach(mine -> {
                    mine.setCellState(CellState.OPENED);
                    minesweeperView.showCellContent(mine.getX(), mine.getY(), mine.getCellContent());
                });
    }
}
