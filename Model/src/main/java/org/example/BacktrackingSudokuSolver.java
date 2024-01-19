/**
 * Copyright (C) 2023  Adrian Bień, Wojciech Durys
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see https://www.gnu.org/licenses/.
 */

package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Random;
import java.util.ResourceBundle;


/**
 * "This is BacktrackingSudokuSolver class that implements SudokuSolver interface."
 */
public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {

    private static final Logger logger = LogManager.getLogger("model");

    /**
     * "This is BacktrackingSudokuSolver constructor."
     */
    public BacktrackingSudokuSolver() {

    }

    /**
     * "check method checks if given digit appears in given row, column or 3x3 box."
     *
     * @return returns false if digit did appear and true if it didn't
     */

    private boolean check(int row, int col, int number, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (board.get(row, i) == number) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (board.get(i, col) == number) {
                return false;
            }
        }

        int boxRow = row - row % 3;
        int boxCol = col - col % 3;

        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board.get(i, j) == number) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * "zero method sets all board cells to 0."
     */
    private void zero(SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i, j, 0);
            }
        }
    }

    /**
     * "randomize method takes integer array and mix its elements randomly."
     *
     * @param array an array that stores digits from 1 to 9.
     */
    private void randomize(int[] array) {
        for (int i = 0; i < 8; i++) {
            Random obj = new Random();
            int random = obj.nextInt(9);
            int temp = array[i];
            array[i] = array[random];
            array[random] = temp;
        }
    }


    /**
     * "solve method assigns checked digit to specified by row and column board cell."
     * "The backtracking used there, provides if at any moment certain digit placement
     * prevents from generating correct Sudoku board, it can go back and try solving with another digit."
     *
     * @return returns true if board filling succeeded
     */
    private boolean fill(SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i, j) == 0) {
                    int[] digits = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                    randomize(digits);
                    for (int digit : digits) {
                        if (check(i, j, digit, board)) {
                            board.set(i, j, digit);
                            if (fill(board)) {
                                return true;
                            } else {
                                board.set(i, j, 0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void solve(SudokuBoard board) {
        zero(board);
        fill(board);
    }

    @Override
    public SudokuSolver clone() {
        try {
            SudokuSolver clone = (SudokuSolver) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("org.example.MyBundleException_en");
            logger.error(bundle.getString("solverClone"));
            throw new SudokuException.SudokuCloneNotSupportedException(bundle.getString("solverClone"));
        }
    }


}


