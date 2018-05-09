import static org.junit.Assert.*;

import DBConnection.DBConnection;
import Services.AccessToken.AccessTokenDBService;
import Services.AccessToken.AccessTokenService;
import Utilities.AccessToken;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccessTokenServiceTest {

    private Connection connection;
    private AccessTokenService accessTokenService = new AccessTokenService();
    private AccessTokenDBService accessTokenDBService = new AccessTokenDBService();

    public AccessTokenServiceTest() {
        DBConnection connectionPool = DBConnection.getInstance();
        this.connection = connectionPool.getConnection();
    }

    @Test
    public void testGetAccessTokens() {
        this.accessTokenService.getAccessTokensAndInsertThemInToDB(1);
        ArrayList<AccessToken> accessTokens = accessTokenDBService.getAllAccessTokensFromDB();
        assertEquals(1, accessTokens.size());

        try {
            PreparedStatement preparedStatement1 = this.connection.prepareStatement("DELETE FROM accesstokens");
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Test
    public void testValidateAccessToken() {
        ArrayList<String> accessTokens = this.accessTokenService.getAccessTokensAndInsertThemInToDB(1);
        assertEquals(true, this.accessTokenService.validateAccessToken(accessTokens.get(0)));

        try {
            PreparedStatement preparedStatement1 = this.connection.prepareStatement("DELETE FROM accesstokens");
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Test
    public void testDeleteLatestValidatedAccessToken() {
        ArrayList<String> accessToken = this.accessTokenService.getAccessTokensAndInsertThemInToDB(1);
        this.accessTokenService.validateAccessToken(accessToken.get(0));
        this.accessTokenService.deleteLatestValidatedToken();

        ArrayList<AccessToken> accessTokens = this.accessTokenDBService.getAllAccessTokensFromDB();
        assertEquals(0, accessTokens.size());
    }
}
