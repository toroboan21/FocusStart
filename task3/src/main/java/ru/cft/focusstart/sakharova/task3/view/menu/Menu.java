package ru.cft.focusstart.sakharova.task3.view.menu;

import lombok.Getter;
import ru.cft.focusstart.sakharova.task3.common.DifficultyMode;
import ru.cft.focusstart.sakharova.task3.controller.MenuCommands;
import ru.cft.focusstart.sakharova.task3.view.ListenerCreator;

import javax.swing.*;

public class Menu {
    @Getter
    private final JMenuBar menuBar;
    private final ListenerCreator listenerCreator;

    public Menu(ListenerCreator listenerCreator) {
        this.listenerCreator = listenerCreator;
        menuBar = new JMenuBar();
        createMenu();
    }

    private void createMenu() {
        JMenu gameMenu = new JMenu("Игра");
        JMenuItem newGame = new JMenuItem("Новая игра");
        newGame.setActionCommand(MenuCommands.RESTART.name());

        JMenuItem beginnerMode = new JMenuItem(DifficultyMode.BEGINNER.getName());
        beginnerMode.setActionCommand(MenuCommands.SWITCH_TO_BEGINNER_MODE.name());

        JMenuItem intermediateMode = new JMenuItem(DifficultyMode.INTERMEDIATE.getName());
        intermediateMode.setActionCommand(MenuCommands.SWITCH_TO_INTERMEDIATE_MODE.name());

        JMenuItem expertMode = new JMenuItem(DifficultyMode.EXPERT.getName());
        expertMode.setActionCommand(MenuCommands.SWITCH_TO_EXPERT_MODE.name());

        JMenuItem customMode = new JMenuItem(DifficultyMode.CUSTOM.getName());
        customMode.setActionCommand(MenuCommands.SWITCH_TO_CUSTOM_MODE.name());

        JMenuItem highScores = new JMenuItem("Таблица рекордов");
        highScores.setActionCommand(MenuCommands.HIGH_SCORES.name());

        JMenuItem quit = new JMenuItem("Выйти из игры");
        quit.setActionCommand(MenuCommands.EXIT.name());

        gameMenu.add(newGame);
        gameMenu.add(beginnerMode);
        gameMenu.add(intermediateMode);
        gameMenu.add(expertMode);
        gameMenu.add(customMode);
        gameMenu.add(highScores);
        gameMenu.add(quit);

        addActionListenerOnMenu(gameMenu);

        menuBar.add(gameMenu);
    }

    private void addActionListenerOnMenu(JMenu menu) {
        for (int i = 0; i < menu.getItemCount(); i++) {
            JMenuItem item = menu.getItem(i);
            item.addActionListener(listenerCreator.createListenerForMenuButton());
        }
    }
}
