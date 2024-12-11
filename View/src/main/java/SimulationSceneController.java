import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.comp.Density;
import pl.comp.GameOfLifeBoard;
import pl.comp.PlainGameOfLifeSimulator;

public class SimulationSceneController {

    @FXML
    private GridPane gameGrid;

    private GameOfLifeBoard gameBoard;

    public void initializeBoard(int rows, int cols, Density density) {
        gameBoard = new GameOfLifeBoard(rows, cols, new PlainGameOfLifeSimulator(), density);
        renderBoard();
    }

    private void renderBoard() {
        gameGrid.getChildren().clear();
        for (int i = 0; i < gameBoard.getRows().size(); i++) {
            for (int j = 0; j < gameBoard.getColumns().size(); j++) {
                Label cell = new Label("\t");
                if (gameBoard.get(i, j)) {
                    cell.setStyle("-fx-background-color: green;-fx-padding: 5px;");
                } else {
                    cell.setStyle("-fx-background-color: lightgray;-fx-padding: 5px;");
                }
                gameGrid.add(cell, j, i);
            }
        }
    }

    @FXML
    private void performNextStep() {
        gameBoard.doSimulationStep();
        renderBoard();
    }
}
