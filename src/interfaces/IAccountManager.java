package interfaces;

import classes.Account;

public interface IAccountManager {
    void registerAccount(String username, String password, boolean isEmployee, String address);
    Account getAccount(int ID);
    void updateAccount(Account newAccount);
    void deleteAccount(int ID);

    boolean logIn(String username, String password);
    boolean logOut(Account account);
}
