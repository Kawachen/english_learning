package Bean;

import Enums.LoginError;
import Services.User.UserService;
import Datamodel.User;
import utils.HashGeneratorUtils;
import utils.SessionUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class LoginBean {
    private String emailAddress;
    private String password;

    private LoginError loginError = LoginError.NO_ERROR_DETECTED;

    public String login() {
        UserService userService = new UserService();
        User user = userService.getUserByEmailAddress(this.emailAddress);
        if(user != null)
        {
            this.password = HashGeneratorUtils.generateSHA1(this.password+user.getSalt());
            if(user.getPassword().equals(this.password)) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("email", this.emailAddress);
                session.setAttribute("role", user.getRole());
                return "loginSuccessful";
            } else {
                this.loginError = LoginError.PASSWORD_DOES_NOT_MATCH_ERROR;
            }
        }
        else
        {
            this.loginError = LoginError.USER_DOES_NOT_EXIST_ERROR;
        }
        return "loginFailed";
    }

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "logout";
    }

    public LoginError getLoginError() {
        return this.loginError;
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
}
