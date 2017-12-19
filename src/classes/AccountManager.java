package classes;

import interfaces.IAccountManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AccountManager implements IAccountManager {

    /**
     * Register new Account with corresponding values
     * @param username username corresponding to new Account, needs to be unique
     * @param password password corresponding to new Account
     * @param isEmployee employee status corresponding to Account
     * @param address address corresponding to new Account
     */
    @Override
    public void registerAccount(String username, String password, boolean isEmployee, String address) {
        throw new NotImplementedException();
    }

    /**
     * Get the Account corresponding to the given ID
     * @param ID ID of requested account
     * @return Account instantiation belonging to given ID
     */
    @Override
    public Account getAccount(int ID) {
        throw new NotImplementedException();
    }

    /**
     * Replace if the ID of the Account corresponds to the ID of the updated Account
     * If that's not the case, leave it
     * @param newAccount Updated Account with valid ID
     */
    @Override
    public void updateAccount(Account newAccount) {
        throw new NotImplementedException();
    }

    /**
     * Remove Account that corresponds with given ID
     * @param ID ID of Account to be removed
     */
    @Override
    public void deleteAccount(int ID) {
        throw new NotImplementedException();
    }

    /**
     * Log the given Account in
     * @param username username of the Acocunt to be signed in
     * @param password password of the Account to be signed in
     * @return true/false depending on successful login
     */
    @Override
    public boolean logIn(String username, String password) {
        throw new NotImplementedException();
    }

    /**
     * Log the given Account out
     * @param account the Account to be logged out
     * @return true/false depending on successful logout
     */
    @Override
    public boolean logOut(Account account) {
        throw new NotImplementedException();
    }
}
