package org.example;

public class SudokuBoardDaoFactory {

    private SudokuBoardDaoFactory() {

    }

    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public static Dao<SudokuBoard> getJdbcDao(String nameOfBoard) {
        return new JdbcSudokuBoardDao(nameOfBoard);
    }
}
