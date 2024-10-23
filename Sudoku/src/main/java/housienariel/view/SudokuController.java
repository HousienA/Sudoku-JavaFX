package housienariel.view;

import housienariel.model.SudokuCell;
import housienariel.model.SudokuModel;
import housienariel.model.SudokuUtilities;
import javafx.stage.FileChooser;

public class SudokuController {

    private SudokuModel myBoard;
    private final SudokuView view;
    private int numberPicked;
    private FileChooser fileChooser;
    private SudokuUtilities.SudokuLevel currentLevel;

    public SudokuController(SudokuModel board, SudokuView view) {
        //this.myBoard = board;
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

    }

    public void loadGame() {
    }

    public void numberSelected(int number) {
    }

    public void cellClicked(int row, int col) {

    }

    public void checkBoard()
    {
      if(isBoardCorrect()) view.showSuccessMessage();
      else view.showErrorMessage();
    }

    public void hintRequested() {

        if(!myBoard.isSolved()) myBoard.provideHint();
        //else view.showAlert("The board is already solved!");

    }

    private boolean isMoveValid(int row, int col) {
        return myBoard.isMoveValid(row, col);
    }

    private boolean isBoardCorrect() {
        return myBoard.isGridCorrect();
    }


}
