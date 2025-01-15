import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.GameOfLifeBoard;
import pl.comp.storage.JdbcGameOfLifeBoardDao;
import pl.comp.storage.JdbcGameOfLifeReader;

import java.util.ResourceBundle;

public class JdbcLoadController {

    private final Logger logger = LoggerFactory.getLogger(JdbcLoadController.class);
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Main.getLocale());

    @FXML
    private ListView<String> listView;

    @FXML
    public void initialize() {
        try (JdbcGameOfLifeReader reader = new JdbcGameOfLifeReader()) {
            ObservableList<String> items = FXCollections.observableArrayList(reader.getBoards());
            listView.setItems(items);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @FXML
    public void read() {
        String gameName = listView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("loadPromptTitle"));
        alert.setHeaderText(bundle.getString("loadPromptTitle"));
        try (JdbcGameOfLifeBoardDao dao = new JdbcGameOfLifeBoardDao(gameName, false)) {
            GameOfLifeBoard board = dao.read();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulation.fxml"));
            loader.setResources(bundle);
            Scene simulationScene = new Scene(loader.load());
            SimulationSceneController controller = loader.getController();
            controller.setGameBoard(board);
            controller.renderBoard();
            Stage stage = (Stage) listView.getScene().getWindow();
            stage.setScene(simulationScene);
            stage.setTitle(bundle.getString("simulationTitle"));

            alert.setContentText(bundle.getString("dbGameLoaded").replace("<gameName>", gameName));
        } catch (Exception e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(bundle.getString("dbGameLoadFailed").replace("<gameName>", gameName));
            logger.error(e.getMessage(), e);
        }
        alert.showAndWait();
    }
}
