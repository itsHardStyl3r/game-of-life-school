import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import pl.comp.Density;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainSceneController {

    private ResourceBundle bundle = ResourceBundle.getBundle("messages", Main.DEFAULTLOCALE);

    @FXML
    private ComboBox<String> densityComboBox;

    @FXML
    private TextField rowsInput;

    @FXML
    private TextField colsInput;

    @FXML
    private Button pl;

    @FXML
    private Button en;

    public void initialize() {
        fillCombo();
        rowsInput.setTextFormatter(getFormatter(4, 20));
        colsInput.setTextFormatter(getFormatter(4, 20));

        pl.setOnAction(e -> changeLanguage("pl"));
        en.setOnAction(e -> changeLanguage("en"));
    }

    private TextFormatter<String> getFormatter(int min, int max) {
        return new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty() || newText.matches("\\d{0,2}")) {
                return change;
            }
            try {
                int value = Integer.parseInt(newText);
                if (value >= min && value <= max) {
                    return change;
                }
            } catch (NumberFormatException ignored) {
                // ignoruj
            }
            return null;
        });
    }

    private void fillCombo() {
        densityComboBox.getItems().clear();
        densityComboBox.getItems().addAll(
                bundle.getString("small"),
                bundle.getString("medium"),
                bundle.getString("large")
        );
        densityComboBox.setValue(bundle.getString("medium"));
    }

    @FXML
    public void startSimulation() {
        try {
            int rows = Integer.parseInt(rowsInput.getText());
            int cols = Integer.parseInt(colsInput.getText());
            String densityValue = densityComboBox.getValue();

            if (rows < 4 || rows > 20 || cols < 4 || cols > 20) {
                throw new IllegalArgumentException("Plansza moze miec wymiary od 4 do 20 w kazda strone.");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("simulation.fxml"));
            loader.setResources(bundle);
            Scene simulationScene = new Scene(loader.load());

            SimulationSceneController controller = loader.getController();
            controller.initializeBoard(rows, cols, fromString(densityValue));

            Stage stage = (Stage) rowsInput.getScene().getWindow();
            stage.setScene(simulationScene);
            stage.setTitle(bundle.getString("simulationTitle"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Density fromString(String s) {
        if (s.equalsIgnoreCase(bundle.getString("small"))) {
            return Density.LOW;
        } else if (s.equalsIgnoreCase(bundle.getString("medium"))) {
            return Density.MEDIUM;
        } else if (s.equalsIgnoreCase(bundle.getString("large"))) {
            return Density.HIGH;
        } else {
            return Density.FULL;
        }
    }

    public void changeLanguage(String languageCode) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            bundle = ResourceBundle.getBundle("messages", Locale.of(languageCode));
            loader.setResources(bundle);
            Stage stage = (Stage) rowsInput.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(loader.getResources().getString("title"));
            fillCombo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
