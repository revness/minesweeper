package minesweeper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static Set<String> bombPositions = new HashSet<>();
    static Set<String> guessedPositions = new HashSet<>();
    static ArrayList<String> myBombs = new ArrayList<>();
    static boolean foundBomb = false;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Random random = new Random();

        System.out.println("Enter a number for size of grid (2-10)");
        int size = scanner.nextInt();
        System.out.println("Enter a number for number of bombs (2-10)");
        int numberOfBombs = scanner.nextInt();

        String[][] grid = new String[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = String.format("[  ]");

            }
        }

        while (bombPositions.size() < numberOfBombs) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            String position = row + "," + col;

            if (!bombPositions.contains(position)) {
                grid[row][col] = "[  ]";
                bombPositions.add(position);
                myBombs.add(position);
            }
        }

        while (foundBomb == false) {
            printGrid(size, grid);
            System.out.println("Enter a number for x value (1-10)");
            int command = scanner.nextInt();
            System.out.println("Enter a number for y value (1-10)");
            int command2 = scanner.nextInt();
            System.out.printf("%d,%d\n", command, command2);
            boolean result = checkBomb(command2 -= 1, command -= 1);
            System.out.println(result);
            if (result) {
                foundBomb = true;
                grid[command2][command] = "[BB]";
                printGrid(size, grid);
                System.out.println("GAME OVER");
            }
            // System.out.println(bombPositions);

            grid[command2][command] = checkSurroundingGrid(command, command2, size); // Mark as safe
            guessedPositions.add(command + "," + command2);

        }
    }

    public static boolean checkGuesses(int x, int y) {
        String position = y + "," + x;
        return guessedPositions.contains(position);
    }

    public static void printGrid(int size, String[][] grid) {

        System.out.printf("%5s", " ");
        for (int j = 0; j < size; j++) {
            System.out.printf("%5d", j + 1);
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.printf("%5d", i + 1);
            for (int j = 0; j < size; j++) {
                System.out.printf("%5s", grid[i][j]);
            }
            System.out.println();
        }
    }

    public static boolean checkBomb(int x, int y) {
        String position = y + "," + x;

        return bombPositions.contains(position);
    }

    public static String checkSurroundingGrid(int x, int y, int size) {
        int count = 0;
        // let say x, y is selected, we check if bombPositions includes
        // (x-1, y-1)(x,y-1)(x+1, y-1)(x+1, y)(x+1, y+1)(x, y+1)(x-1, y+1) etc
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < size && j >= 0 && j < size) {
                    // System.out.printf("%d,%d: counted\n", i + 1, j + 1);
                    if (checkBomb(j, i)) {
                        count++;
                    }
                } else {
                    // System.out.printf("%d,%d: outside\n", i + 1, j + 1);
                }
            }
        }
        return "[" + String.valueOf(count) + " ]";
    }

}
