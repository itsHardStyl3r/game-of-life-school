import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static final String FILESAVENAME = "save.txt";
    public static final Locale DEFAULTLOCALE = Locale.of("pl_PL");

    public static void main(String[] args) {
        launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        loader.setResources(ResourceBundle.getBundle("messages", DEFAULTLOCALE));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle(loader.getResources().getString("title"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        File file = new File(FILESAVENAME);
        file.delete();
    }
}
