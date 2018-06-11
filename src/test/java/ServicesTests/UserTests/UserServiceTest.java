package ServicesTests.UserTests;

import Datamodel.User;
import Enums.Role;
import Services.User.UserService;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {

    private String testFirstname = "Rudolf";
    private String testLastname = "Peterson";
    private String testEmail = "rudolf@gmail.com";
    private String testPassword = "passwort";
    private int testSalt = 42;
    private Role testRole = Role.STUDENT;
    private User user = new User(testFirstname, testLastname, testEmail, testPassword, testSalt);

    public UserServiceTest() {
        user.setRole(testRole);
    }

    @Test
    public void testAddNewUserAndGetUserByUserEmailAddressAndDeleteUserByUserId() {
        UserService userService = new UserService();
        userService.addNewUser(user);
        User localUser = userService.getUserByEmailAddress(user.getEmailAddress());
        compareUserObjectWithInitData(localUser);
        userService.deleteUserById(localUser.getId());
        ArrayList<User> users = userService.getAllUsers();
        boolean doesUserExist = false;
        for (User loopUser:users) {
            if(loopUser.getEmailAddress().equals(user.getEmailAddress())) {
                doesUserExist = true;
            }
        }
        assertFalse(doesUserExist);
    }

    @Test
    public void testUpdateUser() {
        UserService userService = new UserService();
        userService.addNewUser(user);
        User localUser = userService.getUserByEmailAddress(user.getEmailAddress());
        localUser.setPassword("blub");
        localUser.setFirstName("heinz");
        localUser.setLastName("peterson");
        userService.updateUser(localUser);
        User updatedUser = userService.getUserByEmailAddress(user.getEmailAddress());
        assertEquals(localUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(localUser.getLastName(), updatedUser.getLastName());
        assertEquals(localUser.getPassword(), updatedUser.getPassword());
        assertEquals(localUser.getSalt(), updatedUser.getSalt());
        userService.deleteUserById(localUser.getId());
    }

    @Test
    public void testGetAllUsers() {
        UserService userService = new UserService();
        userService.addNewUser(user);
        ArrayList<User> users = userService.getAllUsers();
        User localUser = null;
        for (User loopUser:users) {
            if(loopUser.getEmailAddress().equals(user.getEmailAddress())) {
                localUser = loopUser;
            }
        }
        compareUserObjectWithInitData(localUser);
        userService.deleteUserById(localUser.getId());
    }

    @Test
    public void testDoesEmailExistAlreadyTrue() {
        UserService userService = new UserService();
        userService.addNewUser(user);
        assertTrue(userService.doesEmailExistAlready(user.getEmailAddress()));
        userService.deleteUserById(userService.getUserByEmailAddress(user.getEmailAddress()).getId());
    }

    @Test
    public void testDoesEmailExistAlreadyFalse() {
        UserService userService = new UserService();
        assertFalse(userService.doesEmailExistAlready(user.getEmailAddress()));
    }

    private void compareUserObjectWithInitData(User user) {
        assertEquals(testFirstname, user.getFirstName());
        assertEquals(testLastname, user.getLastName());
        assertEquals(testEmail, user.getEmailAddress());
        assertEquals(testPassword, user.getPassword());
        assertEquals(testSalt, user.getSalt());
        assertEquals(testRole, user.getRole());
    }
}
