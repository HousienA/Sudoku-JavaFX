package housienariel;

public class SudokuCell {

  int value;
  boolean isLocked;

  public SudokuCell(){
    this.value = 0;
    this.isLocked = false;
  }

  public int getvalue() {
    return value;
  }

  public void setvalue(int value) {
    this.value = value;
  }

  public boolean isLocked() {
    return isLocked;
  }

  public void setLocked(boolean locked) {
   this.isLocked = locked;
  }

}
