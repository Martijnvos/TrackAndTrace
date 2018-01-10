package globals;

import classes.Account;
import classes.Package;

import java.rmi.registry.Registry;

public class Globals {

    // Main
    public static final String ApplicationTitle = "Track&Trace";
    public static final int ApplicationSceneWidth = 500;
    public static final int ApplicationSceneHeight = 500;

    // Layout file locations
    public static final String LoginRegisterFileName = "/layouts/loginRegister.fxml";
    public static final String HomeFileName = "/layouts/home.fxml";
    public static final String PackageDetailsFileName = "/layouts/packageDetails.fxml";
    public static final String AccountSettingsFileName = "/layouts/accountSettings.fxml";
    public static final String AddPackageFileName = "/layouts/addPackage.fxml";

    // Account
    public static Account loggedInAccount;

    // Registry
    public static Registry registry;
    public static final String remotePublisherPackageChangesString = "packageChanges";

    // Registry binding names
    public static final String accountQueriesBindingName = "accountQueriesBinding";
    public static final String packageQueriesBindingName = "packageQueriesBinding";
    public static final String remotePublisherPackageBindingName = "packageRemotePublisherBinding";

    // GUI helper classes
    public static Package packageToBeViewed;
}