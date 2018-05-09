package Services.User;

import Utilities.User;

import java.util.ArrayList;

public interface UserDBInterface {

    User selectUserByEmailAddressFromDB(String emailAddress);

    void updateUserInDB(User user);

    ArrayList<User> selectAllUsersFromDB();

    void deleteUserByIdFromDB(int userId);

    ArrayList<String> selectAllEmailAddressesFromDB();

    void insertNewUserInToDB(User user);
}
