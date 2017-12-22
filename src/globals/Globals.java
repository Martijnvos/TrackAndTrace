package globals;

import classes.Account;

import java.rmi.registry.Registry;

public class Globals {

    // Main
    public static final String ApplicationTitle = "Track&Trace";
    public static final int ApplicationSceneWidth = 300;
    public static final int ApplicationSceneHeight = 275;

    // Layout file locations
    public static final String LoginRegisterFileName = "/layouts/loginRegister.fxml";
    public static final String TrackAndTraceFileName = "/layouts/trackAndTrace.fxml";

    // Account
    public static Account loggedInAccount;

    // Registry
    public static Registry registry;

    // Registry binding names
    public static final String accountQueriesBindingName = "accountQueriesBinding";
    public static final String packageQueriesBindingName = "packageQueriesBinding";
}