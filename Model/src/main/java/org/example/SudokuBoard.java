/**
 * Copyright (C) 2023  Adrian Bień, Wojciech Durys
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */

package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * "This is SudokuBoard class."
 */



public class SudokuBoard implements Serializable, Cloneable {

    private SudokuSolver sudokuSolver;

    private SudokuField[][] board = new SudokuField[9][9];

    private static final Logger logger = LogManager.getLogger("model");

    /**
     * "This is SudokuBoard constructor."
     * @param sudokuSolver interface is injected into SudokuBoard class by constructor.
     */

    public SudokuBoard(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }
    }

    /**
     * "CheckBoard method checks if whole board is filled correctly."
     * @return returns true if numbers didn't repeat within given row, column or box.
     */

    public boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify() || !getCol(i).verify() || !getBox(i / 3 * 3, i % 3 * 3).verify()) {
                return false;
            }
        }
        return true;
    }

    /**
     * "solveGame method calls solve method implemented by BacktrackingSudokuSolver class."
     */
    public void solveGame() {
        sudokuSolver.solve(this);
    }

    /**
     * "getBoard method returns value stored in board cell that is specified by given row and column."
     * @param row given row
     * @param col given column
     * @return returns given cell
     */
    public int get(int row, int col) {
        return this.board[row][col].getFieldValue();
    }


    /**
     * "setBoard method sets given value to specified cell in board by row and column."
     * @param row given row
     * @param col given column
     * @param value given value
     */

    public void set(int row, int col, int value) {
        this.board[row][col].setFieldValue(value);
    }

    /**
     * "getRow method gets a certain row of sudoku fields from the board."
     * @param row integer that specifies certain row to get.
     * @return return given row by row parameter.
     */

    public SudokuElement getRow(int row) {
        SudokuRow sudokuRow = new SudokuRow();
        List<SudokuField> fields = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            fields.set(i,board[row][i]);
        }
        sudokuRow.setElement(fields);
        return sudokuRow;
    }

    /**
     * "getCol method gets a certain column of sudoku fields from the board."
     * @param col integer that specifies certain column to get.
     * @return return given column by col parameter.
     */

    public SudokuColumn getCol(int col) {
        SudokuColumn sudokuCol = new SudokuColumn();
        List<SudokuField> fields;
        fields = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            fields.set(i,board[i][col]);
        }
        sudokuCol.setElement(fields);
        return sudokuCol;
    }

    /**
     * "getBox method gets a certain 3x3 box of sudoku fields from the board."
     * @param row integer that specifies row of the very first field of the local box.
     * @param col integer that specifies column of the very first field of the local box.
     * @return return box specified by row and col parameters.
     */

    public SudokuBox getBox(int row, int col) {
        SudokuBox sudokuBox = new SudokuBox();
        List<SudokuField> fields;
        fields = Arrays.asList(new SudokuField[9]);
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        int index = 0;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                fields.set(index++,board[i][j]);
            }
        }
        sudokuBox.setElement(fields);
        return sudokuBox;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SudokuBoard board1 = (SudokuBoard) object;
        boolean flag = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j].getFieldValue() != board1.get(i,j)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    @Override
    public int hashCode() {
        long temp = 0;
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                temp += (long)board[i - 1][j - 1].getFieldValue() * i * j;
            }
        }
        Random rand = new Random(temp);
        return rand.nextInt();
    }

    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                temp = temp + board[i][j].toString() + " ";
            }
        }
        return temp;
    }

    @Override
    public SudokuBoard clone() {
        try {
            SudokuBoard clone = (SudokuBoard) super.clone();
            SudokuField[][] temp = new SudokuField[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    temp[i][j] = this.board[i][j].clone();
                }
            }
            clone.board = temp;
            return clone;
        } catch (CloneNotSupportedException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("org.example.MyBundleException_en");
            logger.error(bundle.getString("boardClone"));
            throw new SudokuException.SudokuCloneNotSupportedException(bundle.getString("boardClone"));
        }
    }
}


