package classes;

import globals.Globals;
import interfaces.IPackageManager;
import interfaces.IPackageQueries;
import sun.security.x509.IPAddressName;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class PackageManager implements IPackageManager {

    private ArrayList<Package> packages;
    private IPackageQueries stub;

    public PackageManager() {
        packages = new ArrayList<>();

        try {
            stub = (IPackageQueries) Globals.registry.lookup(Globals.packageQueriesBindingName);
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Client exception: " + e.toString());
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
            boolean succeeded = stub.updatePackage(newPackageInstance, Globals.loggedInAccount.getID());

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
}
