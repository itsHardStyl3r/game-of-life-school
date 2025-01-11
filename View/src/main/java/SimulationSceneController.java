import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.*;
import pl.comp.storage.Dao;
import pl.comp.storage.FileGameOfLifeBoardDao;

import java.util.ResourceBundle;

public class SimulationSceneController {

    private final CellStyleConverter cellStyleConverter = new CellStyleConverter();
    private final Logger logger = LoggerFactory.getLogger(SimulationSceneController.class);
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Main.getLocale());

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
                        cellValueProperty.set(!cellValueProperty.get());
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
        try (Dao<GameOfLifeBoard> dao = new FileGameOfLifeBoardDao(Main.FILESAVENAME)) {
            dao.write(gameBoard);
        } catch (Exception e) {
            logger.error(bundle.getString("daoWriteException"), e);
        }
    }

    @FXML
    public void loadBoard() {
        try (Dao<GameOfLifeBoard> dao = new FileGameOfLifeBoardDao(Main.FILESAVENAME)) {
            gameBoard = dao.read();
            renderBoard();
        } catch (Exception e) {
            logger.error(bundle.getString("daoReadException"), e);
        }
    }
}
