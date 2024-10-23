package housienariel.model;

import java.io.Serializable;

public class SudokuCell implements Serializable {
    private final int row;
    private final int column;
    private int currentValue;
    private boolean isInitialValue;

    public SudokuCell(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.currentValue = value;
        if(value == 0) this.isInitialValue = false;
        else this.isInitialValue = true;
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

    public void setCurrentValue(int newValue) {
        if(!this.isInitialValue) this.currentValue = newValue;
        else throw new IllegalStateException("This is is immutable");
    }
}