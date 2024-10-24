package housienariel;

import housienariel.model.SudokuModel;
import housienariel.model.SudokuUtilities;
import housienariel.view.SudokuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private SudokuModel board;

    @Override
    public void start(Stage primaryStage) {

        board = new SudokuModel(SudokuUtilities.SudokuLevel.EASY);
        SudokuView view = new SudokuView(board);


        Scene scene = new Scene(view, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(true);

        // Set window title and show the stage
        primaryStage.setTitle("Sudoku Game");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}