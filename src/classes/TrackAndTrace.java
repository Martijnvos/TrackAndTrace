package classes;

import globals.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TrackAndTrace {

    private AccountManager accountManager;
    private PackageManager packageManager;

    @FXML private TextField loginUsernameTextField, loginPasswordTextField;
    @FXML private Button logInButton, trackAndTraceCloseButton;

    public TrackAndTrace(){
        accountManager = new AccountManager();
        packageManager = new PackageManager();
    }

    public void logUserIn(ActionEvent actionEvent) {
        String username = loginUsernameTextField.getText();
        String password = loginPasswordTextField.getText();
        boolean succeeded = accountManager.logIn(username, password);

        if (succeeded) {
            try {
                changeScene(actionEvent.getSource());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeTrackAndTrace(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change Scene according to triggered ActionEvent of object
     * @param actionEventObject the object whose ActionEvent got triggered
     */
    private void changeScene(Object actionEventObject) throws IOException {
        Stage stage;
        Parent root;

        if (actionEventObject == logInButton) {
            stage = (Stage) logInButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Globals.TrackAndTraceFileName));
        } else if (actionEventObject == trackAndTraceCloseButton) {
            stage = (Stage) trackAndTraceCloseButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Globals.LoginRegisterFileName));
        } else {
            return;
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}