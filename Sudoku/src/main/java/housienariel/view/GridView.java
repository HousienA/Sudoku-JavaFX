package housienariel.view;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static housienariel.model.SudokuUtilities.GRID_SIZE;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GridView {
    private Label[][] numberTiles;
    private final GridPane numberPane;
    private final SudokuController controller;

    public GridView(SudokuController controller) {
        this.controller = controller;
        numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        initNumberTiles();
        numberPane = makeNumberPane();
    }

    public GridPane getNumberPane() {
        return numberPane;
    }

    private void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label tile = new Label("");
                tile.setPrefWidth(32);
                tile.setPrefHeight(32);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
                tile.setOnMouseClicked(tileClickHandler);  // Attach event handler
                numberTiles[row][col] = tile;
            }
        }
    }

    private GridPane makeNumberPane() {
        GridPane root = new GridPane();
        root.setStyle("-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");
        int SECTIONS_PER_ROW = 3;
        int SECTION_SIZE = GRID_SIZE / SECTIONS_PER_ROW;
        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                GridPane section = new GridPane();
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        section.add(numberTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col], col, row);
                    }
                }
                root.add(section, scol, srow);
            }
        }
        return root;
    }

    private final EventHandler<MouseEvent> tileClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (event.getSource() == numberTiles[row][col]) {
                        controller.cellClicked(row, col);  // Notify the controller
                        return;
                    }
                }
            }
        }
    };
}