import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static final String FILESAVENAME = "save.txt";

    public static void main(String[] args) {
        launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        loader.setResources(ResourceBundleManager.getBundle());
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle(ResourceBundleManager.getBundle().getString("title"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        File f = new File(FILESAVENAME);
        if (f.exists()) {
            f.delete();
        }
    }
}
