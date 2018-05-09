package Services.AccessToken;

import Utilities.AccessToken;

import java.util.ArrayList;

public interface AccessTokenDBInterface {


    void insertNewAccessTokensInToDB(ArrayList<AccessToken> accessTokens);

    ArrayList<AccessToken> getAllAccessTokensFromDB();

    void deleteAccessTokenByIdFromDB(int id);
}
