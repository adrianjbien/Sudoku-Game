package org.example;

import java.util.Random;

public enum Level {
    EASY,
    MEDIUM,
    HARD;

    public void digger(SudokuBoard board) {
        Random random = new Random(); // easy - wybij 20-30, medium 31-50, hard 51-64
        if (this == EASY) {
            int numberOfCells = random.nextInt(4) + 1;//+ 20; // bylo 11 + 20
            loop(board,numberOfCells);
        } else if (this == MEDIUM) {
            int numberOfCells = random.nextInt(20) + 31;
            loop(board,numberOfCells);
        } else {
            int numberOfCells = random.nextInt(14) + 51;
            loop(board,numberOfCells);
        }


    }

    private void loop(SudokuBoard board, int number) {
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int randomRow = random.nextInt(9);
            int randomCol = random.nextInt(9);
            if (board.get(randomRow, randomCol) == 0) {
                i--;
            } else {
                board.set(randomRow, randomCol, 0);
            }
        }
    }
}
