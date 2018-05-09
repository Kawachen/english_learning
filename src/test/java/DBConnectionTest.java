import static org.junit.Assert.*;
import DBConnection.DBConnection;
import org.junit.Test;

import java.sql.Connection;

public class DBConnectionTest {

    @Test
    public void testDBConnectionGetInstance() {
        DBConnection connectionPool = DBConnection.getInstance();
        assertEquals(true, connectionPool instanceof DBConnection);
    }

    @Test
    public void testDBConnectionGetConnection() {
        DBConnection connectionPool = DBConnection.getInstance();
        assertEquals(true, connectionPool.getConnection() instanceof Connection);
    }
}
