package controllers;

import classes.Account;
import classes.AccountManager;
import classes.Package;
import classes.PackageManager;
import convenienceClasses.AlertDialog;
import enums.ShippingType;
import enums.Status;
import globals.Globals;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
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
    private ObservableList<String> observablePackageList;

    public PackageManager getPackageManager() {
        return packageManager;
    }

    // Login/Register screen contents
    @FXML private TextField loginUsernameTextField, registerUsernameTextField, registerAddressTextField, registerEmailAddressTextField;
    @FXML private PasswordField loginPasswordTextField, registerPasswordTextField;
    @FXML private Button logInButton, registerButton;

    // Home screen contents
    @FXML private ListView<String> packagesListView;
    @FXML private Button homeLogOutButton, homePackageDetailsButton, homeEditAccountButton,
            homeRemovePackageButton, homeAddPackageButton;

    // PackageDetails screen contents
    @FXML private Button packageEditButton, packageSaveButton, packageHomeButton;
    @FXML private TextField packageDetailsName, packageDetailsFromCompany, packageDetailsSize,
            packageDetailsWeight, packageDetailsContents;
    @FXML private ComboBox<ShippingType> packageDetailsShippingType;
    @FXML private ComboBox<Status> packageDetailsStatus;
    @FXML private DatePicker packageDetailsExpectedDeliveryDate;

    // AccountSettings screen contents
    @FXML private TextField accountSettingsUsername, accountSettingsAddress, accountSettingsEmailAddress;
    @FXML private Button accountSettingsSaveButton, accountSettingsEditButton, accountSettingsHomeButton;
    @FXML private PasswordField accountSettingsPassword;
    @FXML private Label accountSettingsEmployeeStatus;

    //AddPackage screen contents
    @FXML private TextField addPackageName, addPackageFromCompany, addPackageSize, addPackageWeight,
            addPackageContents, addPackageLatitude, addPackageLongitude;
    @FXML private ComboBox<ShippingType> addPackageShippingType;
    @FXML private ComboBox<Status> addPackageStatus;
    @FXML private Button addPackageButton, addPackageHomeButton;
    @FXML private DatePicker addPackageExpectedDeliveryDate;

    public TrackAndTrace(){
        accountManager = new AccountManager();
        try {
            packageManager = new PackageManager(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
            packageDetailsItems = Arrays.asList(packageDetailsName, packageDetailsFromCompany,
                    packageDetailsSize, packageDetailsWeight, packageDetailsContents);

            // Fill ComboBoxes with appropriate enum values
            packageDetailsShippingType.setItems(FXCollections.observableArrayList(ShippingType.values()));
            packageDetailsStatus.setItems(FXCollections.observableArrayList(Status.values()));

            if (Globals.packageToBeViewed != null) {
                // Set all values of Package to be viewed
               packageDetailsName.setText(Globals.packageToBeViewed.getName());
               packageDetailsFromCompany.setText(Globals.packageToBeViewed.getFromCompany());
               packageDetailsShippingType.setValue(Globals.packageToBeViewed.getShippingType());
               packageDetailsStatus.setValue(Globals.packageToBeViewed.getStatus());
               packageDetailsSize.setText(Globals.packageToBeViewed.getSize());
               packageDetailsWeight.setText(String.valueOf(Globals.packageToBeViewed.getWeight()));
               packageDetailsContents.setText(Globals.packageToBeViewed.getContents());
               packageDetailsExpectedDeliveryDate.setValue(Globals.packageToBeViewed.getExpectedDeliveryDate());
            } else {
                AlertDialog.createAlert(Alert.AlertType.ERROR, "Couldn't get Package", "Package could not be found",
                        "The Package you want to edit could unfortunately not be fetched");
            }
        } else if (accountSettingsEditButton != null) {
            if (Globals.loggedInAccount != null) {
                accountSettingsUsername.setText(Globals.loggedInAccount.getUsername());
                accountSettingsPassword.setText(Globals.loggedInAccount.getPassword());
                accountSettingsEmployeeStatus.setText(String.valueOf(Globals.loggedInAccount.isEmployee()));
                accountSettingsAddress.setText(Globals.loggedInAccount.getAddress());
                accountSettingsEmailAddress.setText(Globals.loggedInAccount.getEmailAddress());
            } else {
                AlertDialog.createAlert(Alert.AlertType.ERROR, "Couldn't get logged in Account", "Account could not be fetched",
                        "The Account you want to edit could unfortunately not be fetched");
            }
        } else if (addPackageButton != null) {
            addPackageShippingType.setItems(FXCollections.observableArrayList(ShippingType.values()));
            addPackageStatus.setItems(FXCollections.observableArrayList(Status.values()));
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
     * Change scene to accountSettings scene
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void homeEditAccount(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a Package to the Track And Trace list
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void addPackageScreen(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove a Package from the Track And Trace list
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void removePackage(ActionEvent actionEvent) {
        int index = packagesListView.getSelectionModel().getSelectedIndex();

        if (index != -1) {
            Package toBeRemoved = currentPackagesOfAccount.get(index);

            boolean succeeded = packageManager.deletePackage(toBeRemoved.getID());
            if(!succeeded) {
                AlertDialog.createAlert(Alert.AlertType.ERROR, "Package not removed",
                        "Package could not be removed",
                        "Your Package could, unfortunately, not be removed due to technical problems");

            } else {
                packagesListView.getItems().remove(index);
            }
        } else {
            AlertDialog.createAlert(Alert.AlertType.INFORMATION, "Please select Package", "No Package is selected",
                    "You have to select a Package before you can remove it");
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
            observablePackageList = FXCollections.observableList(packageDescriptions);
            packagesListView.setItems(observablePackageList);
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

        // Check for Employee status and adjust editable items accordingly
        if (Globals.loggedInAccount.isEmployee()) {
            packageDetailsItems.forEach(item -> item.setEditable(true));
            packageDetailsShippingType.setEditable(true);
            packageDetailsStatus.setEditable(true);
            packageDetailsExpectedDeliveryDate.setEditable(true);

            // Set value back when changed to editable
            packageDetailsShippingType.setValue(Globals.packageToBeViewed.getShippingType());
            packageDetailsStatus.setValue(Globals.packageToBeViewed.getStatus());
        } else {
            packageDetailsName.setEditable(true);
        }
    }

    /**
     * Save changes to the Package
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void saveEditedPackage(ActionEvent actionEvent) {
        String packageName = packageDetailsName.getText();
        String packageFromCompany = packageDetailsFromCompany.getText();
        ShippingType packageShippingType = packageDetailsShippingType.getValue();
        Status packageStatus = packageDetailsStatus.getValue();
        String packageSize = packageDetailsSize.getText();
        int packageWeight = Integer.valueOf(packageDetailsWeight.getText());
        String packageContents = packageDetailsContents.getText();
        LocalDate packageExpectedDeliveryDate = packageDetailsExpectedDeliveryDate.getValue();

        Package updatedPackage = new Package(Globals.packageToBeViewed.getID(), packageName, packageFromCompany,
                packageShippingType, packageStatus, packageSize, packageWeight,
                packageContents, packageExpectedDeliveryDate,
                Globals.packageToBeViewed.getLocationLat(), Globals.packageToBeViewed.getLocationLong());

        boolean succeeded = packageManager.updatePackage(updatedPackage);

        if(!succeeded){
            AlertDialog.createAlert(Alert.AlertType.ERROR, "Package not updated",
                    "Package could not be updated",
                    "Your Package could, unfortunately, not be updated");
        } else {
            AlertDialog.createAlert(Alert.AlertType.INFORMATION, "Package updated", "Package updated successfully",
                    "Your Package has been succesfully updated!");

            Globals.packageToBeViewed = updatedPackage;

            packageDetailsItems.forEach(item -> item.setEditable(false));
            packageDetailsShippingType.setEditable(false);
            packageDetailsStatus.setEditable(false);
            packageDetailsExpectedDeliveryDate.setEditable(false);

            // Set values back to updated viewed Package
            packageDetailsShippingType.setValue(Globals.packageToBeViewed.getShippingType());
            packageDetailsStatus.setValue(Globals.packageToBeViewed.getStatus());

            packageEditButton.setDisable(false);
            packageSaveButton.setDisable(true);
        }
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

    // ACCOUNTSETTINGS SCENE

    /**
     * Make fields editable
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void editAccount(ActionEvent actionEvent) {
        accountSettingsUsername.setEditable(true);
        accountSettingsPassword.setEditable(true);
        accountSettingsAddress.setEditable(true);
        accountSettingsEmailAddress.setEditable(true);

        accountSettingsEditButton.setDisable(true);
        accountSettingsSaveButton.setDisable(false);
    }

    /**
     * Save changes to the currently logged in Account
     * @param actionEvent ActionEvent corresponding to the button
     */
    public void saveAccount(ActionEvent actionEvent) {
        String userName = accountSettingsUsername.getText();
        String password = accountSettingsPassword.getText();
        boolean isEmployee = Boolean.parseBoolean(accountSettingsEmployeeStatus.getText());
        String address = accountSettingsAddress.getText();
        String emailAddress = accountSettingsEmailAddress.getText();

        Account account = new Account(Globals.loggedInAccount.getID(), userName, password, isEmployee, address, emailAddress);
        boolean succeeded = accountManager.updateAccount(account);

        if (!succeeded) AlertDialog.createAlert(Alert.AlertType.ERROR, "Error saving Account",
                "Account could not be saved",
                "Saving the edited items of your Account was NOT completed successfully");

        AlertDialog.createAlert(Alert.AlertType.INFORMATION, "Account updated", "Account updated successfully",
                "Your Account has been updated successfully with the provided values");
    }

    // ADDPACKAGE SCENE

    public void addPackage(ActionEvent actionEvent) {
        String name = addPackageName.getText();
        String fromCompany = addPackageFromCompany.getText();
        ShippingType shippingType = addPackageShippingType.getValue();
        Status status = addPackageStatus.getValue();
        String size = addPackageSize.getText();
        int weight = Integer.valueOf(addPackageWeight.getText());
        String contents = addPackageContents.getText();
        LocalDate expectedDeliveryDate = addPackageExpectedDeliveryDate.getValue();
        double latitude = Double.valueOf(addPackageLatitude.getText());
        double longitude = Double.valueOf(addPackageLongitude.getText());

        Package newPackage = new Package(Globals.loggedInAccount.getID(), name, fromCompany, shippingType, status, size, weight, contents,
                expectedDeliveryDate, latitude, longitude);

        boolean succeeded = packageManager.addPackage(newPackage);

        if(!succeeded) AlertDialog.createAlert(Alert.AlertType.ERROR, "Package not added", "Could not add Package",
                "Your Package could, unfortunately, not be added");

        AlertDialog.createAlert(Alert.AlertType.INFORMATION, "Package added", "Your Package has been added",
                "Your Package has been added to the list of Track and Trace packages");

        try {
            changeScene(addPackageHomeButton);
        } catch (IOException e){
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

        // Unsubscribe PackageManager to prevent multiple propertyChange calls
        // when leftover class exists after switching Scene
        packageManager.unsubscribeRemotePublisher();

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
        } else if (actionEventObject == accountSettingsHomeButton) {
            stage = (Stage) accountSettingsHomeButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Globals.HomeFileName));
        } else if (actionEventObject == addPackageHomeButton) {
            stage = (Stage) addPackageHomeButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Globals.HomeFileName));
        } else if (actionEventObject == homeEditAccountButton) {
            stage = (Stage) homeEditAccountButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Globals.AccountSettingsFileName));
        } else if (actionEventObject == homeAddPackageButton) {
            stage = (Stage) homeAddPackageButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(Globals.AddPackageFileName));
        } else {
            try {
                packageManager = new PackageManager(this);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return;
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateInPackageList(Package packageToBeUpdated) {
        // Update observable list of packages in listview
        Platform.runLater(() -> {
            for (int i = 0; i < observablePackageList.size(); i++) {
                if (observablePackageList.get(i).startsWith("ID : " + packageToBeUpdated.getID())) {
                    observablePackageList.set(i, packageToBeUpdated.toString());
                    packagesListView.setItems(observablePackageList);
                }
            }
        });
    }
}