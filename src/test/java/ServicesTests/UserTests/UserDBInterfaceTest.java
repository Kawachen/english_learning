package ServicesTests.UserTests;

import Datamodel.User;
import Enums.Role;
import Services.User.UserDBInterface;
import Services.User.UserDBService;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserDBInterfaceTest {

    private String testFirstname = "Rudolf";
    private String testLastname = "Peterson";
    private String testEmail = "rudolf@gmail.com";
    private String testPassword = "passwort";
    private int testSalt = 42;
    private Role testRole = Role.STUDENT;
    private User user = new User(testFirstname, testLastname, testEmail, testPassword, testSalt);

    public UserDBInterfaceTest() {
        user.setRole(testRole);
    }

    @Test
    public void testInsertNewUserAndSelectUserFromDB() {
        UserDBInterface userDBService = new UserDBService();
        userDBService.insertNewUserInToDB(user);
        User localUser = userDBService.selectUserByEmailAddressFromDB(user.getEmailAddress());
        assertNotNull(localUser);
        assertEquals(testFirstname, localUser.getFirstName());
        assertEquals(testLastname, localUser.getLastName());
        assertEquals(testEmail, localUser.getEmailAddress());
        assertEquals(testPassword, localUser.getPassword());
        assertEquals(testSalt, localUser.getSalt());
        assertEquals(testRole, localUser.getRole());
        userDBService.deleteUserByIdFromDB(localUser.getId());
    }

    @Test
    public void testSelectAllUsersFromDB() {
        UserDBInterface userDBService = new UserDBService();
        userDBService.insertNewUserInToDB(user);
        ArrayList<User> users = userDBService.selectAllUsersFromDB();
        User localUser = null;
        for (User loopUser:users) {
            if(loopUser.getEmailAddress().equals(user.getEmailAddress())) {
                localUser = loopUser;
            }
        }
        assertEquals(testFirstname, localUser.getFirstName());
        assertEquals(testLastname, localUser.getLastName());
        assertEquals(testEmail, localUser.getEmailAddress());
        assertEquals(testPassword, localUser.getPassword());
        assertEquals(testSalt, localUser.getSalt());
        assertEquals(testRole, localUser.getRole());
        userDBService.deleteUserByIdFromDB(localUser.getId());
    }

    @Test
    public void testDeleteUserByUserId() {
        UserDBInterface userDBService = new UserDBService();
        userDBService.insertNewUserInToDB(user);
        User localUser = userDBService.selectUserByEmailAddressFromDB(user.getEmailAddress());
        userDBService.deleteUserByIdFromDB(localUser.getId());
        ArrayList<User> users = userDBService.selectAllUsersFromDB();
        boolean doesUserExists = false;
        for (User loopUser:users) {
            if(loopUser.getEmailAddress().equals(user.getEmailAddress())) {
                doesUserExists = true;
            }
        }
        assertFalse(doesUserExists);
    }

    @Test
    public void testUpdateUser() {
        UserDBInterface userDBService = new UserDBService();
        userDBService.insertNewUserInToDB(user);
        User localUser = userDBService.selectUserByEmailAddressFromDB(user.getEmailAddress());
        localUser.setPassword("test123");
        userDBService.updateUserInDB(localUser);
        User updatedUser = userDBService.selectUserByEmailAddressFromDB(user.getEmailAddress());
        assertEquals(localUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(localUser.getLastName(), updatedUser.getLastName());
        assertEquals(localUser.getPassword(), updatedUser.getPassword());
        assertEquals(localUser.getSalt(), updatedUser.getSalt());
        userDBService.deleteUserByIdFromDB(localUser.getId());
    }

    @Test
    public void testSelectAllEmailAddresses() {
        UserDBInterface userDBService = new UserDBService();
        userDBService.insertNewUserInToDB(user);
        User localUser = userDBService.selectUserByEmailAddressFromDB(user.getEmailAddress());
        ArrayList<String> emails = userDBService.selectAllEmailAddressesFromDB();
        boolean doesEmailExists = false;
        for(String email:emails) {
            if(email.equals(user.getEmailAddress())) {
                doesEmailExists = true;
            }
        }
        assertTrue(doesEmailExists);
        userDBService.deleteUserByIdFromDB(localUser.getId());
    }
}
