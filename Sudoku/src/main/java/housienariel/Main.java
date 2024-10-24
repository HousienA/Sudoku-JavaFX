package housienariel;

import housienariel.model.*;
import housienariel.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    private SudokuModel board;

    @Override
    public void start(Stage primaryStage) {

        board = new SudokuModel(SudokuUtilities.SudokuLevel.EASY);
        SudokuView view = new SudokuView(board);


        Scene scene = new Scene(view, 550, 450);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);

        // Set window title and show the stage
        primaryStage.setTitle("Sudoku Game");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}