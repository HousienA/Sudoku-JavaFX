package housienariel.view;

import housienariel.model.SudokuModel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SudokuView extends BorderPane {

  private final GridView gridView;
  private SudokuModel myBoard;
  private final SudokuController controller;

  public SudokuView(SudokuController controller) {
    this.controller = controller;
    this.gridView = new GridView(controller);
  }

  public void start(Stage stage)
  {
    Scene scene = new Scene(gridView.getNumberPane(), 600, 600);
    stage.setScene(scene);
    stage.sizeToScene();
    stage.setResizable(false);
    stage.show();
  }

  public void updateBoard(int row, int col) {
    // TODO: Update the view...
  }

  public void updateBoard(SudokuModel board) {
    this.myBoard = board;
    // TODO: Update the view...
  }

  public GridView getGridView() {
    return gridView;
  }

  public SudokuController getController() {
    return controller;
  }

  public void showSolvedMessage() {
    System.out.println("Congratulations! You've solved the puzzle!");
  }

  public void showUnsolvedMessage() {
    System.out.println("Sorry, the puzzle is not yet solved.");
  }

  public void showErrorMessage() {
    System.out.println("Invalid move. Please try again.");
  }

    public void showAlert(String message) {
        System.out.println(message);
    }


}