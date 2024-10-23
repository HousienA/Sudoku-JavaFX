package housienariel.model;

public class SudokuCell {
    private int correctValue;
    private int userValue;
    private boolean isFixed;

    public SudokuCell(int correctValue, boolean isFixed) {
        this.correctValue = correctValue;
        this.isFixed = isFixed;
        this.userValue = isFixed ? correctValue : 0;
    }

    public int getCorrectValue() {
        return correctValue;
    }

    public int getUserValue() {
        return userValue;
    }

    public void setUserValue(int userValue) {
        if (!isFixed) {
            this.userValue = userValue;
        }
    }

    public boolean isFixed() {
        return isFixed;
    }
}
