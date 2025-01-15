import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.GameOfLifeBoard;
import pl.comp.storage.Dao;

import java.util.ResourceBundle;

import static pl.comp.storage.GameOfLifeBoardDaoFactory.createDatabaseGameOfLifeBoardDao;

public class JdbcSaveController {

    private final Logger logger = LoggerFactory.getLogger(JdbcSaveController.class);
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Main.getLocale());

    @FXML
    private TextField gameName;

    @FXML
    public void initialize() {
        gameName.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty() || newText.matches("\\w{0,15}")) {
                return change;
            }
            return null;
        }));
    }

    @FXML
    public void saveToDatabase() {
        String gameName = this.gameName.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("savePromptTitle"));
        alert.setHeaderText(bundle.getString("savePromptTitle"));
        if (gameName.isEmpty()) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(bundle.getString("gameNameEmpty"));
            alert.show();
            return;
        }
        try (Dao<GameOfLifeBoard> dao = createDatabaseGameOfLifeBoardDao(gameName, false)) {
            dao.write(SimulationSceneController.getGameBoard());
            alert.setContentText(bundle.getString("dbGameSaved").replace("<gameName>", gameName));
        } catch (Exception e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(bundle.getString("dbGameSaveFailed").replace("<gameName>", gameName));
            logger.error(e.getMessage(), e);
        }
        alert.show();
    }
}
