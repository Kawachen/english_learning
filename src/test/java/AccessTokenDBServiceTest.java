import static org.junit.Assert.*;

import DBConnection.DBConnection;
import Services.AccessToken.AccessTokenDBInterface;
import Services.AccessToken.AccessTokenDBService;
import Utilities.AccessToken;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccessTokenDBServiceTest {

    private Connection connection;
    private AccessTokenDBInterface accessTokenDBService = new AccessTokenDBService();
    private ArrayList<AccessToken> accessTokens = new ArrayList<AccessToken>();

    public AccessTokenDBServiceTest() {
        DBConnection connectionPool = DBConnection.getInstance();
        this.connection = connectionPool.getConnection();
        AccessToken accessToken1 = new AccessToken(0, "xXhaxxorXx");
        accessTokens.add(accessToken1);
        AccessToken accessToken2 = new AccessToken(0, "leetSpeek");
        accessTokens.add(accessToken2);
    }

    @Test
    public void testInsertAccessTokensInToDB() {
        this.accessTokenDBService.insertNewAccessTokensInToDB(accessTokens);

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM accesstokens");
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                assertEquals(resultSet.getString("accessToken"), accessTokens.get(i).getAccessString());
                i++;
            }

            PreparedStatement preparedStatement1 = this.connection.prepareStatement("DELETE FROM accesstokens");
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Exeption");
        }
    }

    @Test
    public void testGetAllAccessTokensFromDB() {
        try {
            for(AccessToken accessToken:accessTokens) {
                PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO accesstokens (id, accessToken) VALUES (DEFAULT, ?)");
                preparedStatement.setString(1, accessToken.getAccessString());
                preparedStatement.executeUpdate();
            }

            ArrayList<AccessToken> testAccessTokens = this.accessTokenDBService.getAllAccessTokensFromDB();

            int i = 0;
            for(AccessToken testAccessToken:testAccessTokens) {
                assertEquals(testAccessToken.getAccessString(), accessTokens.get(i).getAccessString());
                i++;
            }

            PreparedStatement preparedStatement1 = this.connection.prepareStatement("DELETE FROM accesstokens");
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Test
    public void testDeleteAccessTokenById() {
        this.accessTokenDBService.insertNewAccessTokensInToDB(accessTokens);
        ArrayList<Integer> accessIDs = new ArrayList<Integer>();
        for(AccessToken accessToken:this.accessTokenDBService.getAllAccessTokensFromDB()) {
            accessIDs.add(accessToken.getId());
        }
        for(int i:accessIDs) {
            this.accessTokenDBService.deleteAccessTokenByIdFromDB(i);
        }

        assertEquals(0, this.accessTokenDBService.getAllAccessTokensFromDB().size());
    }
}
