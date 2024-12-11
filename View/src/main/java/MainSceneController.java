import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.comp.Density;

public class MainSceneController {

    @FXML
    private TextField rowsInput;

    @FXML
    private TextField colsInput;

    @FXML
    private ComboBox<String> densityComboBox;

    @FXML
    private void startSimulation() {
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
            stage.setTitle("Game of Life (symulacja)");
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
}
