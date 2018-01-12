package classes;

import globals.Globals;
import interfaces.IAccountManager;
import interfaces.IAccountQueries;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AccountManager implements IAccountManager {

    private IAccountQueries stub;

    public AccountManager() {
        try {
            stub = (IAccountQueries) Globals.registry.lookup(Globals.accountQueriesBindingName);
        } catch(RemoteException | NotBoundException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Register new Account with corresponding values
     * @param username username corresponding to new Account, needs to be unique
     * @param password password corresponding to new Account
     * @param isEmployee employee status corresponding to Account
     * @param address address corresponding to new Account
     * @param emailAddress emailAddress of new Account
     */
    @Override
    public boolean registerAccount(String username, String password, boolean isEmployee, String address, String emailAddress) {
        try {
            boolean succeeded = stub.addAccount(new Account(username, password, isEmployee, address, emailAddress));

            if (!succeeded) return false;

            Globals.loggedInAccount = new Account(username, password, isEmployee, address, emailAddress);
            return true;
        } catch(RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get the Account corresponding to the given ID
     * @param ID ID of requested account
     * @return Account instantiation belonging to given ID
     */
    @Override
    public Account getAccount(int ID) {
        try {
            return stub.getAccount(ID);
        } catch(RemoteException e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Replace if the ID of the Account corresponds to the ID of the updated Account
     * If that's not the case, leave it
     * @param newAccount Updated Account with valid ID
     */
    @Override
    public boolean updateAccount(Account newAccount) {
        try {
            boolean succeeded = stub.updateAccount(newAccount);

            if (!succeeded) return false;

            Globals.loggedInAccount = newAccount;
            return true;
        } catch(RemoteException e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove Account that corresponds with given ID
     * @param ID ID of Account to be removed
     */
    @Override
    public boolean deleteAccount(int ID) {
        try {
            boolean succeeded = stub.deleteAccount(ID);

            if (!succeeded) return false;

            Globals.loggedInAccount = null;
            return true;
        } catch(RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Log the given Account in
     * @param username username of the Acocunt to be signed in
     * @param password password of the Account to be signed in
     * @return true/false depending on successful login
     */
    @Override
    public boolean logIn(String username, String password) {
        try {
            Account response = stub.logIn(username, password);

            Globals.loggedInAccount = response;

            return response != null;
        } catch (RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Log the given Account out
     * @param account the Account to be logged out
     * @return true/false depending on successful logout
     */
    @Override
    public boolean logOut(Account account) {
        try {
            boolean succeeded = stub.logOut(account);

            if (!succeeded) return false;

            Globals.loggedInAccount = null;
            return true;
        } catch (RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return false;
        } catch (NotImplementedException e) {
            //TODO remove when logOut on server is implemented  correctly
            e.printStackTrace();
            return false;
        }
    }
}
