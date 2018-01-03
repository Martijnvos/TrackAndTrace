package controllers;

import classes.AccountManager;
import classes.Package;
import classes.PackageManager;
import convenienceClasses.AlertDialog;
import enums.ShippingType;
import enums.Status;
import globals.Globals;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TrackAndTrace {

    private AccountManager accountManager;
    private PackageManager packageManager;

    private ArrayList<Package> currentPackagesOfAccount;
    private List<TextField> packageDetailsItems;

    // Login/Register screen contents
    @FXML private TextField loginUsernameTextField, registerUsernameTextField, registerAddressTextField, registerEmailAddressTextField;
    @FXML private PasswordField loginPasswordTextField, registerPasswordTextField;
    @FXML private Button logInButton, registerButton;

    // Home screen contents
    @FXML private ListView<String> packagesListView;
    @FXML private Button homeLogOutButton, homePackageDetailsButton;

    // PackageDetails screen contents
    @FXML private Button packageEditButton, packageSaveButton, packageHomeButton;
    @FXML private TextField packageDetailsName, packageDetailsFromCompany, packageDetailsShippingType,
            packageDetailsStatus, packageDetailsSize, packageDetailsWeight, packageDetailsContents;
    @FXML private DatePicker packageDetailsExpectedDeliveryDate;

    public TrackAndTrace(){
        accountManager = new AccountManager();
        packageManager = new PackageManager();
    }

    /**
     * Do work once all FXML items have been loaded
     */
    @FXML
    public void initialize() {
        // Check certain elements to decide which Scene is initialized
        if (packagesListView != null) {
            prepareHomeScreen();
        } else if (packageEditButton != null) {
            // Populate list with all the textfields for easy traversal
            packageDetailsItems = Arrays.asList(packageDetailsName, packageDetailsFromCompany, packageDetailsShippingType,
                    packageDetailsStatus, packageDetailsSize, packageDetailsWeight, packageDetailsContents);

            if (Globals.packageToBeViewed != null) {
               packageDetailsName.setText(Globals.packageToBeViewed.getName());
               packageDetailsFromCompany.setText(Globals.packageToBeViewed.getFromCompany());
               packageDetailsShippingType.setText(Globals.packageToBeViewed.getShippingType().toString());
               packageDetailsStatus.setText(Globals.packageToBeViewed.getStatus().toString());
               packageDetailsSize.setText(Globals.packageToBeViewed.getSize());
               packageDetailsWeight.setText(String.valueOf(Globals.packageToBeViewed.getWeight()));
               packageDetailsContents.setText(Globals.packageToBeViewed.getContents());
               packageDetailsExpectedDeliveryDate.setValue(Globals.packageToBeViewed.getExpectedDeliveryDate());
            } else {
                AlertDialog.createAlert(Alert.AlertType.ERROR, "Couldn't get Package", "Package could not be found",
                        "The Package you want to edit could unfortunately not be fetched");
            }
        }
    }

    // LOGIN/REGISTER SCENE

    /**
     * Log-in flow
     * @param actionEvent ActionEvent corresponding to the button
     */
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
        } else {
            AlertDialog.createAlert(Alert.AlertType.ERROR, "Couldn't log in", "Log-in failure",
                    "You could not be logged in due to a credential mismatch or a faulty connection");
        }
    }

    /**
     * Register flow
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void registerAccount(ActionEvent actionEvent) {
        String username = registerUsernameTextField.getText();
        String password = registerPasswordTextField.getText();
        String address = registerAddressTextField.getText();
        String emailAddress = registerEmailAddressTextField.getText();

        if (Objects.equals(username, "")) AlertDialog.createAlert(Alert.AlertType.ERROR, "Couldn't register", "Registration failure",
                "You could not be registered due to empty ");

        boolean succeeded = accountManager.registerAccount(username, password, false, address, emailAddress);

        if (succeeded) {
            try {
                changeScene(actionEvent.getSource());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AlertDialog.createAlert(Alert.AlertType.ERROR, "Couldn't register", "Registration failure",
                    "You could not be registered due to a credential mismatch or a faulty connection");
        }
    }

    // HOME SCENE

    /**
     * Log out of application
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void homeLogOut(ActionEvent actionEvent) {
        try {
            accountManager.logOut(Globals.loggedInAccount);
            changeScene(actionEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * View Package details of specific package
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void viewPackageDetails(ActionEvent actionEvent) {
        int index = packagesListView.getSelectionModel().getSelectedIndex();

        if (index != -1) {
            Globals.packageToBeViewed = currentPackagesOfAccount.get(index);

            try {
                changeScene(actionEvent.getSource());
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
           AlertDialog.createAlert(Alert.AlertType.INFORMATION, "Please select Package", "No Package is selected",
                   "You have to select a Package before you can inspect the details");
        }
    }

    /**
     * Populate ListView in Home screen
     */
    private void prepareHomeScreen() {
        currentPackagesOfAccount = packageManager.getAllPackagesOfAccount(Globals.loggedInAccount.getID());
        if (currentPackagesOfAccount != null) {
            // Get description from packages based on the toString method
            List<String> packageDescriptions = currentPackagesOfAccount.stream().map(Object::toString).collect(Collectors.toList());
            // Apply the given descriptions to the ListView
            packagesListView.setItems(FXCollections.observableArrayList(packageDescriptions));
        }
    }

    // PACKAGEDETAILS SCENE

    /**
     * Make package editable taking the employee status of the currently logged in Account into consideration
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void editPackage(ActionEvent actionEvent) {
        packageSaveButton.setDisable(false);
        packageEditButton.setDisable(true);

        packageDetailsItems.forEach(item -> item.setEditable(true));
        packageDetailsExpectedDeliveryDate.setEditable(true);
    }

    /**
     * Save changes to the Package
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void saveEditedPackage(ActionEvent actionEvent) {
        String packageName = packageDetailsName.getText();
        String packageFromCompany = packageDetailsFromCompany.getText();
        String packageShippingType = packageDetailsShippingType.getText();
        String packageStatus = packageDetailsStatus.getText();
        String packageSize = packageDetailsSize.getText();
        String packageWeight = packageDetailsWeight.getText();
        String packageContents = packageDetailsContents.getText();
        LocalDate packageExpectedDeliveryDate = packageDetailsExpectedDeliveryDate.getValue();

        packageDetailsItems.forEach(item -> item.setEditable(false));
        packageDetailsExpectedDeliveryDate.setEditable(false);

        packageEditButton.setDisable(false);
        packageSaveButton.setDisable(true);

        packageManager.updatePackage(new Package(Globals.packageToBeViewed.getID(), packageName, packageFromCompany,
                ShippingType.valueOf(packageShippingType), Status.fromString(packageStatus), packageSize, Integer.valueOf(packageWeight),
                packageContents, packageExpectedDeliveryDate,
                Globals.packageToBeViewed.getLocationLat(), Globals.packageToBeViewed.getLocationLong()));

        AlertDialog.createAlert(Alert.AlertType.INFORMATION, "Package updated", "Package updated successfully",
                "Your Package has been succesfully updated!");
    }

    /**
     * Button to get back home
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void backHome(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent.getSource());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // CONVENIENCE METHOD(S)

    /**
     * Change Scene according to triggered ActionEvent of object
     * @param actionEventObject the object whose ActionEvent got triggered
     */
    private void changeScene(Object actionEventObject) throws IOException {
        Stage stage;
        Parent root;

        if (actionEventObject == logInButton || actionEventObject == registerButton) {
            stage = (Stage) logInButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Globals.HomeFileName));
        } else if (actionEventObject == homeLogOutButton) {
            stage = (Stage) homeLogOutButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Globals.LoginRegisterFileName));
        } else if (actionEventObject == homePackageDetailsButton) {
            stage = (Stage) homePackageDetailsButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Globals.PackageDetailsFileName));
        } else if (actionEventObject == packageHomeButton) {
            stage = (Stage) packageHomeButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Globals.HomeFileName));
        } else {
            return;
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}