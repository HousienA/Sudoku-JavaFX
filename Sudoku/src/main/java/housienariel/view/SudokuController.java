package housienariel.view;

import housienariel.model.SudokuCell;
import housienariel.model.SudokuModel;
import housienariel.model.SudokuUtilities;
import housienariel.model.SudokuFileHandler;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class SudokuController {

    private SudokuModel myBoard;
    private final SudokuView view;
    private int numberSlected;
    private FileChooser fileChooser;
    private SudokuUtilities.SudokuLevel currentLevel;

    public SudokuController(SudokuModel board, SudokuView view) {
        this.myBoard = board;
        this.view = view;
        fileChooser = new FileChooser();

    }

    public void setNewGameDifficulty(int level) {
        switch (level) {
            case 1 -> currentLevel = SudokuUtilities.SudokuLevel.EASY;
            case 2 -> currentLevel = SudokuUtilities.SudokuLevel.MEDIUM;
            case 3 -> currentLevel = SudokuUtilities.SudokuLevel.HARD;
            default -> {}
        }

        myBoard = new SudokuModel(currentLevel);
    }

    public void saveGame() {
        try {
            fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home" + "/Downloads")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));
            File file = fileChooser.showSaveDialog(new Stage());

            if(file != null) {
                SudokuFileHandler.saveToFile(myBoard, file);
            }
        } catch (Exception savingError) {
            view.showAlert("Error saving file");
            throw new RuntimeException(savingError);
        }
    }

    public void loadGame() {

        try {
            fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home" + "/Downloads")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));
            File file = fileChooser.showOpenDialog(new Stage());

            if(file != null) {
                this.myBoard = SudokuFileHandler.loadFromFile(file);
                view.updateBoard(myBoard);
            }
        } catch (Exception loadingError) {
            view.showAlert("Error loading file");
            throw new RuntimeException(loadingError);
        }

    }

    public void setNumberSelected(int number) {
        this.numberSlected = number;
    }

    public void cellClicked(int row, int col) {
        try {
            if(numberSlected >= 0 && numberSlected < 9) {
                myBoard.setCellValue(row, col, numberSlected + 1);
                view.updateBoard(row, col);
            }
            if(numberSlected == 9) {
                myBoard.clearCell(row, col);
                view.updateBoard(row, col);
            }
        } catch(IllegalStateException invalidMove) { view.showErrorMessage(); }

    }

    public void checkBoard()
    {
      if(isBoardCorrect()) view.showSolvedMessage();
      else view.showErrorMessage();
    }

    public void hintRequested() {

        if(!myBoard.isSolved()) myBoard.provideHint();
        else view.showSolvedMessage();

    }

    private boolean isMoveValid(int row, int col) {
        return myBoard.isMoveValid(row, col);
    }

    private boolean isBoardCorrect() {
        return myBoard.isGridCorrect();
    }

    public String rules() {
        return "RULES:\n" +
                "Sudoku is a number puzzle where you fill a grid.\n" +
                "Each row, column, and block section must contain the numbers 1-9 without repetition.\n" +
                "The goal is to complete the grid following these rules!\n" +
                "You can use Check to see if your current grid is correct. And hint to get a free number!";
    }


}
