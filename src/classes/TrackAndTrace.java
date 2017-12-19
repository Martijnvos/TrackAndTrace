package classes;

public class TrackAndTrace {
    private AccountManager accountManager;
    private PackageManager packageManager;

    public TrackAndTrace(){
        accountManager = new AccountManager();
        packageManager = new PackageManager();
    }
}