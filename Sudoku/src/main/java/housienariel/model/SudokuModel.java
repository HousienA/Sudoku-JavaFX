package housienariel.model;

import java.io.Serializable;
import java.util.Random;

public class SudokuModel implements Serializable {

    public static final int SIZE = 9;

    private SudokuCell[][] grid;
    private SudokuCell[][] correctGrid;
    private int[][][] referenceCopy;
    private SudokuUtilities.SudokuLevel level;


    /**
     * Constructor, initializes a new sudoku game with solution grid.
     * @param level represents the difficulty of the game.
     */
    public SudokuModel(SudokuUtilities.SudokuLevel level) {
        setLevel(level);
        initGrid();
        initCorrectGrid();
    }

    /**
     * Initialize the game board with the initial values.
     * The initial values are set from the referenceCopy.
     */
    private void initGrid() {
        grid = new SudokuCell[SIZE][SIZE];
        for (int i = 0; i < SIZE * SIZE; i++) {
            int row = i / SIZE;
            int col = i % SIZE;
            grid[row][col] = new SudokuCell(row, col, referenceCopy[row][col][0]);
        }
    }

    /**
     * Initialize the correct grid with the solution values.
     * The solution values are set from the referenceCopy.
     */
    private void initCorrectGrid() {
        correctGrid = new SudokuCell[SIZE][SIZE];
        for (int i = 0; i < SIZE * SIZE; i++) {
            int row = i / SIZE;
            int col = i % SIZE;
            correctGrid[row][col] = new SudokuCell(row, col, referenceCopy[row][col][1]);
        }
    }


    /**
     * Get a copy of the current grid.
     * @return a copy of the current grid.
     */
    public int[][] getGridCopy() {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                copy[i][j] = grid[i][j].getCurrentValue();
            }
        }
        return copy;
    }

    /**
     * @return the grid.
     */
    public SudokuCell[][] getGrid() {
        return grid;
    }

    /**
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @param value the value to set.
     */
    public void setCellValue(int row, int col, int value) {
        grid[row][col].setCurrentValue(value);
    }

    /**
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return the value of the cell.
     */
    public int getCellValue(int row, int col) {
        return grid[row][col].getCurrentValue();
    }

    /**
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return true if the cell is an initial value, false otherwise.
     */
    public boolean isInitialValue(int row, int col) {
        return grid[row][col].isInitialValue();
    }

    /**
     * @param level
     */
    public void setLevel(SudokuUtilities.SudokuLevel level) {
        referenceCopy = SudokuUtilities.generateSudokuMatrix(level);
        this.level = level;
    }

    public SudokuUtilities.SudokuLevel getLevel() { return level; }


    /**
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return the value of the cell as a string.
     */
    public String getCellString(int row, int col) {
        return Integer.toString(grid[row][col].getCurrentValue());
    }

    /**
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return the value of the cell as a string.
     */
    public void clearCell(int row, int col) { grid[row][col].setCurrentValue(0); }

    /**
     * Clear the grid of all values if they are not initial values.
     */
    public void clearGrid() {
        for(SudokuCell[] row : grid) {
            for(SudokuCell cell : row) {
                if(!cell.isInitialValue()) {
                    cell.setCurrentValue(0);
                }
            }
        }
    }

    /**
     * Check if the values in the grid is correct.
     * @return true if the grid is correct, false otherwise.
     */
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

    /**
     * Provide a hint to the player
     * setting a random cell to the correct value and set it as initialValue.
     */
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

    /**
     * Check if the game is solved.
     * @return true if the game is solved, false otherwise.
     */
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

    /**
     * Check if a move is valid.
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return true if the move is valid as in th cell doesn't have an initialValue.
     */
    public boolean isMoveValid(int row, int col) {
        return !grid[row][col].isInitialValue();
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