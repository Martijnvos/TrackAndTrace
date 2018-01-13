import classes.PackageManager;
import controllers.TrackAndTrace;
import convenienceClasses.WrongConstructorException;
import globals.Globals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PackageManagerTest {

    private PackageManager packageManager;

    @BeforeAll
    public void initialize() {
        try {
            Globals.registry = LocateRegistry.getRegistry("127.0.0.1",1099);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void constructorTest() {
        try {
            packageManager = new PackageManager(new TrackAndTrace(true));

            assertNotEquals(packageManager.getStub(), null);
            assertNotEquals(packageManager.getPackageUpdateStub(), null);
            assertNotEquals(packageManager.getRemotePublisher(), null);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("RMI problems made this test fail, check if the server is online");
        } catch (WrongConstructorException e) {
            e.printStackTrace();
            fail("Wrong use of constructor");
        }
    }

}
