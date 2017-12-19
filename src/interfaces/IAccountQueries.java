package interfaces;

import classes.Account;

public interface IAccountQueries {
    Account getAccount(int accountID);
    boolean addAccount(Account account);
    boolean updateAccount(Account account);
    boolean deleteAccount(int accountID);
}
