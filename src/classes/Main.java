package classes;

import controllers.TrackAndTrace;
import globals.Globals;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Globals.registry = LocateRegistry.getRegistry("localhost", 1099);

        Parent root = FXMLLoader.load(getClass().getResource(Globals.LoginRegisterFileName));
        primaryStage.setTitle(Globals.ApplicationTitle);
        primaryStage.setScene(new Scene(root, Globals.ApplicationSceneWidth, Globals.ApplicationSceneHeight));
        primaryStage.show();

        new TrackAndTrace();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
