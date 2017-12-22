package classes;

import globals.Globals;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(Globals.LoginRegisterFileName));
        primaryStage.setTitle(Globals.ApplicationTitle);
        primaryStage.setScene(new Scene(root, Globals.ApplicationSceneWidth, Globals.ApplicationSceneHeight));
        primaryStage.show();

        Globals.registry = LocateRegistry.getRegistry("127.0.0.1", 1099);

        new TrackAndTrace();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
