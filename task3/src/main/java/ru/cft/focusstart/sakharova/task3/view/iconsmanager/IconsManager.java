package ru.cft.focusstart.sakharova.task3.view.iconsmanager;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import ru.cft.focusstart.sakharova.task3.common.CellContent;
import ru.cft.focusstart.sakharova.task3.common.CellState;

import javax.swing.*;
import java.util.EnumMap;

@UtilityClass
public class IconsManager {
    private static final EnumMap<CellContent, Icon> cellContentIcons;
    private static final EnumMap<CellState, Icon> cellStateIcons;

    @Getter
    private static final ImageIcon gameIcon;
    @Getter
    private static final ImageIcon highScoresIcon;
    @Getter
    private static final ImageIcon normalRestartButton;
    @Getter
    private static final ImageIcon deadRestartButton;
    @Getter
    private static final ImageIcon winnerRestartButton;
    @Getter
    private static final ImageIcon remainingBombs;
    @Getter
    private static final ImageIcon timer;

    static {
        gameIcon = createIcon("minesweeper.png");
        highScoresIcon = createIcon("trophy.png");
        remainingBombs = createIcon("bombs.png");
        timer = createIcon("timer.png");

        normalRestartButton = createIcon("normal.png");
        deadRestartButton = createIcon("dead.png");
        winnerRestartButton = createIcon("winner.png");

        cellContentIcons = new EnumMap<>(CellContent.class);
        cellContentIcons.put(CellContent.EMPTY, createIcon("empty.png"));
        cellContentIcons.put(CellContent.ONE_MINE_NEARBY, createIcon("one.png"));
        cellContentIcons.put(CellContent.TWO_MINE_NEARBY, createIcon("two.png"));
        cellContentIcons.put(CellContent.THREE_MINE_NEARBY, createIcon("three.png"));
        cellContentIcons.put(CellContent.FOUR_MINE_NEARBY, createIcon("four.png"));
        cellContentIcons.put(CellContent.FIVE_MINE_NEARBY, createIcon("five.png"));
        cellContentIcons.put(CellContent.SIX_MINE_NEARBY, createIcon("six.png"));
        cellContentIcons.put(CellContent.SEVEN_MINE_NEARBY, createIcon("seven.png"));
        cellContentIcons.put(CellContent.EIGHT_MINE_NEARBY, createIcon("eight.png"));
        cellContentIcons.put(CellContent.MINE, createIcon("bomb.png"));
        cellContentIcons.put(CellContent.MISTAKE, createIcon("miss.png"));
        cellContentIcons.put(CellContent.DETONATED, createIcon("boom.png"));

        cellStateIcons = new EnumMap<>(CellState.class);
        cellStateIcons.put(CellState.CLOSED, createIcon("0.png"));
        cellStateIcons.put(CellState.FLAGGED, createIcon("flag.png"));
    }

    private static ImageIcon createIcon(String fileName) {
        return new ImageIcon(IconsManager.class.getResource("/icons/" + fileName));
    }

    public static Icon getContentIcon(CellContent contentType) {
        return cellContentIcons.get(contentType);
    }

    public static Icon getStateIcon(CellState stateType) {
        return cellStateIcons.get(stateType);
    }

    public static Icon getBaseRestartButtonIcon() {
        return normalRestartButton;
    }
}
