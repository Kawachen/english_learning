package ServicesTests.AccessTokenTests;

import DBConnection.DBConnection;
import Datamodel.AccessToken;
import Services.AccessToken.AccessTokenService;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccessTokenServiceTest {

    private Connection connection = DBConnection.getInstance().getConnection();

    @Test
    public void testGetAccessTokensAndInsertThemIntoDB() {
        AccessTokenService accessTokenService = new AccessTokenService();
        ArrayList<String> accessTokens = accessTokenService.getAccessTokensAndInsertThemInToDB(1);
        assertEquals(1, accessTokens.size());
        deleteAllAccessTokensFromDB();
    }

    @Test
    public void testValidateAccessTokenTrue() {
        AccessTokenService accessTokenService = new AccessTokenService();
        ArrayList<String> accessTokens = accessTokenService.getAccessTokensAndInsertThemInToDB(1);
        assertTrue(accessTokenService.validateAccessToken(accessTokens.get(0)));
        deleteAllAccessTokensFromDB();
    }

    @Test
    public void testValidateAccessTokenFalse() {
        AccessTokenService accessTokenService = new AccessTokenService();
        ArrayList<String> accessTokens = accessTokenService.getAccessTokensAndInsertThemInToDB(1);
        assertFalse(accessTokenService.validateAccessToken("blub"));
        deleteAllAccessTokensFromDB();
    }

    @Test
    public void testDeleteLatestValidatedToken() {
        AccessTokenService accessTokenService = new AccessTokenService();
        ArrayList<String> accessTokens = accessTokenService.getAccessTokensAndInsertThemInToDB(1);
        accessTokenService.validateAccessToken(accessTokens.get(0));
        accessTokenService.deleteLatestValidatedToken();
        ArrayList<AccessToken> accessTokens1 = getAllAccessTokensFromDB();
        boolean insertedTokenIsInTokenList = false;
        for (AccessToken accessToken:accessTokens1) {
            if(accessToken.getAccessString().equals(accessTokens.get(0))) {
                insertedTokenIsInTokenList = true;
            }
        }
        assertFalse(insertedTokenIsInTokenList);
    }

    private void deleteAllAccessTokensFromDB() {
        try {
            PreparedStatement deleteAllAccessTokens = connection.prepareStatement("DELETE FROM 'accessTokens'");
            deleteAllAccessTokens.executeUpdate();
        } catch (SQLException e) {

        }
    }

    private ArrayList<AccessToken> getAllAccessTokensFromDB() {
        ArrayList<AccessToken> accessTokens = new ArrayList<>();
        try {
            PreparedStatement getAllAccessTokens = connection.prepareStatement("SELECT * FROM 'accessTokens'");
            ResultSet resultSet = getAllAccessTokens.executeQuery();
            while(resultSet.next()) {
                AccessToken accessToken = new AccessToken(resultSet.getInt("id"), resultSet.getString("accesstoken"));
                accessTokens.add(accessToken);
            }
        } catch (SQLException e) {

        }
        return accessTokens;
    }
}
