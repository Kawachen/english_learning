package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection implements DBInterface {

    private final static int MAX_CONNECTIONS = 8;
    private static Connection[] connections = new Connection[MAX_CONNECTIONS];
    private static int counter;

    public void initializeConnections() {
        machReudigenScheiss();
        for(int i = 0; i < MAX_CONNECTIONS; i++) {
            try {
                connections[i] = DriverManager.getConnection("jdbc:mysql://localhost:3306/englishtest?"
                        + "user=root&password=norenezar#123");
                System.out.println("successfull");
            } catch(SQLException e) {
                e.printStackTrace();
                System.err.println("ERROR: Connection to database failed!");
            }
        }
    }

    private static void machReudigenScheiss() {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Reudiger Scheiss did not work!");
        }
    }

    public Connection getConnection() {
        counter++;
        if(counter == Integer.MAX_VALUE) {
            counter = 0;
        }
        return connections[counter%MAX_CONNECTIONS];
    }
}
