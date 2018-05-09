package Services.User;

import DBConnection.DBConnection;
import Exceptions.StringDidNotMatchOnRoleException;
import Utilities.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDBService implements UserDBInterface {

    private Connection dBConnection;

    public UserDBService() {
        DBConnection connectionPool = DBConnection.getInstance();
        dBConnection = connectionPool.getConnection();
    }

    public User selectUserByEmailAddressFromDB(String emailAddress) {
        try {
            return selectUserByEmailAddress(emailAddress);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: select user by email address from DB failed!");
        }
        return null;
    }

    public void updateUserInDB(User user) {
        try {
            updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: update user in DB failed!");
        }
    }

    public ArrayList<User> selectAllUsersFromDB() {
        ArrayList<User> users = new ArrayList<>();
        try {
            users = selectAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: select all users from DB failed!");
        }
        return users;
    }

    public void deleteUserByIdFromDB(int userId) {
        try {
            deleteUserById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: delete user by id from DB failed!");
        }
    }

    public ArrayList<String> selectAllEmailAddressesFromDB() {
        ArrayList<String> emailAddresses = new ArrayList<>();
        try {
            emailAddresses = selectAllEmailAddresses();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: select all email addresses from DB failed!");
        }
        return emailAddresses;
    }

    public void insertNewUserInToDB(User user) {
        try {
            insertNewUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: insert new user into DB failed!");
        }
    }

    private User selectUserByEmailAddress(String emailAddress) throws SQLException {
        User user = null;
        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT * FROM user WHERE email='"+emailAddress+"';");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            user = new User(resultSet.getInt("id"), resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getInt("salt"));
            user = setRoleForUser(resultSet.getString("role"), user);
        }
        return user;
    }

    private User setRoleForUser(String role, User user) {
        try {
            user.setRoleWithString(role);
        } catch (StringDidNotMatchOnRoleException e) {
            e.getMessage();
            return null;
        }
        return user;
    }

    private void updateUser(User user) throws SQLException{
        PreparedStatement preparedStatement = dBConnection.prepareStatement("UPDATE user SET firstname = ?, lastname = ?, email = ?, password = ?, salt = ?, role = ? WHERE id = ?;");
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmailAddress());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, Integer.toString(user.getSalt()));
        preparedStatement.setString(6, user.getRole().toString());
        preparedStatement.setString(7, Integer.toString(user.getId()));
        preparedStatement.executeUpdate();
    }

    private ArrayList<User> selectAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT * FROM user;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            users.add(new User(resultSet.getInt("id"), resultSet.getString("firstname"), resultSet.getString("lastname"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getInt("salt")));
        }
        return users;
    }

    private void deleteUserById(int userId) throws SQLException {
        PreparedStatement preparedStatement = dBConnection.prepareStatement("DELETE FROM user WHERE id = ?;");
        preparedStatement.setString(1, Integer.toString(userId));
        preparedStatement.executeUpdate();
    }

    private ArrayList<String> selectAllEmailAddresses() throws SQLException {
        ArrayList<String> emailAddresses = new ArrayList<>();
        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT email FROM user");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
           emailAddresses.add(resultSet.getString("email"));
        }
        return emailAddresses;
    }

    private void insertNewUser(User user) throws SQLException {
        PreparedStatement preparedStatement = dBConnection.prepareStatement("INSERT INTO user (id, email, password, firstname, lastname, role, salt) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, user.getEmailAddress());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getLastName());
        preparedStatement.setString(5, user.getRole().toString());
        preparedStatement.setString(6, Integer.toString(user.getSalt()));
        preparedStatement.executeUpdate();
    }
}
