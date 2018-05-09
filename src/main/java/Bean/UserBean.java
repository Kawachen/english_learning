package Bean;

import Enums.Role;
import Services.User.UserService;
import Utilities.User;
import utils.SessionUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;

@ManagedBean
@RequestScoped
public class UserBean {

    private User user;
    private UserService userService = new UserService();

    private String search = "";

    public UserBean() {
        this.user = this.userService.getUserByEmailAddress(SessionUtils.getSession().getAttribute("email").toString());
    }

    public void deleteUser(int userId) {
        this.userService.deleteUserById(userId);
    }

    public String getUserName() {
        return user.getUserName();
    }

    public boolean getIsUserTeacherOrAdmin() {
        return (this.user.getRole() == Role.TEACHER || this.user.getRole() == Role.ADMIN);
    }

    public boolean getIsUserAdmin() {
        return this.user.getRole() == Role.ADMIN;
    }

    public ArrayList<User> getAllUserWithSearch() {
        ArrayList<User> result = new ArrayList<User>();
        ArrayList<User> users = this.userService.getAllUsers();
        if(!this.search.equalsIgnoreCase("")) {
            for (User user:users) {
                if(user.getUserName().contains(this.search) || user.getEmailAddress().contains(this.search)) {
                    result.add(user);
                }
            }
        } else {
            result = users;
        }
        return result;
    }

    public void reload(){}

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
