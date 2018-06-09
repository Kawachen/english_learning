package Services.AccessToken;

import Datamodel.AccessToken;
import utils.HashGeneratorUtils;

import java.util.ArrayList;

public class AccessTokenService {

    private AccessTokenDBInterface accessTokenDBService = new AccessTokenDBService();
    private int latestValidatedTokenId;

    public ArrayList<String> getAccessTokensAndInsertThemInToDB(int count) {
        ArrayList<AccessToken> accessTokens = this.createAccessTokens(count);
        this.accessTokenDBService.insertNewAccessTokensInToDB(accessTokens);
        final ArrayList<String> returnAccessTokens = new ArrayList<String>();
        accessTokens.forEach(accessToken -> returnAccessTokens.add(accessToken.getAccessString()));
        return returnAccessTokens;
    }

    public boolean validateAccessToken(String accessToken) {
        ArrayList<AccessToken> accessTokens = this.accessTokenDBService.getAllAccessTokensFromDB();
        for(AccessToken oldAccessToken:accessTokens) {
            if(accessToken.equals(oldAccessToken.getAccessString())) {
                this.latestValidatedTokenId = oldAccessToken.getId();
                return true;
            }
        }
        return false;
    }

    public void deleteLatestValidatedToken() {
        this.accessTokenDBService.deleteAccessTokenByIdFromDB(latestValidatedTokenId);
    }

    private ArrayList<AccessToken> createAccessTokens(int count) {
        ArrayList<AccessToken> accessTokens = new ArrayList<AccessToken>();
        for(int i=0;i<count;i++) {
            int randomNumber = (int) (Math.random()*999999)+100000;
            String randomString = Integer.toString(randomNumber);
            String hashedRandomString = HashGeneratorUtils.generateSHA256(randomString);
            hashedRandomString = hashedRandomString.substring(0,10);
            AccessToken accessToken = new AccessToken(0, hashedRandomString);
            accessTokens.add(accessToken);
        }
        return accessTokens;
    }
}
