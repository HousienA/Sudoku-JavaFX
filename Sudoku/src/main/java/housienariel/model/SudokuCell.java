package housienariel.model;

public class SudokuCell {
    private final int correctValue;
    private int userValue;
    private final boolean isFixed;

    public SudokuCell(int row, int column, int value, boolean isInitialValue) {
        this.row = row;
        this.column = column;
        this.currentValue = value;
        this.isInitialValue = isInitialValue;
        if(value!=0){
            this.isInitialValue = true;
        }else if(value==0){
            this.isInitialValue = false;
        }
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