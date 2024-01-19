package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ResourceBundle;


public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private static final Logger logger = LogManager.getLogger("model");

    private String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard board;
        try (FileInputStream fileIn = new FileInputStream(fileName)) {
            ObjectInputStream in = new ObjectInputStream(fileIn);
            board = (SudokuBoard) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("MyBundleException_en");
            logger.error(bundle.getString("io"));
            throw new SudokuException.SudokuRuntimeException(bundle.getString("io"));
        }
        return board;
    }

    @Override
    public void write(SudokuBoard obj) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {

            ObjectOutput out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
        } catch (IOException e) {
            ResourceBundle bundle = ResourceBundle.getBundle("com.example.MyBundle_en");
            logger.error(bundle.getString("io"));
            throw new SudokuException.SudokuRuntimeException(bundle.getString("io"));
        }
    }

    @Override
    public void close() {

    }
}
