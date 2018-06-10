package DBConnectionTests;

import DBConnection.DBInterface;
import DBConnection.SQLConnection;
import org.junit.Test;

import java.net.ConnectException;
import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DBInterfaceTest {

    @Test
    public void testCreateSQLConnectionObject() {
        DBInterface sqlConnection = new SQLConnection();
        assertEquals(SQLConnection.class, sqlConnection.getClass());
    }

    @Test
    public void testInitializeAndGetConnection() {
        DBInterface sqlConnection = new SQLConnection();
        sqlConnection.initializeConnections();
        Connection connection = sqlConnection.getConnection();
        assertNotNull(connection);
    }
}
