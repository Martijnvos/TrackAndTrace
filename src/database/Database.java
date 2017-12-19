package database;

public class Database {

    private Connection connection;

    private AccountQueries accountQueries;
    private PackageQueries packageQueries;

    public Database() {
        connection = new Connection();

        accountQueries = new AccountQueries();
        packageQueries = new PackageQueries();
    }
}
