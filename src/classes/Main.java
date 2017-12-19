package classes;

import globals.Globals;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(Globals.LayoutFileName));
        primaryStage.setTitle(Globals.ApplicationTitle);
        primaryStage.setScene(new Scene(root, Globals.ApplicationSceneWidth, Globals.ApplicationSceneHeight));
        primaryStage.show();

        new TrackAndTrace();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
