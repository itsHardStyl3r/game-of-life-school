import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.comp.*;

import java.util.ResourceBundle;

public class SimulationSceneController {

    private final FileGameOfLifeBoardDao fdao = new FileGameOfLifeBoardDao(Main.FILESAVENAME);
    @FXML
    private Label simulationTitle;
    @FXML
    private Button nextStepButton;
    @FXML
    private Button saveBoardButton;
    @FXML
    private Button loadBoardButton;
    @FXML
    private GridPane gameGrid;
    private GameOfLifeBoard gameBoard;

    private final ResourceBundle bundle = ResourceBundleManager.getBundle();

    @FXML
    public void initialize() {
        simulationTitle.setText(bundle.getString("simulationTitle"));
        nextStepButton.setText(bundle.getString("nextStep"));
        saveBoardButton.setText(bundle.getString("saveBoard"));
        loadBoardButton.setText(bundle.getString("loadBoard"));
    }

    public void initializeBoard(int rows, int cols, Density density) {
        gameBoard = new GameOfLifeBoard(rows, cols, new PlainGameOfLifeSimulator(), density);
        renderBoard();
    }

    private void renderBoard() {
        gameGrid.getChildren().clear();

        for (int i = 0; i < gameBoard.getRows().size(); i++) {
            for (int j = 0; j < gameBoard.getColumns().size(); j++) {
                GameOfLifeCell cell = gameBoard.getCell(i, j);

                Label cellLabel = new Label("\t");
                bindCellToLabel(cell, cellLabel);

                cellLabel.setOnMouseClicked(event -> {
                    cell.updateState(!cell.getCellValue());
                    renderBoard();
                });

                gameGrid.add(cellLabel, j, i);
            }
        }
    }

    @FXML
    public void performNextStep() {
        gameBoard.doSimulationStep();
        renderBoard();
    }

    private void bindCellToLabel(GameOfLifeCell cell, Label label) {
        try {
            JavaBeanBooleanProperty cellValueProperty = JavaBeanBooleanPropertyBuilder.create()
                    .bean(cell)
                    .name("cellValue")
                    .getter("getCellValue")
                    .setter("updateState")
                    .build();

            cellValueProperty.addListener((observable, oldValue, newValue) -> {
                label.setStyle(newValue
                        ? "-fx-background-color: green; -fx-padding: 5px;"
                        : "-fx-background-color: lightgray; -fx-padding: 5px;");
            });

            label.setStyle(cell.getCellValue()
                    ? "-fx-background-color: green; -fx-padding: 5px;"
                    : "-fx-background-color: lightgray; -fx-padding: 5px;");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveBoard() {
        try {
            fdao.write(gameBoard);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void loadBoard() {
        try {
            gameBoard = fdao.read();
            renderBoard();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
