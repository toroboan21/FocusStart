package ru.cft.focusstart.sakharova.task3.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.cft.focusstart.sakharova.task3.common.CellContent;
import ru.cft.focusstart.sakharova.task3.common.CellState;
import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.common.Observer;
import ru.cft.focusstart.sakharova.task3.model.highscores.HighScoresStorage;

import java.util.*;

@RequiredArgsConstructor
public class GameManager implements Model {
    private static final DifficultyMode defaultDifficulty = DifficultyMode.BEGINNER;
    private static final int MILLIS_IN_SECOND = 1000;

    private final HighScoresStorage highScoresStorage;
    private Observer observer;
    private FlagsMap flagsMap;
    private MinesMap minesMap;
    private Cell[][] playingField;
    private Random randomCoordinatesGenerator;

    private int rowsNumber;
    private int columnsNumber;
    private int minesNumber;
    private int openedCellsToWinNumber;
    private int openedCellsNumber;
    private long timeSpentInSeconds;

    private DifficultyMode difficultyMode;
    private HashMap<Integer, CellContent> cellContentMap;

    @Getter
    private GameState gameState;
    @Getter
    private boolean isFirstStep;

    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }


    @Override
    public void initObserver() {
        observer.init(rowsNumber, columnsNumber);
        flagsMap.showRemainingBombsNumber(minesNumber);
    }

    @Override
    public void init() {
        this.difficultyMode = defaultDifficulty;
        randomCoordinatesGenerator = new Random();
        prepareGameData(difficultyMode.getRowsNumber(), difficultyMode.getColumnsNumber(), difficultyMode.getMinesNumber());
        fillCellContentMap();
    }

    private void prepareGameData(int rowsNumber, int columnsNumber, int minesNumber) {
        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
        this.minesNumber = minesNumber;

        openedCellsToWinNumber = (rowsNumber * columnsNumber) - minesNumber;
        openedCellsNumber = 0;

        playingField = new Cell[rowsNumber][columnsNumber];
        flagsMap = new FlagsMap(minesNumber, observer);
        minesMap = new MinesMap(minesNumber, observer);

        isFirstStep = true;

        createPlayingField();
    }

    private void createPlayingField() {
        for (int x = 0; x < rowsNumber; x++) {
            for (int y = 0; y < columnsNumber; y++) {
                playingField[x][y] = new Cell(x, y, CellState.CLOSED, CellContent.EMPTY);
            }
        }
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

    @Override
    public void setFlag(int x, int y) {
        flagsMap.setFlag(playingField[x][y]);
    }

    @Override
    public void openCell(int x, int y) {
        if (isFirstStep()) {
            startGame(x, y);
        }
        Cell currentCell = playingField[x][y];

        if (getGameState() == GameState.IS_ON && currentCell.getCellState() == CellState.CLOSED) {
            if (currentCell.getCellContent() == CellContent.MINE) {
                observer.showCellContent(currentCell.getX(), currentCell.getY(), currentCell.getCellContent());
                stopGameAfterDefeat(currentCell);
            } else {
                openCellAndNeighbours(currentCell);

                if (isGameWon()) {
                    stopGameAfterVictory();
                }
            }
        }
    }

    @Override
    public void startGame(int x, int y) {
        isFirstStep = false;
        Cell firstStepCell = playingField[x][y];
        locateMines(firstStepCell);
        gameState = GameState.IS_ON;
        observer.processGameStart();
    }

    private void locateMines(Cell firstStepCell) {
        int counter = minesNumber;
        while (counter > 0) {
            int x = randomCoordinatesGenerator.nextInt(rowsNumber);
            int y = randomCoordinatesGenerator.nextInt(columnsNumber);
            Cell currentCell = playingField[x][y];

            if (currentCell.getCellContent() != CellContent.MINE && !currentCell.equals(firstStepCell)) {
                currentCell.setCellContent(CellContent.MINE);
                increaseMinesAroundNumber(currentCell);

                minesMap.addMine(currentCell);
                --counter;
            }
        }
    }

    private void increaseMinesAroundNumber(Cell cell) {
        List<Cell> neighbours = getCellNeighbours(cell);
        neighbours.stream().filter(neighbour -> neighbour.getCellContent() != CellContent.MINE).
                forEach(neighbour -> {
                    neighbour.increaseMinesAroundNumber();
                    neighbour.setCellContent(getContentByMinesNumbers(neighbour.getMinesAroundNumber()));
                });
    }

    private List<Cell> getCellNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();

        int rowNumber = cell.getX();
        int columnNumber = cell.getY();

        for (int x = rowNumber - 1; x <= rowNumber + 1; ++x) {
            for (int y = columnNumber - 1; y <= columnNumber + 1; ++y) {
                if (isPlayingFieldContainsThisCell(x, y)) {
                    if (x == rowNumber && y == columnNumber) {
                        continue;
                    }
                    neighbours.add(playingField[x][y]);
                }
            }
        }
        return neighbours;
    }

    private boolean isGameWon() {
        return openedCellsNumber == openedCellsToWinNumber;
    }

    private void openCellAndNeighbours(Cell clickedSafeCell) {
        Set<Cell> visitedCells = new HashSet<>();
        Deque<Cell> deque = new LinkedList<>();
        deque.addLast(clickedSafeCell);

        while (!deque.isEmpty()) {
            Cell currentCell = deque.removeLast();

            if (visitedCells.add(currentCell) &&
                    currentCell.getCellState() == CellState.CLOSED &&
                    currentCell.getCellContent() != CellContent.MINE) {
                currentCell.setCellState(CellState.OPENED);
                observer.showCellContent(currentCell.getX(), currentCell.getY(), currentCell.getCellContent());
                ++openedCellsNumber;
                tryAddNeighboursToDeque(deque, currentCell);
            }
        }
    }

    private void tryAddNeighboursToDeque(Deque<Cell> deque, Cell currentCell) {
        if (currentCell.getCellContent() == CellContent.EMPTY) {
            List<Cell> neighbours = getCellNeighbours(currentCell);
            neighbours.stream().filter(neighbour -> !deque.contains(neighbour)).forEach(deque::addLast);
        }
    }

    private boolean isPlayingFieldContainsThisCell(int x, int y) {
        return x >= 0 && x < rowsNumber && y >= 0 && y < columnsNumber;
    }

    private void stopGameAfterVictory() {
        gameState = GameState.VICTORY;
        flagsMap.markAllMinesAfterVictory(minesMap.getMinedCells());
        stopTimerAndBlockCells();

        if (difficultyMode != DifficultyMode.CUSTOM &&
                highScoresStorage.isHighScore(timeSpentInSeconds, difficultyMode)) {
            observer.notifyPlayerAboutRecord();
        }
    }

    private void stopTimerAndBlockCells() {
        observer.finishGame(isGameWon());
    }

    private void stopGameAfterDefeat(Cell detonatedCell) {
        gameState = GameState.WASTED;
        minesMap.openNotFlaggedMinesAfterDetonation(detonatedCell);
        flagsMap.openMistakenFlags();
        stopTimerAndBlockCells();
    }

    @Override
    public void openNotFlaggedNeighbours(int x, int y) {
        if (getGameState() == GameState.IS_ON) {
            Cell currentCell = playingField[x][y];
            if (currentCell.getCellState() != CellState.OPENED || currentCell.getCellContent() == CellContent.EMPTY) {
                return;
            }
            List<Cell> neighbours = getCellNeighbours(currentCell);
            int flaggedNeighboursNumber = 0;

            for (Cell neighbour : neighbours) {
                if (neighbour.getCellState() == CellState.FLAGGED) {
                    ++flaggedNeighboursNumber;
                }
            }

            int minesAroundCurrentCellNumber = currentCell.getMinesAroundNumber();
            if (minesAroundCurrentCellNumber == flaggedNeighboursNumber) {
                neighbours.forEach(currentNeighbour -> openCell(currentNeighbour.getX(), currentNeighbour.getY()));
            }
        }
    }

    private void prepareGameAfterRestart(int rowsNumber, int columnsNumber, int minesNumber) {
        prepareGameData(rowsNumber, columnsNumber, minesNumber);
        flagsMap.showRemainingBombsNumber(minesNumber);
    }

    @Override
    public void restartGame() {
        prepareGameAfterRestart(rowsNumber, columnsNumber, minesNumber);
        observer.restartGame();
    }

    @Override
    public void restartGameWithCustomSettings(int rowsNumber, int columnsNumber, int minesNumber) {
        difficultyMode = DifficultyMode.CUSTOM;

        int validateRowsNumber = CustomSettingsUtils.fitRowsNumber(rowsNumber);
        int validateColumnsNumber = CustomSettingsUtils.fitColumnsNumber(columnsNumber);
        int validateMinesNumber = CustomSettingsUtils.fitMinesNumber(validateRowsNumber, validateColumnsNumber,
                minesNumber);

        prepareGameAfterRestart(validateRowsNumber, validateColumnsNumber, validateMinesNumber);

        observer.restartGameWithNewDifficulty(validateRowsNumber, validateColumnsNumber);
    }

    @Override
    public void restartGameWithNewDifficulty(DifficultyMode newDifficultyMode) {
        difficultyMode = newDifficultyMode;
        prepareGameAfterRestart(difficultyMode.getRowsNumber(), difficultyMode.getColumnsNumber(),
                difficultyMode.getMinesNumber());
        observer.restartGameWithNewDifficulty(newDifficultyMode.getRowsNumber(), newDifficultyMode.getColumnsNumber());
    }

    @Override
    public void showCustomSettings() {
        observer.showCustomSettings(rowsNumber, columnsNumber, minesNumber);
    }

    public void showHighScores() {
        observer.showHighScores(highScoresStorage.getHighScores());
    }

    @Override
    public void writeNewHighScore(String name) {
        highScoresStorage.writeNewHighScore(name, difficultyMode);
        observer.hideHighScoreNotification();
    }

    @Override
    public void resetHighScores() {
        highScoresStorage.resetHighScores();
        observer.showHighScores(highScoresStorage.getHighScores());
    }

    @Override
    public void hideCustomSettingsInput() {
        observer.hideCustomSettingsInput();
    }

    @Override
    public void setTime(long timeSpent) {
        this.timeSpentInSeconds = timeSpent / MILLIS_IN_SECOND;
        observer.modifyTimer(timeSpent);
    }

    @Override
    public void exitGame() {
        highScoresStorage.saveBeforeExit();
        observer.exitGame();
    }
}
