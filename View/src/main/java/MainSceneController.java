import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.comp.Density;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainSceneController {

    private ResourceBundle bundle = ResourceBundleManager.getBundle();

    @FXML
    private Label titleLabel;

    @FXML
    private Label rowsInputLabel;

    @FXML
    private Label colsInputLabel;

    @FXML
    private Label densityLabel;

    @FXML
    private Label changeLocale;

    @FXML
    private ComboBox<String> densityComboBox;

    @FXML
    private TextField rowsInput;

    @FXML
    private TextField colsInput;

    @FXML
    private Button startButton;

    @FXML
    private Button pl;

    @FXML
    private Button en;

    public void initialize() {
        titleLabel.setText(bundle.getString("title"));
        rowsInputLabel.setText(bundle.getString("rows_label"));
        colsInputLabel.setText(bundle.getString("cols_label"));
        densityLabel.setText(bundle.getString("density_label"));
        rowsInput.setPromptText(bundle.getString("rows_prompt"));
        colsInput.setPromptText(bundle.getString("cols_prompt"));
        startButton.setText(bundle.getString("start_button"));
        changeLocale.setText(bundle.getString("changeLocale"));

        densityComboBox.getItems().clear();
        densityComboBox.getItems().addAll(
                bundle.getString("small"),
                bundle.getString("medium"),
                bundle.getString("large")
        );
        densityComboBox.setValue(bundle.getString("medium"));

        pl.setOnAction(e -> changeLanguage("pl"));
        en.setOnAction(e -> changeLanguage("en"));
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
        if (s.equalsIgnoreCase("MALY")) {
            return Density.LOW;
        } else if (s.equalsIgnoreCase("SREDNI")) {
            return Density.MEDIUM;
        } else if (s.equalsIgnoreCase("DUZY")) {
            return Density.HIGH;
        } else {
            return Density.FULL;
        }
    }

    public void changeLanguage(String languageCode) {
        ResourceBundleManager.setLocale(Locale.of(languageCode));
        bundle = ResourceBundleManager.getBundle();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Stage stage = (Stage) rowsInput.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(bundle.getString("title"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
