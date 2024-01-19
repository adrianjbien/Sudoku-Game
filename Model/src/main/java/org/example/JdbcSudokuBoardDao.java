package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    public static final String Url = "jdbc:sqlserver://BIENIOLEL;encrypt=false;"
            + "databaseName=sudoku;integratedSecurity=true;";

    private String nameOfSavedBoard;

    private String nameOfLoadedBoard;

    private static final Logger logger = LogManager.getLogger("model");

    public JdbcSudokuBoardDao(String name) {
        nameOfSavedBoard = name;
    }

    public String getNameOfLoadedBoard() {
        return nameOfLoadedBoard;
    }

    public void setNameOfLoadedBoard(String nameOfLoadedBoard) {
        this.nameOfLoadedBoard = nameOfLoadedBoard;
    }

    public String getNameOfSavedBoard() {
        return nameOfSavedBoard;
    }

    public void clearDataBase() {

        try (Connection connection = DriverManager.getConnection(Url)) {
            connection.setAutoCommit(false);
            String query1 = "DELETE from pole";
            String query2 = "DELETE from plansza";
            try (Statement statement = connection.createStatement()) {
                statement.execute(query1);
                statement.execute(query2);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Url)) {
            connection.setAutoCommit(false);
            String query = "SELECT plansza.nazwa from plansza";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet set = statement.executeQuery(query)) {
                    while (set.next()) {
                        names.add(set.getString(1));
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return names;
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard temp = Repository.getBoardInstance();
        try (Connection connection = DriverManager.getConnection(Url)) {
            connection.setAutoCommit(false);
            String query = "SELECT plansza.nazwa, pole.x, pole.y, pole.wartość from pole "
                    + "join plansza on plansza.id = pole.nr_planszy";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet set = statement.executeQuery(query)) {
                    while (set.next()) {
                        if (Objects.equals(set.getString(1), nameOfLoadedBoard)) {
                            temp.set(set.getInt(2),set.getInt(3),set.getInt(4));
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("cos");
                }
            } catch (SQLException e) {
                System.out.println("cos"); //wyjatek za malo liczba fieldow zaladowana
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println("cos");
        }
        return temp;
    }

    @Override
    public void write(SudokuBoard obj) {
        int primaryKey = obj.hashCode();

        try (Connection connection = DriverManager.getConnection(Url)) {
            connection.setAutoCommit(true);
            String queryBoard = "INSERT INTO plansza VALUES (?, ?)";
            String checkQuery = "SELECT plansza.nazwa from plansza"; //nazwa board1 nazwa board10
            try (PreparedStatement statement = connection.prepareStatement(queryBoard)) {
                statement.setInt(1, primaryKey);
                try (Statement checkStatement = connection.createStatement()) {
                    try (ResultSet set = checkStatement.executeQuery(checkQuery)) {
                        ArrayList<String> temp = new ArrayList<>();
                        while (set.next()) {
                            temp.add(set.getString(1));
                        }
                        for (int i = 0; i < temp.size(); i++) {
                            if (Objects.equals(nameOfSavedBoard, temp.get(i))) {
                                nameOfSavedBoard += '0';
                                i = -1;
                            }
                        }
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                }
                statement.setString(2, this.getNameOfSavedBoard());
                statement.executeUpdate();

            } catch (SQLException e) {
                logger.error(e.getMessage());
            }

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String queryField = "INSERT INTO pole VALUES (?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(queryField)) {
                        statement.setInt(1, primaryKey);
                        statement.setInt(2, i);
                        statement.setInt(3, j);
                        statement.setInt(4, obj.get(i, j));
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }


    @Override
    public void close() throws Exception {

    }
}
