import classes.AccountManager;
import globals.Globals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountManagerTest {

    private AccountManager accountManager;

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
        accountManager = new AccountManager();

        assertNotEquals(accountManager.getAccuntQueriesStub(), null);
    }
}
