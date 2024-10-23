package housienariel.model;

public class SudokuCell {
    private final int row;
    private final int column;
    private int currentValue;
    private final boolean isInitialValue;

    public SudokuCell(int row, int column, int value, boolean isInitialValue) {
        this.row = row;
        this.column = column;
        this.currentValue = value;
        this.isInitialValue = isInitialValue;
        if(currentValue==0) this.isInitialValue = false;
        else this.isInitialValue = true;
    }

    public boolean isInitialValue() {
        return this.isInitialValue;
    }

    public void setInitialValue(){
        isInitialValue = true;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int newValue) {
        if(!this.isInitialValue)
            this.currentValue = newValue;
        else {
            throw new IllegalStateException("Immutable cell");
        }
    }
}