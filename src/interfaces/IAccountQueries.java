package interfaces;

import classes.Account;
import com.sun.org.apache.xml.internal.utils.SerializableLocatorImpl;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IAccountQueries extends Remote {
    Account getAccount(int accountID) throws RemoteException;
    boolean addAccount(Account account) throws RemoteException;
    boolean updateAccount(Account account) throws RemoteException;
    boolean deleteAccount(int accountID) throws RemoteException;

    Account logIn(String username, String password) throws RemoteException;
    boolean logOut(Account account) throws RemoteException;
}
