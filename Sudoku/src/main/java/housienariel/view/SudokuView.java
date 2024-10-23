package housienariel.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SudokuView {

  private final GridView gridView;
  private final SudokuController controller;

  public SudokuView(SudokuController controller) {
    this.controller = controller;
    this.gridView = new GridView();
  }

  public void start(Stage stage)
  {
    Scene scene = new Scene(gridView.getNumberPane(), 600, 600);
    stage.setScene(scene);
    stage.sizeToScene();
    stage.setResizable(false);
    stage.show();
  }

  public GridView getGridView() {
    return gridView;
  }

  public SudokuController getController() {
    return controller;
  }


}