package housienariel.view;

import java.io.File;

import housienariel.model.SudokuFileHandler;
import housienariel.model.SudokuModel;
import housienariel.model.SudokuUtilities;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SudokuController {

    private SudokuModel myBoard;
    private final SudokuView view;
    private int numberSelected;
    private final FileChooser fileChooser;
    private SudokuUtilities.SudokuLevel currentLevel;


    /**
     * @param board The SudokuModel object
     * @param view The SudokuView object
     * New file object and default level is set to easy
     */
    public SudokuController(SudokuModel board, SudokuView view) {
        this.myBoard = board;
        this.view = view;
        this.fileChooser = new FileChooser();
        this.currentLevel = SudokuUtilities.SudokuLevel.EASY;  // Default level
    }

    /**
     * @param level The level of the new game
     */
    public void setNewGameDifficulty(int level) {
        switch (level) {
            case 1 -> currentLevel = SudokuUtilities.SudokuLevel.EASY;
            case 2 -> currentLevel = SudokuUtilities.SudokuLevel.MEDIUM;
            case 3 -> currentLevel = SudokuUtilities.SudokuLevel.HARD;
            default -> {}
        }

        myBoard = new SudokuModel(currentLevel);
        view.updateBoard(myBoard);
    }

    /**
     * Save the current game to a file with .sudoku extension
     */
    public void saveGame() {
        try {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Downloads"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) SudokuFileHandler.saveToFile(myBoard, file);

        } catch (Exception savingError) {
            view.showAlert("Error saving file");
            throw new RuntimeException(savingError);
        }
    }

    /**
     * Load a game from a file with .sudoku extension
     */
    public void loadGame() {
        try {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Downloads"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));
            File file = fileChooser.showOpenDialog(new Stage());

            if (file != null) {
                this.myBoard = SudokuFileHandler.loadFromFile(file);
                view.updateBoard(myBoard);
            }
        } catch (Exception loadingError) {
            view.showAlert("Error loading file");
            throw new RuntimeException(loadingError);
        }
    }

    /**
     * Handle the new game button
     */
    public void handleNewGame() {
        myBoard = new SudokuModel(currentLevel);
        view.updateBoard(myBoard);
    }

    public void setNumberSelected(int number) {
        this.numberSelected = number;
    }

    /**
     * @param row The row of the cell clicked
     * @param col The column of the cell clicked
     * Check if the move is valid and update the board
     */
    public void cellClicked(int row, int col) {
        try {
            if (isMoveValid(row, col)) {
                if (numberSelected >= 0 && numberSelected < 9) {
                    myBoard.setCellValue(row, col, numberSelected + 1);
                    view.updateBoard(row, col);
                }
                if (numberSelected == 9) {
                    myBoard.clearCell(row, col);
                    view.updateBoard(row, col);
                }
            } else view.showAlert("Invalid Move");
        } catch (IllegalStateException invalidMove) {
            view.showAlert("Invalid Move");
        }
        if (myBoard.isSolved()) {
            view.showAlert("Congratulations! You've solved the puzzle.");
        }
    }

    /**
     * @return A string with the result of the check
     */
    public String checkBoard() {
        if (!myBoard.isSolved()) {
            if (isBoardCorrect()) return "Placement is correct so far.";
            else return "Placement is incorrect!";
        } else return "The puzzle is solved!";
    }

    /**
     * Provide a hint to the user once the hint button is clicked
     */
    public void hintRequested() {
        if (!myBoard.isSolved()) {
            myBoard.provideHint();
            view.updateBoard(myBoard);
        } else view.showAlert("The puzzle is already solved!");

    }

    /**
     * Clear the board of all user-placed numbers
     */
    public void clearBoard() {
        myBoard.clearGrid();
        view.updateBoard(myBoard);
    }

    /**
     * @param row The row of the cell
     * @param col The column of the cell
     * @return True if the move is valid, false otherwise
     */
    private boolean isMoveValid(int row, int col) { return myBoard.isMoveValid(row, col); }

    private boolean isBoardCorrect() { return myBoard.isGridCorrect(); }

    /**
     * @return A string with the rules of the game
     */
    public String rules() {
        return """
                RULES:
                Sudoku is a number puzzle where you fill a grid.
                Each row, column, and block section must contain the numbers 1-9 without repetition.
                The goal is to complete the grid following these rules!
                You can use Check to see if your current grid is correct and Hint to get a free number!""";
    }
}