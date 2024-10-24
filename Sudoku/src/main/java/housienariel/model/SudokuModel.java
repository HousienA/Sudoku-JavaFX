package housienariel.model;

import java.io.Serializable;
import java.util.Random;

public class SudokuModel implements Serializable {

    public static final int SIZE = 9;

    private SudokuCell[][] grid;
    private SudokuCell[][] correctGrid;
    private int[][][] referenceCopy;
    private SudokuUtilities.SudokuLevel level;


    public SudokuModel(SudokuUtilities.SudokuLevel level) {
        setLevel(level);
        initGrid();
        initCorrectGrid();
    }

    // Initialize the game board
    private void initGrid() {
        grid = new SudokuCell[SIZE][SIZE];
        for (int i = 0; i < SIZE * SIZE; i++) {
            int row = i / SIZE;
            int col = i % SIZE;
            grid[row][col] = new SudokuCell(row, col, referenceCopy[row][col][0]);
        }
    }

    // Initialize the correct solution board
    private void initCorrectGrid() {
        correctGrid = new SudokuCell[SIZE][SIZE];
        for (int i = 0; i < SIZE * SIZE; i++) {
            int row = i / SIZE;
            int col = i % SIZE;
            correctGrid[row][col] = new SudokuCell(row, col, referenceCopy[row][col][1]);
        }
    }


    public int[][] getGridCopy() {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                copy[i][j] = grid[i][j].getCurrentValue();
            }
        }
        return copy;
    }

    public SudokuCell[][] getGrid() {
        return grid;
    }

    public void setCellValue(int row, int col, int value) {
        grid[row][col].setCurrentValue(value);
    }

    public int getCellValue(int row, int col) {
        return grid[row][col].getCurrentValue();
    }

    public boolean isInitialValue(int row, int col) {
        return grid[row][col].isInitialValue();
    }

    public void setLevel(SudokuUtilities.SudokuLevel level) {
        referenceCopy = SudokuUtilities.generateSudokuMatrix(level);
        this.level = level;
    }

    public SudokuUtilities.SudokuLevel getLevel() { return level; }

    public String getCellString(int row, int col) {
        return Integer.toString(grid[row][col].getCurrentValue());
    }

    public void clearCell(int row, int col) { grid[row][col].setCurrentValue(0); }

    public void clearGrid() {
        for(SudokuCell[] row : grid) {
            for(SudokuCell cell : row) {
                if(!cell.isInitialValue()) {
                    cell.setCurrentValue(0);
                }
            }
        }
    }
    // Check if the current grid is correct so far
    public boolean isGridCorrect() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int currentValue = grid[row][col].getCurrentValue();
                if (currentValue != correctGrid[row][col].getCurrentValue() && currentValue != 0) {
                    return false;
                }
            }
        }
        return true;
    }


   public void provideHint() {
    Random rand = new Random();
    int row, col;
       do {
           row = rand.nextInt(SIZE);
           col = rand.nextInt(SIZE);
       } while (grid[row][col].getCurrentValue() != 0 && grid[row][col].getCurrentValue() == correctGrid[row][col].getCurrentValue());

    grid[row][col].setCurrentValue(correctGrid[row][col].getCurrentValue());
    grid[row][col].setInitialValue();
}

    // Check if the grid is solved
    public boolean isSolved() {
        for (int i = 0; i < SIZE * SIZE; i++) {
            int row = i / SIZE;
            int col = i % SIZE;
            if (grid[row][col].getCurrentValue() != correctGrid[row][col].getCurrentValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean isMoveValid(int row, int col) {
        return grid[row][col].isInitialValue() == false;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                sb.append(grid[row][col].getCurrentValue()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}