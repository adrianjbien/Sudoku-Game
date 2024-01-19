package com.example.view;

import javafx.beans.property.SimpleObjectProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.property.SimpleIntegerProperty;
import org.example.SudokuBoard;
import org.example.SudokuException;

import java.util.ResourceBundle;

public class PropertyBoardAdapter extends SimpleObjectProperty<Integer> {
    private static final Logger logger = LogManager.getLogger("model");
    public SimpleIntegerProperty convert(SudokuBoard board, int row, int col) {
        try {
            return new SimpleIntegerProperty(board.get(row,col));
        } catch (NullPointerException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("org.example.MyBundleException_en");
            logger.error(bundle.getString("boardNull"));
            throw new SudokuException.SudokuNullException(bundle.getString("boardNull"));
        }

    }
}
