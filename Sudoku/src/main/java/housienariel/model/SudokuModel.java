package housienariel.model;

import java.io.Serializable;

public class SudokuModel implements Serializable {
    private final SudokuCell[][] grid;

    public SudokuModel(SudokuLevel level) {
        int[][][] initialGrid = SudokuUtilities.generateSudokuMatrix(level);
        grid = new SudokuCell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new SudokuCell(i, j, initialGrid[i][j][0], initialGrid[i][j][0] != 0);
            }
        }
    }

    public SudokuCell[][] getGrid() {
        return grid;
    }

    public void setCellValue(int row, int col, int value) {
        grid[row][col].setCurrentValue(value);
    }

    public int[][] getGridCopy() {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy[i][j] = grid[i][j].getCurrentValue();
            }
        }
        return copy;
    }


}