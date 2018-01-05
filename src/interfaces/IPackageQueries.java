package interfaces;

import classes.Package;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IPackageQueries extends Remote {
    Package getPackage(int packageID) throws RemoteException;
    ArrayList<Package> getAllPackagesOfAccount(int accountID) throws RemoteException;
    boolean addPackage(Package packageInstantiation, int accountID) throws RemoteException;
    boolean updatePackage(Package packageInstantiation, int accountID) throws RemoteException;
    boolean deletePackage(int packageID) throws RemoteException;
}
