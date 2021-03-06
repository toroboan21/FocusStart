package ru.cft.focusstart.sakharova.table.parameters;

import lombok.experimental.UtilityClass;

import java.util.InputMismatchException;
import java.util.Scanner;

@UtilityClass
public class ParametersBuilder {

    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 32;

    public static TableParameters buildParameters() {
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

    private static void checkTableSize(int size) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new IllegalArgumentException("Введен неподдерживаемый размер!");
        }
    }
}
