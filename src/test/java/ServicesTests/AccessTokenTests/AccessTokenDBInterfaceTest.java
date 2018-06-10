package ServicesTests.AccessTokenTests;

import Datamodel.AccessToken;
import Services.AccessToken.AccessTokenDBInterface;
import Services.AccessToken.AccessTokenDBService;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccessTokenDBInterfaceTest {

    private AccessToken accessToken = new AccessToken(42, "blub");
    private ArrayList<AccessToken> accessTokens = new ArrayList<AccessToken>() {
        {
            add(accessToken);
        }
    };

    @Test
    public void testEverything() {
        AccessTokenDBInterface accessTokenDBService = new AccessTokenDBService();

        accessTokenDBService.insertNewAccessTokensInToDB(accessTokens);
        ArrayList<AccessToken> accessTokens = accessTokenDBService.getAllAccessTokensFromDB();
        boolean insertedTokenIsInTokenList = false;
        int id = 0;
        for (AccessToken accessToken:accessTokens) {
            if(accessToken.getAccessString().equals(this.accessToken.getAccessString())) {
                id = accessToken.getId();
                insertedTokenIsInTokenList = true;
            }
        }
        assertTrue(insertedTokenIsInTokenList);
        accessTokenDBService.deleteAccessTokenByIdFromDB(id);
        ArrayList<AccessToken> accessTokensNew = accessTokenDBService.getAllAccessTokensFromDB();
        insertedTokenIsInTokenList = false;
        for (AccessToken accessToken:accessTokensNew) {
            if(accessToken.getAccessString().equals(this.accessToken.getAccessString())) {
                insertedTokenIsInTokenList = true;
            }
        }
        assertFalse(insertedTokenIsInTokenList);
    }
}
