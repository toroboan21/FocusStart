package ru.cft.focusstart.sakharova.task1.parameters;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ParametersBuilder {

    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 32;

    public TableParameters buildParameters() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите размер таблицы целым числом от " +
                    MIN_SIZE + " до " + MAX_SIZE + ":");
            int tableSize = scanner.nextInt();
            checkTableSize(tableSize);
            return new TableParameters(tableSize);
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException("Допускается вводить только целые числа!");
        }
    }

    private void checkTableSize(int size) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new IllegalArgumentException("Введен неподдерживаемый размер!");
        }
    }
}
