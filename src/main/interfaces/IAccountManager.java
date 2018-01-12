package interfaces;

import classes.Account;

public interface IAccountManager {
    boolean registerAccount(String username, String password, boolean isEmployee, String address, String emailAddress);
    Account getAccount(int ID);
    boolean updateAccount(Account newAccount);
    boolean deleteAccount(int ID);

    boolean logIn(String username, String password);
    boolean logOut(Account account);
}
