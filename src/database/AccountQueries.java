package database;

import classes.Account;
import interfaces.IAccountQueries;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AccountQueries implements IAccountQueries {

    /**
     * Get Account from database which corresponds to the given ID
     * @param accountID the ID of the Account requested
     * @return The Account that corresponds to the given accountID, otherwise null
     */
    @Override
    public Account getAccount(int accountID) {
        throw new NotImplementedException();
    }

    /**
     * Add the given Account to the database
     * @param account correct Account instantiation with corresponding information
     * @return true/false depending on success of saving in database
     */
    @Override
    public boolean addAccount(Account account) {
        throw new NotImplementedException();
    }

    /**
     * Update the given Account in the database
     * @param account correct Account instantiation with updated information
     * @return true/false depending on success of update in database
     */
    @Override
    public boolean updateAccount(Account account) {
        throw new NotImplementedException();
    }

    /**
     * Delete given Account from database
      * @param accountID ID of Account that has to be deleted
     * @return true/false depending on success of deletion in database
     */
    @Override
    public boolean deleteAccount(int accountID) {
        throw new NotImplementedException();
    }
}
