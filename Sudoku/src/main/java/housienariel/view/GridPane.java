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
    private final Label[][] numberTiles;  // The tiles/squares to show in the UI grid
    private final GridPane numberPane;
    private SudokuController controller;  // Reference to the controller

    public GridView() {
        numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        initNumberTiles();
        numberPane = makeNumberPane();
    }

    // Use this method to get a reference to the number pane (called by some other class)
    public GridPane getNumberPane() {
        return numberPane;
    }

    // Initialize number tiles, only called by the constructor
    private void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label tile = new Label(""); // Empty label to start, can add data from model later
                tile.setPrefWidth(32);
                tile.setPrefHeight(32);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
                // tile.setOnMouseClicked(tileClickHandler); // Uncomment and add your event handler here
                numberTiles[row][col] = tile;
            }
        }
    }

    // Create the grid pane and arrange number tiles
    private GridPane makeNumberPane() {
        GridPane root = new GridPane();
        root.setStyle("-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        int SECTIONS_PER_ROW = 3; // Define based on your game's logic
        int SECTION_SIZE = GRID_SIZE / SECTIONS_PER_ROW; // Assumes 9x9 grid, so each section is 3x3

        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                GridPane section = new GridPane();
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");

                // Add number tiles to this section
                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        section.add(numberTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col], col, row);
                    }
                }

                // Add the section to the root grid pane
                root.add(section, scol, srow);
            }
        }

        return root;
    }
}
