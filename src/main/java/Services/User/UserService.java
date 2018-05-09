package Services.User;

import Utilities.User;

import java.util.ArrayList;

public class UserService {

    private UserDBInterface userDBService = new UserDBService();

    public User getUserByEmailAddress(String emailAddress) {
        return userDBService.selectUserByEmailAddressFromDB(emailAddress);
    }

    public void updateUser(User user) {
        userDBService.updateUserInDB(user);
    }

    public ArrayList<User> getAllUsers() {
        return userDBService.selectAllUsersFromDB();
    }

    public void deleteUserById(int userId) {
        userDBService.deleteUserByIdFromDB(userId);
    }

    public boolean doesEmailExistAlready(String emailAddress) {
        for(String addressFromDB:userDBService.selectAllEmailAddressesFromDB()) {
            if(addressFromDB.equalsIgnoreCase(emailAddress)) {
                return true;
            }
        }
        return false;
    }

    public void addNewUser(User user) {
        userDBService.insertNewUserInToDB(user);
    }
}
