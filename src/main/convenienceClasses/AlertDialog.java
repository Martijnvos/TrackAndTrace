package convenienceClasses;

import javafx.scene.control.Alert;

public class AlertDialog {
    /**
     * Creation of an Alert dialog for the user to see
     * @param type type of alert hat has to be issued
     * @param title title of the alert
     * @param headerText header text seen next to the AlertType icon
     * @param contentText alert message itself
     */
    public static void createAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
