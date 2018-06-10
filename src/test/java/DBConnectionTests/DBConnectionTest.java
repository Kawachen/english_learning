package DBConnectionTests;

import DBConnection.DBConnection;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DBConnectionTest {

    @Test
    public void testGetInstanceAndGetConnection() {
        DBConnection dbConnection = DBConnection.getInstance();
        assertEquals(DBConnection.class, dbConnection.getClass());
        Connection connection = dbConnection.getConnection();
        assertNotNull(connection);
    }
}
