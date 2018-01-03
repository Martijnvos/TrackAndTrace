package interfaces;

import classes.Package;

import java.util.ArrayList;

public interface IPackageManager {
    Package getPackage(int ID);
    ArrayList<Package> getAllPackagesOfAccount(int accountID);
    boolean addPackage(Package packageInstance);
    boolean updatePackage(Package newPackageInstance);
    boolean deletePackage(int ID);
}
