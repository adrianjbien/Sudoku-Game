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

/**
 * "This is SudokuSolver interface."
 */
public interface SudokuSolver extends Cloneable {


    /**
     * "solve method is responsible for solving sudoku. It does have body in BacktrackingSudokuSolver class."
     * @param board is an object of SudokuBoard class
     */
    void solve(SudokuBoard board);
}