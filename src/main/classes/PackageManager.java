package classes;

import controllers.TrackAndTrace;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import globals.Globals;
import interfaces.IPackageManager;
import interfaces.IPackageQueries;
import interfaces.IPackageUpdates;

import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class PackageManager extends UnicastRemoteObject implements IPackageManager, IPackageUpdates, IRemotePropertyListener {

    private TrackAndTrace trackAndTrace;

    private ArrayList<Package> packages;
    private IPackageQueries stub;
    private IPackageUpdates packageUpdateStub;
    private IRemotePublisherForListener remotePublisher;

    public IRemotePublisherForListener getRemotePublisher() {
        return remotePublisher;
    }

    public PackageManager(TrackAndTrace trackAndTrace) throws RemoteException {
        super();
        packages = new ArrayList<>();
        this.trackAndTrace = trackAndTrace;

        try {
            stub = (IPackageQueries) Globals.registry.lookup(Globals.packageQueriesBindingName);
            packageUpdateStub = (IPackageUpdates) Globals.registry.lookup(Globals.packageUpdateBindingName);
            remotePublisher = (IRemotePublisherForListener) Globals.registry.lookup(Globals.remotePublisherPackageBindingName);

            remotePublisher.subscribeRemoteListener(this, Globals.remotePublisherPackageChangesString);
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void unsubscribeRemotePublisher() {
        try {
            remotePublisher.unsubscribeRemoteListener(this, Globals.remotePublisherPackageChangesString);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setPackageLocationUpdates() {
        try {
            packageUpdateStub.setPackageLocationUpdates();
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unSetPackageLocationUpdates() {
        try {
            packageUpdateStub.unSetPackageLocationUpdates();
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Package which corresponds to given Package ID
     * @param ID ID of requested Package
     * @return Package with corresponding ID, otherwise null
     */
    @Override
    public Package getPackage(int ID) {
        return packages.stream()
                .filter(x -> x.getID() == ID)
                .findFirst()
                .orElse(null);
    }

    /**
     * Get all packages corresponding to the account of the given accountID
     * @param accountID accountID of the account wished to get all packages from
     * @return list of packages corresponding to given accountID
     */
    @Override
    public ArrayList<Package> getAllPackagesOfAccount(int accountID) {
        try {
            return stub.getAllPackagesOfAccount(accountID);
        } catch(RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add Package to the Packagelist
     * @param packageInstance Package which has to be added to the Packagelist
     */
    @Override
    public boolean addPackage(Package packageInstance) {
        try {
            boolean succeeded = stub.addPackage(packageInstance);

            if (!succeeded) return false;

            packages.add(packageInstance);
            return true;
        } catch (RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Replace if the ID of the Package corresponds to the ID of the updated Package
     * If that's not the case, leave it
     * @param newPackageInstance Updated Package with valid ID
     */
    @Override
    public boolean updatePackage(Package newPackageInstance) {
        try {
            boolean succeeded = stub.updatePackage(newPackageInstance);

            if (!succeeded) return false;

            packages.replaceAll(x -> x.getID() == newPackageInstance.getID() ? newPackageInstance : x);
            return true;
        } catch(RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove Package that corresponds with given ID
     * @param ID ID of Package to be removed
     */
    @Override
    public boolean deletePackage(int ID) {
        try {
            boolean succeeded = stub.deletePackage(ID);

            if(!succeeded) return false;

            packages.removeIf(x -> x.getID() == ID);
            return true;
        } catch(RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Changelistener of remote Package for location updates
     * @param propertyChangeEvent Event that gets fired by the remote publisher
     * @throws RemoteException when an exception occurred on the server-side or when transmitting the data
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) throws RemoteException {
        if (propertyChangeEvent.getNewValue() != null) {
            Package newPackage = (Package) propertyChangeEvent.getNewValue();
            trackAndTrace.updateInPackageList(newPackage);
        }
    }
}
