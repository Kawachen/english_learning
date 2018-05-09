package DBConnection;

import java.sql.Connection;

public interface DBInterface {

    void initializeConnections();

    Connection getConnection();
}
