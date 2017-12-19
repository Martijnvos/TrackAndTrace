package classes;

import interfaces.IPackageManager;
import java.util.ArrayList;
import java.util.Arrays;

public class PackageManager implements IPackageManager {

    private ArrayList<Package> packages;

    public PackageManager() {
        packages = new ArrayList<>();
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
     * Add Package to the Packagelist
     * @param packageInstance Package which has to be added to the Packagelist
     */
    @Override
    public void addPackage(Package packageInstance) {
        packages.add(packageInstance);
    }

    /**
     * Replace if the ID of the Package corresponds to the ID of the updated Package
     * If that's not the case, leave it
     * @param newPackageInstance Updated Package with valid ID
     */
    @Override
    public void updatePackage(Package newPackageInstance) {
        packages.replaceAll(x -> x.getID() == newPackageInstance.getID() ? newPackageInstance : x);
    }

    /**
     * Remove Package that corresponds with given ID
     * @param ID ID of Package to be removed
     */
    @Override
    public void deletePackage(int ID) {
        packages.removeIf(x -> x.getID() == ID);
    }
}
