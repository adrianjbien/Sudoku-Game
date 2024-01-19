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
import org.example.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;



/**
 * "This is SudokuBoardTest class which is responsible for unit tests."
 */
public class SudokuBoardTest {

    /**
     * "This is SudokuBoardTest constructor."
     */

    public SudokuBoardTest() {

    }

    @Test
    void filling() {
        SudokuBoard board1 = Repository.getBoardInstance();
        board1.solveGame();

        int flag = 0;
        if (board1.checkBoard()) {
            flag++;
        }
        assertEquals(1,flag);

        //rows

        int flag2 = 0;
        SudokuBoard board2 = Repository.getBoardInstance();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board2.set(i,j,i + 1);
            }
        }
        if (board2.checkBoard()) {
            flag2++;
        }
        assertEquals(0,flag2);

        //columns

        int flag3 = 0;
        SudokuBoard board3 = Repository.getBoardInstance();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board3.set(j,i,i + 1);
            }
        }
        if (board3.checkBoard()) {
            flag3++;
        }
        assertEquals(0,flag3);

        //boxes

        int flag4 = 0;
        SudokuBoard board4 = Repository.getBoardInstance();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j == 0) {
                    board4.set(i,j,i + 1);
                } else {
                    board4.set(i,j,j + 1);
                }
            }
        }
        if (board4.checkBoard()) {
            flag4++;
        }
        assertEquals(0,flag4);
    }



    @Test
    void notSame() {
        SudokuBoard board1 = Repository.getBoardInstance();
        SudokuBoard board2 = Repository.getBoardInstance();
        SudokuBoard board3 = Repository.getBoardInstance();
        for (SudokuBoard board : Arrays.asList(board1, board2, board3)) {
            board.solveGame();
        }
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        int flag = 0;
        if (board1.checkBoard()) {
            flag++;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board1.get(i,j) != board2.get(i,j)) {
                    flag1 = true;
                }

            }
        }
        if (board2.checkBoard()) {
            flag++;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board2.get(i,j) != board3.get(i,j)) {
                    flag2 = true;
                }
            }
        }
        if (board3.checkBoard()) {
            flag++;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board1.get(i,j) != board3.get(i,j)) {
                    flag3 = true;
                }
            }
        }
        assertEquals(3, flag);
        assertTrue(flag1 && flag2 && flag3);
    }


    @Test
    void setTest() {
        SudokuBoard board1 = Repository.getBoardInstance();
        board1.set(0,0,5);
        assertEquals(board1.get(0,0), 5);
    }

    @Test
    void getTest() {
        SudokuBoard board1 = Repository.getBoardInstance();
        board1.solveGame();
        int x = board1.get(0,0);
        board1.set(0,0,10 - board1.get(0,0));
        assertEquals(board1.get(0,0), 10 - x);
    }

    @Test
    void toStringTest() {
        SudokuBoard board = Repository.getBoardInstance();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i,j,2);
            }
        }
        final String result = "2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 "
                + "2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 "
                + "2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 ";
        final String failure = "2";
        assertEquals(board.toString(), result);
        assertNotEquals(board.toString(), failure);
    }

    @Test
    void equalsTest() {
        SudokuBoard board1 = Repository.getBoardInstance();
        SudokuBoard board2 = Repository.getBoardInstance();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board1.set(i,j,2);
                board2.set(i,j,2);
            }
        }
        boolean flag = board1.equals(board2);
        assertTrue(flag);

        SudokuBoard board4 = board1;
        boolean flag2 = board4.equals(board1);
        assertTrue(flag2);

        SudokuField field = new SudokuField();
        boolean flag3 = board1.equals(field);
        assertFalse(flag3);

        SudokuBoard board5 = null;
        boolean flag4 = board1.equals(board5);
        assertFalse(flag4);

        SudokuBoard board6 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board7 = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board6.set(i,j,2);
                board7.set(i,j,3);
            }
        }

        boolean flag5 = board6.equals(board7);
        assertFalse(flag5);

    }

    @Test
    void hashCodeTest() {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board1.set(i,j,2);
                board2.set(i,j,2);
            }
        }

        assertEquals(board1.hashCode(), board2.hashCode());
    }

    @Test
    void cloneTest() {
        SudokuBoard board = Repository.getBoardInstance();
        SudokuBoard clone = board.clone();
        assertNotSame(board,clone); //czyli sprawdz na same
    }
}