import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.Density;
import pl.comp.GameOfLifeBoard;
import pl.comp.GameOfLifeCell;
import pl.comp.PlainGameOfLifeSimulator;
import pl.comp.storage.Dao;

import java.util.ResourceBundle;

import static pl.comp.storage.GameOfLifeBoardDaoFactory.createFileGameOfLifeBoardDao;

public class SimulationSceneController {

    private static GameOfLifeBoard gameBoard;
    private final CellStyleConverter cellStyleConverter = new CellStyleConverter();
    private final Logger logger = LoggerFactory.getLogger(SimulationSceneController.class);
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Main.getLocale());
    @FXML
    private GridPane gameGrid;

    public static GameOfLifeBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameOfLifeBoard board) {
        gameBoard = board;
    }

    @FXML
    public void initialize() {
    }

    public void initializeBoard(int rows, int cols, Density density) {
        gameBoard = new GameOfLifeBoard(rows, cols, new PlainGameOfLifeSimulator(), density);
        renderBoard();
    }

    public void renderBoard() {
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
    public void saveToFile() {
        try (Dao<GameOfLifeBoard> dao = createFileGameOfLifeBoardDao(Main.FILESAVENAME)) {
            dao.write(gameBoard);
        } catch (Exception e) {
            logger.error(bundle.getString("daoWriteException"), e.toString());
        }
    }

    @FXML
    public void loadFromFile() {
        try (Dao<GameOfLifeBoard> dao = createFileGameOfLifeBoardDao(Main.FILESAVENAME)) {
            gameBoard = dao.read();
            renderBoard();
        } catch (Exception e) {
            logger.error(bundle.getString("daoReadException"), e.toString());
        }
    }

    @FXML
    public void openDatabaseSave() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("databaseSave.fxml"));
            loader.setResources(bundle);
            Scene databaseSaveScene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(databaseSaveScene);
            stage.setTitle(bundle.getString("savePromptTitle"));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openDatabaseLoad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("databaseLoad.fxml"));
            loader.setResources(bundle);
            Scene databaseLoadScene = new Scene(loader.load());

            Stage stage = (Stage) gameGrid.getScene().getWindow();
            stage.setScene(databaseLoadScene);
            stage.setTitle(bundle.getString("loadPromptTitle"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
