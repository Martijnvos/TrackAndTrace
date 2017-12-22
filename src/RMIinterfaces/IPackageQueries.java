package RMIinterfaces;

import classes.Package;

import java.util.ArrayList;

public interface IPackageQueries {
    Package getPackage(int packageID);
    ArrayList<Package> getAllPackagesOfAccount(int accountID);
    boolean addPackage(Package packageInstantiation);
    boolean updatePackage(Package packageInstantiation);
    boolean deletePackage(int packageID);
}
