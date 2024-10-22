package housienariel;

public class SudokuModel {

  public int[][] getGrid() { return new int[9][9]; }

  // code for setting Cell Value needs to be added
  public void setCellValue(int row, int col, int value) { };
  // code for checking valid move nedds to be added
  public boolean isValidMove(int row, int col, int value) { return true; };

  // code for checking if the game is complete needs to be added
  public boolean isComplete() { return true; };
}
