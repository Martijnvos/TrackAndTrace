package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPackageUpdates extends Remote {
    void setPackageLocationUpdates() throws RemoteException;
    void unSetPackageLocationUpdates() throws RemoteException;
}
