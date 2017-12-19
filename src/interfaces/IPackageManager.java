package interfaces;

import classes.Package;

public interface IPackageManager {
    Package getPackage(int ID);
    void addPackage(Package packageInstance);
    void updatePackage(Package newPackageInstance);
    void deletePackage(int ID);
}
