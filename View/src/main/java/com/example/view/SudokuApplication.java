package com.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Popup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.*;


import java.util.ResourceBundle;

public class SudokuApplication extends Application {

    private static final Logger logger = LogManager.getLogger("model");

    private TextField[][] fields = new TextField[9][9];

    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    private IntegerProperty[][] boardProperty = new SimpleIntegerProperty[9][9];

    private ResourceBundle bundle;

    public static void main(String[] args) {
        launch(args);
    }

    private void initialize() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fields[i][j] = new TextField();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Label name = new Label("SUDOKU");
        name.setStyle("-fx-font-size: 20");

        ChoiceBox<String> langChoice = new ChoiceBox<>();
        langChoice.getItems().addAll("English", "Polski");
        langChoice.setValue("Select language");

        langChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("English")) {
                    bundle = ResourceBundle.getBundle("com.example.view.MyBundle_en");
                } else if (newValue.equals("Polski")) {
                    bundle = ResourceBundle.getBundle("com.example.view.MyBundle_pl");
                }
                this.version(primaryStage,bundle);
            }
        });

        VBox elements = new VBox();
        elements.getChildren().addAll(name, langChoice);
        elements.setAlignment(Pos.TOP_CENTER);


        BorderPane root = new BorderPane();
        root.setCenter(elements);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void version(Stage primaryStage, ResourceBundle language) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 400);
        board.solveGame();


        ChoiceBox<String> difficultyChoice = new ChoiceBox<>();
        difficultyChoice.getItems().addAll(language.getString("easyLevel"), language.getString("mediumLevel"), language.getString("hardLevel"));
        difficultyChoice.setValue(language.getString("easyLevel"));

        Button startButton = new Button(language.getString("startGame"));
        startButton.setOnAction(e -> {
            this.buttonClick(root, difficultyChoice);
            this.showSudokuGame(primaryStage, language);
        });

        HBox topMenu = new HBox(10);
        topMenu.setPadding(new Insets(10, 10, 10, 10));
        topMenu.getChildren().addAll(difficultyChoice, startButton);
        topMenu.setAlignment(Pos.TOP_CENTER);
        root.setTop(topMenu);

        primaryStage.setScene(scene);
        primaryStage.setTitle(language.getString("title"));
        primaryStage.show();
    }

    private void buttonClick(BorderPane root, ChoiceBox<String> difficultyChoice) {
        String selectedDifficulty = difficultyChoice.getValue();
        LevelDesignator adapter = new LevelDesignator();
        adapter.set(selectedDifficulty).digger(board);
    }

    private void showGrid(BorderPane root) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        this.initialize(); //inicjalizacja fieldow

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                if (board.get(i, j) == 0) {
                    textField.setText("");
                } else {
                    textField.setText(Integer.toString(board.get(i, j)));
                }
                fields[i][j] = textField;
                fields[i][j].setMinSize(30, 30);
                gridPane.add(fields[i][j], j, i);

                int row = i;
                int col = j;

                PropertyBoardAdapter adapter = new PropertyBoardAdapter();
                boardProperty[row][col] = adapter.convert(board,row,col);

                ZeroToStringConverter converter = new ZeroToStringConverter(); // konwerter

                boardProperty[row][col].addListener((observable, oldValue, newValue) -> {
                    fields[row][col].setText(newValue.toString());
                    board.set(row,col, newValue.intValue());
                });

                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (isValidInput(newValue)) {
                        fields[row][col].setText(newValue);
                        board.set(row,col, converter.fromString(newValue));
                        boardProperty[row][col].setValue(converter.fromString(newValue));
                    } else {
                        textField.setText(oldValue);
                    }
                });
            }
        }
        root.setCenter(gridPane);
    }

    private void showSudokuGame(Stage gameStage, ResourceBundle lang) {
        BorderPane game = new BorderPane();
        VBox buttons = new VBox(10);
        Button backButton = new Button(lang.getString("backButton"));
        Button saveButton = new Button(lang.getString("saveButton"));
        Button readButton = new Button(lang.getString("loadButton"));
        Button checkButton = new Button(lang.getString("checkButton"));

        buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.getChildren().addAll(backButton, saveButton, readButton, checkButton);
        game.setRight(buttons);

        backButton.setOnAction(t -> this.start(gameStage));
        saveButton.setOnAction(t -> this.saveToDataBase(lang, gameStage));
        readButton.setOnAction(t -> this.loadFromFile(lang, gameStage));
        checkButton.setOnAction(t -> game.setBottom(this.gameOver(lang)));

        showGrid(game);
        Scene gameScene = new Scene(game, 500, 500);
        gameStage.setScene(gameScene);
        gameStage.show();
    }

    private void saveToDataBase(ResourceBundle lang, Stage stage) { // save board to database
        BorderPane pane = new BorderPane();
        VBox query = new VBox(10);
        Button save = new Button(lang.getString("acceptSave"));
        Label information = new Label(lang.getString("saveBoard"));
        TextField choose = new TextField();
        query.getChildren().addAll(information,choose, save); //dodac max rozmiar 20 znkakow
        query.setAlignment(Pos.TOP_CENTER);

        save.setOnAction(t -> {try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getJdbcDao(choose.getText())) {
            dao.write(board);
            this.showSudokuGame(stage, lang);
        } catch (Exception e) {
            ResourceBundle bundle1 = ResourceBundle.getBundle("org.example.MyBundleException_en");
            logger.error(bundle1.getString("io"));
            throw new SudokuException.SudokuIoException(bundle1.getString("io"));} //uzytkownik nie moze zapisac tego samego boarda
        });

        pane.setCenter(query);
        Scene scene = new Scene(pane, 400, 400);
        stage.setScene(scene);
        stage.show();


    }

    private void loadFromFile(ResourceBundle lang, Stage stage) { // load board from database
        Button acceptSave = new Button(lang.getString("applyButton"));
        ListView<String> list = new ListView<>();

        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getJdbcDao("board")) {
            BorderPane pane = new BorderPane();
            VBox query = new VBox(10);



            Label information = new Label(lang.getString("loadBoard")); //dodac max rozmiar 20 znkakow
            query.setAlignment(Pos.TOP_CENTER);

            list.getItems().addAll(dao.getNames());

            query.getChildren().addAll(information, list, acceptSave);
            pane.setCenter(query);
            Scene scene = new Scene(pane, 400, 400);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("First dao failure");
        }


         acceptSave.setOnAction(t -> {try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getJdbcDao("board")) {
             dao.setNameOfLoadedBoard(list.getSelectionModel().getSelectedItem());
             SudokuBoard newBoard = dao.read();
             board = newBoard;
             this.showSudokuGame(stage, lang);

         } catch (Exception e) {
             ResourceBundle bundle1 = ResourceBundle.getBundle("org.example.MyBundleException_en");
             logger.error(bundle1.getString("io"));
             throw new SudokuException.SudokuIoException(bundle1.getString("io"));

         }});


//            for (int i = 0; i < 9; i++) {
//                for (int j = 0; j < 9; j++) {
//                    int value = board.get(i, j);
//                    fields[i][j].setText(value == 0 ? "" : String.valueOf(value));
//                }
//            }
//
//            for (int i = 0; i < 9; i++) {
//                for (int j = 0; j < 9; j++) {
//                    boardProperty[i][j] = new SimpleIntegerProperty();
//                    boardProperty[i][j].setValue(board.get(i, j));
//
//                    int row = i;
//                    int col = j;
//
//                    boardProperty[i][j].addListener((observable, oldValue, newValue) -> {
//                        fields[row][col].setText(newValue.toString());
//                        board.set(row, col, newValue.intValue());
//                    });
//                }
//            }
    }


    private Label gameOver(ResourceBundle lang) {
        Label result = new Label();
        if (board.checkBoard()) {
            result.setText(lang.getString("victoryLabel"));
        } else {
            result.setText(lang.getString("lostLabel"));
        }
        result.setStyle("-fx-font-size: 20");
        return result;
    }

    private static boolean isValidInput(String input) {
        return input.matches("[1-9]") || input.isEmpty() || input.matches(" ");
    }
}
