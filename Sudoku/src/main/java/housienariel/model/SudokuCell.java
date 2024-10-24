package housienariel.model;

import java.io.Serializable;

public class SudokuCell implements Serializable {
    private final int row;
    private final int column;
    private int currentValue;
    private boolean isInitialValue;


    /**
     * Sets whether a cell is a initialValue based on whether the number is zero or not.
     * @param row the row index of the cell
     * @param column the col index of the cell
     * @param value is the initial number for the cell
     */
    public SudokuCell(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.currentValue = value;
        this.isInitialValue = value != 0;
    }

    public boolean isInitialValue() {
        return this.isInitialValue;
    }

    public void setInitialValue(){
        this.isInitialValue = true;
    }

    public int getCurrentValue() {
        return this.currentValue;
    }

    /**
     * Sets a new value for the cell if the cell is not immutable else throw exception.
     * @param newValue is the value that the cell value will have
     * @throw if the cell is immutable(initialValue = true).
     */
    public void setCurrentValue(int newValue) {
        if(!this.isInitialValue) this.currentValue = newValue;
        else throw new IllegalStateException("This is is immutable");
    }
}