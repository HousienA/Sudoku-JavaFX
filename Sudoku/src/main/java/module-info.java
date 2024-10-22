module housienariel.sudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens housienariel.sudoku to javafx.fxml;
    exports housienariel.sudoku;
}