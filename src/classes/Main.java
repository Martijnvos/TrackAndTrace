package classes;

import controllers.TrackAndTrace;
import globals.Globals;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.sound.midi.Track;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Globals.registry = LocateRegistry.getRegistry("localhost", 1099);

        FXMLLoader loader = new FXMLLoader( getClass().getResource(Globals.LoginRegisterFileName));
        Parent root = loader.load();
        primaryStage.setTitle(Globals.ApplicationTitle);
        Scene rootScene = new Scene(root, Globals.ApplicationSceneWidth, Globals.ApplicationSceneHeight);
        primaryStage.setScene(rootScene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            try {
                TrackAndTrace trackAndTrace = loader.getController();
                trackAndTrace.getPackageManager().getRemotePublisher().unsubscribeRemoteListener(
                        trackAndTrace.getPackageManager(), Globals.remotePublisherPackageChangesString);
            } catch(RemoteException e) {
                e.printStackTrace();
            }

            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
