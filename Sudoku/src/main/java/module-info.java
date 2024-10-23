module housienariel.sudoku {
    requires javafx.controls;
    requires javafx.fxml;



    exports housienariel.model;
    opens housienariel.model to javafx.fxml;
    exports housienariel.view;
    opens housienariel.view to javafx.fxml;
}