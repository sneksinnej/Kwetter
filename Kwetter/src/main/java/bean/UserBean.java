/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domain.Role;
import domain.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import service.UserService;

/**
 *
 * @author Dennis
 */
@Named
@SessionScoped
public class UserBean implements Serializable {

    @Inject
    private UserService userService;

    public List<User> getUsers(String username) {
        if (username == null || username.isEmpty())
            return this.userService.getUsers();
        else
            return new ArrayList<>(Arrays.asList(this.userService.findUser(username)));
    }

    public void updateUserRole(String role) {}

    public void updateUserRole(User user, String role) {
        user.setRole(Role.valueOf(role));
        this.userService.editUser(user);
    }

    public void removeUser(User user) {
        this.userService.removeUser(user);
    }
}
