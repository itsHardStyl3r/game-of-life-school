import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pl.comp.*;

public class SimulationSceneController {

    private final FileGameOfLifeBoardDao fdao = new FileGameOfLifeBoardDao(Main.FILESAVENAME);
    private final CellStyleConverter cellStyleConverter = new CellStyleConverter();
    @FXML
    private GridPane gameGrid;
    private GameOfLifeBoard gameBoard;

    @FXML
    public void initialize() {
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
                try {
                    Label cellLabel = new Label("\t");
                    JavaBeanBooleanProperty cellValueProperty = JavaBeanBooleanPropertyBuilder.create()
                            .bean(cell)
                            .name("cellValue")
                            .getter("getCellValue")
                            .setter("updateState")
                            .build();

                    cellLabel.styleProperty().bindBidirectional(cellValueProperty, cellStyleConverter);
                    cellLabel.setOnMouseClicked(event -> {
                        cell.updateState(!cell.getCellValue());
                        renderBoard();
                    });
                    gameGrid.add(cellLabel, j, i);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void performNextStep() {
        gameBoard.doSimulationStep();
        renderBoard();
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
