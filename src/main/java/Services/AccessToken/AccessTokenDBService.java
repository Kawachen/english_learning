package Services.AccessToken;

import DBConnection.DBConnection;
import Utilities.AccessToken;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccessTokenDBService implements AccessTokenDBInterface {

    private DBConnection connectionPool = DBConnection.getInstance();

    public void insertNewAccessTokensInToDB(ArrayList<AccessToken> accessTokens) {
        try {
            this.insertNewAccessTokens(accessTokens);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: insert new accessTokens into database failed!");
        }
    }

    public ArrayList<AccessToken> getAllAccessTokensFromDB() {
        try {
            return this.getAllAccessTokens();
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: get all accessTokens from database failed!");
        }
        return new ArrayList<AccessToken>();
    }

    public void deleteAccessTokenByIdFromDB(int id) {
        try {
            this.deleteAccessTokenById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: delete accessToken by id from database failed!");
        }
    }

    private void insertNewAccessTokens(ArrayList<AccessToken> accessTokens) throws SQLException {
        for(AccessToken accessToken: accessTokens) {
            PreparedStatement preparedStatement = this.connectionPool.getConnection().prepareStatement("INSERT INTO accesstokens (id, accessToken) VALUES (DEFAULT, ?)");
            preparedStatement.setString(1, accessToken.getAccessString());
            preparedStatement.executeUpdate();
        }
    }

    private ArrayList<AccessToken> getAllAccessTokens() throws SQLException {
        ArrayList<AccessToken> accessTokens = new ArrayList<AccessToken>();
        PreparedStatement preparedStatement = this.connectionPool.getConnection().prepareStatement("SELECT * FROM accesstokens");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            AccessToken accessToken = new AccessToken(resultSet.getInt("id"), resultSet.getString("accessToken"));
            accessTokens.add(accessToken);
        }
        return accessTokens;
    }

    private void deleteAccessTokenById(int id) throws SQLException {
        PreparedStatement preparedStatement = this.connectionPool.getConnection().prepareStatement("DELETE FROM accesstokens WHERE id= ? ;");
        preparedStatement.setString(1, Integer.toString(id));
        preparedStatement.executeUpdate();
    }
}
