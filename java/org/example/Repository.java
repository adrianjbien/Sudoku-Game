package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

public class Repository {

    private static final SudokuSolver solver = new BacktrackingSudokuSolver();
    private static final SudokuBoard board = new SudokuBoard(solver);

    private static final Logger logger = LogManager.getLogger("model");

    private Repository() {

    }

    public static SudokuBoard getBoardInstance() {
        try {
            return board.clone();
        } catch (NullPointerException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("com.example.MyBundle_en");
            logger.error(bundle.getString("boardNull"));
            throw new SudokuException.SudokuNullException(bundle.getString("boardNull"));
        }

    }
}
