package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPackageUpdates extends Remote {
    void setPackageLocationUpdates(int accountID) throws RemoteException;
    void unSetPackageLocationUpdates() throws RemoteException;
}
