package Bean;

import Enums.RegistrationError;
import Enums.RegistrationResult;
import Enums.Role;
import Exceptions.StringDidNotMatchOnRoleException;
import Services.AccessToken.AccessTokenService;
import Services.User.UserService;
import Datamodel.User;
import org.apache.commons.validator.routines.EmailValidator;
import utils.HashGeneratorUtils;
import utils.SessionUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class RegistrationBean {

    private UserService userService = new UserService();
    private AccessTokenService accessTokenService = new AccessTokenService();

    private String accessToken;
    private String emailAddress;
    private String password;
    private String repeatPassword;
    private String firstName;
    private String lastName;

    //admin registration values
    private String role;
    private String passwordUnHashed;

    //change password values
    private String newPassword;

    private RegistrationResult registrationResult = null;
    private RegistrationError registrationError = RegistrationError.NO_ERROR_DETECTED;

    public String registerNewUser() {
        if(this.accessTokenService.validateAccessToken(this.accessToken)) {
            if(!userService.doesEmailExistAlready(this.emailAddress)) {
                if(EmailValidator.getInstance().isValid(this.emailAddress)) {
                    if(this.password.equals(this.repeatPassword)) {
                        int salt = (int) (Math.random() * 100000) + 1;
                        this.password = HashGeneratorUtils.generateSHA1(this.password+salt);
                        User user = new User(this.firstName, this.lastName, this.emailAddress, this.password, salt);
                        user.setRole(Role.STUDENT);
                        userService.addNewUser(user);
                        this.accessTokenService.deleteLatestValidatedToken();
                        this.registrationResult = RegistrationResult.SUCCESSFULLY_REGISTERED;
                        return "login";
                    } else {
                        this.registrationError = RegistrationError.REPEAT_PASSWORD_DOES_NOT_MATCH_ERROR;
                    }
                } else {
                    this.registrationError = RegistrationError.EMAIL_NOT_VALID_ERROR;
                }
            } else {
                this.registrationError = RegistrationError.EMAIL_EXISTS_ALREADY_ERROR;
            }
        } else {
            this.registrationError = RegistrationError.ACCESS_TOKEN_IS_NOT_VALID_ERROR;
        }
        return "registrationFailed";
    }

    public String registerNewUserByAdmin() {
        if(!userService.doesEmailExistAlready(this.emailAddress)) {
            if(EmailValidator.getInstance().isValid(this.emailAddress)) {
                int salt = (int) (Math.random() * 100000) + 1;
                this.password = Integer.toString( (int) (Math.random() * 1000000)+ 1);
                this.passwordUnHashed = this.password;
                this.password = HashGeneratorUtils.generateSHA1(this.password+salt);
                User user = new User(this.firstName, this.lastName, this.emailAddress, this.password, salt);
                try {
                    user.setRoleWithString(this.role);
                    userService.addNewUser(user);
                    this.registrationResult = RegistrationResult.SUCCESSFULLY_CREATED_NEW_USER;
                    return "registrationSuccessful";
                } catch(StringDidNotMatchOnRoleException e) {
                    this.registrationError = RegistrationError.STRING_ROLE_DOES_NOT_MATCH_ROLE_ERROR;
                }
            } else {
                this.registrationError = RegistrationError.EMAIL_NOT_VALID_ERROR;
            }
        } else {
            this.registrationError = RegistrationError.EMAIL_EXISTS_ALREADY_ERROR;
        }
        return "registrationFailed";
    }

    public String changePassword() {
        User user = this.userService.getUserByEmailAddress(SessionUtils.getSession().getAttribute("email").toString());
        if(user.getPassword().equalsIgnoreCase(HashGeneratorUtils.generateSHA1(this.password+user.getSalt()))) {
            if(this.newPassword.equalsIgnoreCase(this.repeatPassword)) {
                this.newPassword = HashGeneratorUtils.generateSHA1(this.newPassword+user.getSalt());
                user.setPassword(this.newPassword);
                this.userService.updateUser(user);
                this.registrationResult = RegistrationResult.SUCCESSFULLY_CHANGED_PASSWORD;
                return "changePasswordSuccessful";
            } else {
                this.registrationError = RegistrationError.REPEAT_NEW_PASSWORD_DOES_NOT_MATCH_ERROR;
            }
        } else {
            this.registrationError = RegistrationError.PASSWORD_DOES_NOT_MATCH_ERROR;
        }
        return "changePasswordFailed";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RegistrationError getRegistrationError() {
        return registrationError;
    }

    public RegistrationResult getRegistrationResult() {
        return registrationResult;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPasswordUnHashed() {
        return passwordUnHashed;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
