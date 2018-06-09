package DatamodelTests;

import Datamodel.User;
import Enums.Role;
import Exceptions.StringDidNotMatchOnRoleException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private int testId = 13;
    private String testFirstname = "Rudolf";
    private String testLastname = "Peterson";
    private String testEmail = "rudolf@gmail.com";
    private String testPassword = "passwort";
    private int testSalt = 42;

    private int testid2 = 14;
    private String testFirstname2 = "Peter";
    private String testLastname2 = "Hans";
    private String testEmail2 = "hans@web.de";
    private String testPassword2 = "username";
    private int testSalt2 = 43;

    @Test
    public void testCreateUserObjectWithoutUserId() {
        User user = new User(testFirstname, testLastname, testEmail, testPassword, testSalt);
        assertEquals(testFirstname, user.getFirstName());
        assertEquals(testLastname, user.getLastName());
        assertEquals(testEmail, user.getEmailAddress());
        assertEquals(testPassword, user.getPassword());
        assertEquals(testSalt, user.getSalt());
    }

    @Test
    public void testCreateUserObjectWithId() {
        User user = new User(testId, testFirstname, testLastname, testEmail, testPassword, testSalt);
        assertEquals(testId, user.getId());
        assertEquals(testFirstname, user.getFirstName());
        assertEquals(testLastname, user.getLastName());
        assertEquals(testEmail, user.getEmailAddress());
        assertEquals(testPassword, user.getPassword());
        assertEquals(testSalt, user.getSalt());
    }

    @Test
    public void testSetterOfUserObject() {
        User user = new User(testId, testFirstname, testLastname, testEmail, testPassword, testSalt);
        user.setId(testid2);
        user.setFirstName(testFirstname2);
        user.setLastName(testLastname2);
        user.setEmailAddress(testEmail2);
        user.setPassword(testPassword2);
        user.setSalt(testSalt2);

        assertEquals(testid2, user.getId());
        assertEquals(testFirstname2, user.getFirstName());
        assertEquals(testLastname2, user.getLastName());
        assertEquals(testEmail2, user.getEmailAddress());
        assertEquals(testPassword2, user.getPassword());
        assertEquals(testSalt2, user.getSalt());
    }

    @Test
    public void testSetAndGetRole() {
        User user = new User(testId, testFirstname, testLastname, testEmail, testPassword, testSalt);
        user.setRole(Role.ADMIN);
        assertEquals(Role.ADMIN, user.getRole());
        user.setRole(Role.STUDENT);
        assertEquals(Role.STUDENT, user.getRole());
        user.setRole(Role.TEACHER);
        assertEquals(Role.TEACHER, user.getRole());
    }

    @Test
    public void testGetUserName() {
        User user = new User(testId, testFirstname, testLastname, testEmail, testPassword, testSalt);
        assertEquals(testFirstname+" "+testLastname, user.getUserName());
    }

    @Test
    public void testSetRoleWithString_WithRightStrings() {
        User user = new User(testId, testFirstname, testLastname, testEmail, testPassword, testSalt);
        try {
            user.setRoleWithString("TEACHER");
        } catch(StringDidNotMatchOnRoleException e) {
            e.printStackTrace();
        }
        assertEquals(Role.TEACHER, user.getRole());

        try {
            user.setRoleWithString("STUDENT");
        } catch(StringDidNotMatchOnRoleException e) {
            e.printStackTrace();
        }
        assertEquals(Role.STUDENT, user.getRole());

        try {
            user.setRoleWithString("ADMIN");
        } catch(StringDidNotMatchOnRoleException e) {
            e.printStackTrace();
        }
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test (expected = StringDidNotMatchOnRoleException.class)
    public void testSetRoleWithString_WithWrongStringteacher2() throws StringDidNotMatchOnRoleException {
        User user = new User(testId, testFirstname, testLastname, testEmail, testPassword, testSalt);
        user.setRoleWithString("teacher2");
    }
}
