package DBConnection;

import java.sql.Connection;

public class DBConnection {
    private static DBConnection instance = null;
    private static DBInterface dbInterface = new SQLConnection();

    public static DBConnection getInstance() {
        if(instance == null) {
            synchronized (DBConnection.class) {
                if(instance == null) {
                    instance = new DBConnection();
                    dbInterface.initializeConnections();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return dbInterface.getConnection();
    }
}
