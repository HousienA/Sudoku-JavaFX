package housienariel.view;

import housienariel.model.SudokuLevel;
import housienariel.model.SudokuUtilities;

public class SudokuController {

    private int[][][] myBoard;
    private final SudokuView view;
    private SudokuLevel currentLevel;

    public SudokuController(SudokuView view) {
        this.view = view;
    }

    public void setNewGameDifficulty(int level) {
        switch (level) {
            case 1 -> currentLevel = SudokuLevel.EASY;
            case 2 -> currentLevel = SudokuLevel.MEDIUM;
            case 3 -> currentLevel = SudokuLevel.HARD;
            default -> currentLevel = SudokuLevel.MEDIUM;
        }

        myBoard = SudokuUtilities.generateSudokuMatrix(currentLevel);
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

    }

    private boolean isMoveValid(int row, int col) {
        return myBoard[row][col][0] == 0;
    }

    private boolean isBoardCorrect() {
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                if (myBoard[row][col][0] != myBoard[row][col][1]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[] getHint() {
        for (int row = 0; row < SudokuUtilities.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuUtilities.GRID_SIZE; col++) {
                if (myBoard[row][col][0] == 0) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }
}
