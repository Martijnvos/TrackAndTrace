package database;

import classes.Package;
import interfaces.IPackageQueries;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PackageQueries implements IPackageQueries {

    /**
     * Get Package corresponding to given ID
     * @param packageID ID of Package wished to be received
     * @return Package instantiation corresponding to given ID
     */
    @Override
    public Package getPackage(int packageID) {
        throw new NotImplementedException();
    }

    /**
     * Add Package to database
     * @param packageInstantiation Package instantiation with correct values
     * @return true/false depending on success of saving in database
     */
    @Override
    public boolean addPackage(Package packageInstantiation) {
        throw new NotImplementedException();
    }

    /**
     * Update Package details in database according to given Package
     * @param packageInstantiation Package instantiation with updated values
     * @return true/false depending on success of updating in database
     */
    @Override
    public boolean updatePackage(Package packageInstantiation) {
        throw new NotImplementedException();
    }

    /**
     * Delete Package corresponding to given ID
     * @param packageID ID of Package to be removed
     * @return true/false depending on success of deletion from database
     */
    @Override
    public boolean deletePackage(int packageID) {
        throw new NotImplementedException();
    }
}
