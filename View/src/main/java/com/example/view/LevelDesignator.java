package com.example.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.example.Level;
import org.example.SudokuException;

import java.util.ResourceBundle;

public class LevelDesignator {

    private static final Logger logger = LogManager.getLogger("model");
    public Level set(String name) throws SudokuException.SudokuNullException {
        Level level;
        try {
            if (name.equals("ŁATWY") || name.equals("EASY")) {
                level = Level.EASY;
                return level;
            } else if (name.equals("ŚREDNI") || name.equals("MEDIUM")) {
                level = Level.MEDIUM;
                return level;
            } else if (name.equals("TRUDNY") || name.equals("HARD")) {
                level = Level.HARD;
                return level;
            }
            return null;
        } catch (NullPointerException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("org.example.MyBundle_en");
            logger.error(bundle.getString("stringNull"));
            throw new SudokuException.SudokuNullException(bundle.getString("stringNull"));
        }

    }
}
