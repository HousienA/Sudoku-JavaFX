package housienariel.view;

import housienariel.model.SudokuModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public class SudokuView extends BorderPane {

    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Label[][] cellGrid;
    private TilePane numberPane;
    private SudokuModel board;
    private Button hintButton;
    private Button checkButton;
    private final Button[] buttons = new Button[10];
    private final SudokuController controller;

    public SudokuView(SudokuModel board) {
        super(); // Creates BorderPane
        this.controller = new SudokuController(board, this);
        this.board = board;
        gridSetup();
        createButtons();
        createMenu();
        addButtonEventHandlers();
    }

    // Updates a specific cell based on user interaction
    public void updateBoard(int row, int col) {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        createClickableCell(row, col, font);
        numberPane = makeNumberPane();
        this.setCenter(numberPane);
    }

    // Updates the entire board
    public void updateBoard(SudokuModel board) {
        this.board = board;
        gridSetup();
    }

    // Creates the sudoku grid and handles initialization
    private void gridSetup() {
        cellGrid = new Label[GRID_SIZE][GRID_SIZE];
        createSudokuGrid();
        numberPane = makeNumberPane();
        this.setCenter(numberPane);
    }

    private void createSudokuGrid() {
        Font font;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (!board.isInitialValue(row, col)) {
                    font = Font.font("Monospaced", FontWeight.NORMAL, 20);
                    createClickableCell(row, col, font);
                } else {
                    font = Font.font("Monospaced", FontWeight.EXTRA_BOLD, 20);
                    createClickableCell(row, col, font);
                }
            }
        }
    }

    private void createClickableCell(int row, int col, Font font) {
        Label cell;
        if (board.getCellValue(row, col) == 0) cell = new Label(" ");
        else cell = new Label(String.valueOf(board.getCellValue(row, col)));


        cell.setPrefWidth(40);
        cell.setPrefHeight(40);
        cell.setFont(font);
        cell.setAlignment(Pos.CENTER);
        cell.setStyle("-fx-border-color: black; -fx-border-width: 0.9px;");

        cell.setOnMouseClicked(event -> controller.cellClicked(row, col));
        cellGrid[row][col] = cell;
    }

    private TilePane makeNumberPane() {
        TilePane root = new TilePane();
        root.setPrefColumns(SECTIONS_PER_ROW);
        root.setPrefRows(SECTIONS_PER_ROW);
        root.setAlignment(Pos.CENTER);
        root.setHgap(5);
        root.setVgap(5);
        root.setStyle("-fx-border-color: black; -fx-border-width: 0.3px; -fx-background-color: white;");

        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                TilePane section = new TilePane();
                section.setPrefColumns(SECTION_SIZE);
                section.setPrefRows(SECTION_SIZE);
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.3px;");
                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        section.getChildren().add(cellGrid[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col]);
                    }
                }
                root.getChildren().add(section);
            }
        }
        return root;
    }

    private void createButtons() {
        checkButton = new Button("Check");
        hintButton = new Button("Hint");

        VBox leftButtons = new VBox(5);
        leftButtons.getChildren().addAll(checkButton, hintButton);
        leftButtons.setAlignment(Pos.CENTER);
        this.setLeft(leftButtons);

        VBox rightNumberButtons = new VBox();
        rightNumberButtons.setAlignment(Pos.CENTER);

        String[] buttonLabels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "C"};
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new Button(buttonLabels[i]);
            rightNumberButtons.getChildren().add(buttons[i]);
        }
        this.setRight(rightNumberButtons);
    }

    private void createMenu() {
        Menu fileMenu = new Menu("File");
        Menu gameMenu = new Menu("Game");
        Menu helpMenu = new Menu("Help");

        MenuItem exitItem = new MenuItem("Exit");
        MenuItem loadItem = new MenuItem("Load game");
        MenuItem saveItem = new MenuItem("Save game");

        exitItem.setOnAction(event -> Platform.exit());
        loadItem.setOnAction(event -> controller.loadGame());
        saveItem.setOnAction(event -> controller.saveGame());

        MenuItem startNewGame = new MenuItem("Start new game");
        Menu pickDifficulty = new Menu("Choose difficulty");
        MenuItem easy = new MenuItem("Easy");
        MenuItem medium = new MenuItem("Medium");
        MenuItem hard = new MenuItem("Hard");

        pickDifficulty.getItems().addAll(easy, medium, hard);
        easy.setOnAction(event -> controller.setNewGameDifficulty(1));
        medium.setOnAction(event -> controller.setNewGameDifficulty(2));
        hard.setOnAction(event -> controller.setNewGameDifficulty(3));

        startNewGame.setOnAction(event -> controller.handleNewGame());

        MenuItem clearAllItem = new MenuItem("Clear all");
        MenuItem checkItem = new MenuItem("Check");
        MenuItem infoItem = new MenuItem("Info");

        clearAllItem.setOnAction(event -> controller.clearBoard());
        checkItem.setOnAction(event -> showAlert(controller.checkBoard()));
        infoItem.setOnAction(event -> showAlert(controller.rules()));

        fileMenu.getItems().addAll(loadItem, saveItem, exitItem);
        gameMenu.getItems().addAll(startNewGame, pickDifficulty);
        helpMenu.getItems().addAll(clearAllItem, checkItem, infoItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu);
        this.setTop(menuBar);
    }

    private void addButtonEventHandlers() {
        for (int i = 0; i < 10; i++) {
            int selectedNumber = i;
            buttons[i].setOnAction(event -> controller.setNumberSelected(selectedNumber));
        }

        checkButton.setOnAction(event -> showAlert(controller.checkBoard()));
        hintButton.setOnAction(event -> controller.hintRequested());
    }

    void showAlert(String message) {
        alert.setHeaderText("");
        alert.setTitle("Alert!");
        alert.setContentText(message);
        alert.show();
    }
}
