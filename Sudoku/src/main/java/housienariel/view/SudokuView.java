package housienariel.view;

import housienariel.model.SudokuModel;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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

    /**
     * Constructor for the SudokuView class.
     * @param board The SudokuModel object to be used.
     */
    public SudokuView(SudokuModel board) {
        super(); // Creates BorderPane
        this.controller = new SudokuController(board, this);
        this.board = board;
        gridSetup();
        createButtons();
        createMenu();
        addButtonEventHandlers();
    }

    /**
     * Updates the board with the new value at the specified row and column.
     * @param row The row of the cell to be updated.
     * @param col The column of the cell to be updated.
     */
    public void updateBoard(int row, int col) {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        createClickableCell(row, col, font);
        numberPane = makeNumberPane();
        this.setCenter(numberPane);
    }

    /**
     * Updates the board with the new SudokuModel object.
     * @param board The new SudokuModel object.
     */
    public void updateBoard(SudokuModel board) {
        this.board = board;
        gridSetup();
    }

    /**
     * Set the Sudoku grid.
     */
    private void gridSetup() {
        cellGrid = new Label[GRID_SIZE][GRID_SIZE];
        createSudokuGrid();
        numberPane = makeNumberPane();
        this.setCenter(numberPane);
    }

    /**
     * Create the Sudoku grid.
     * The grid is a 9x9 grid with 3x3 sections.
     */
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

    /**
     * Create a clickable cell.
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @param font The font of the cell.
     */
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

    /**
     * Create the number pane.
     * The number pane is a 9x9 grid with 3x3 sections.
     * @return The number pane.
     */
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

    /**
     * Create the buttons.
     * For Hint, Check and the number buttons.
     */
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
            if (buttonLabels[i].equals("C")) {
                buttons[i].setOnAction(event -> controller.clearBoard());
            } else {
                final int selectedNumber = Integer.parseInt(buttonLabels[i]);
                buttons[i].setOnAction(event -> controller.setNumberSelected(selectedNumber));
            }
            rightNumberButtons.getChildren().add(buttons[i]);
        }
        this.setRight(rightNumberButtons);
    }


    /**
     * Create the menu bar.
     */
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

    /**
     * Add event handlers to the buttons.
     */
    private void addButtonEventHandlers() {
        for (int i = 0; i < 10; i++) {
            int selectedNumber = i;
            buttons[i].setOnAction(event -> controller.setNumberSelected(selectedNumber));
        }

        checkButton.setOnAction(event -> showAlert(controller.checkBoard()));
        hintButton.setOnAction(event -> controller.hintRequested());
    }

    /**
     * Show an alert with the specified message.
     * @param message The message to be shown.
     */
    void showAlert(String message) {
        alert.setHeaderText("");
        alert.setTitle("Alert!");
        alert.setContentText(message);
        alert.show();
    }
}
