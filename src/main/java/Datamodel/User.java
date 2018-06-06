package Datamodel;

import Enums.Role;
import Exceptions.StringDidNotMatchOnRoleException;
import Datamodel.UserKomponents.UserName;
import Datamodel.UserKomponents.UserPassword;

public class User {

    private int id;
    private UserName userName = new UserName();
    private String emailAddress;
    private UserPassword userPassword = new UserPassword();
    private Role role;

    public User(String firstname, String lastname, String emailAddress, String password, int salt) {
        this.setFirstName(firstname);
        this.setLastName(lastname);
        this.setEmailAddress(emailAddress);
        this.setPassword(password);
        this.setSalt(salt);
    }

    public User(int id, String firstname, String lastname, String emailAddress, String password, int salt) {
        this.setId(id);
        this.setFirstName(firstname);
        this.setLastName(lastname);
        this.setEmailAddress(emailAddress);
        this.setPassword(password);
        this.setSalt(salt);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalt() {
        return userPassword.salt;
    }

    public void setSalt(int salt) {
        this.userPassword.salt = salt;
    }

    public String getUserName() {
        return userName.toString();
    }

    public void setFirstName(String firstName) {
        this.userName.firstname = firstName;
    }

    public void setLastName(String lastName) {
        this.userName.lastname = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setRoleWithString(String roleString) throws StringDidNotMatchOnRoleException {
        if(roleString.equals("STUDENT")) {
            this.role = Role.STUDENT;
        } else if(roleString.equals("TEACHER")) {
            this.role = Role.TEACHER;
        } else if(roleString.equals("ADMIN")) {
            this.role = Role.ADMIN;
        } else {
            throw new StringDidNotMatchOnRoleException();
        }
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return userPassword.password;
    }

    public void setPassword(String password) {
        this.userPassword.password = password;
    }

    public String getFirstName() {
        return userName.firstname;
    }

    public String getLastName() {
        return userName.lastname;
    }
}
