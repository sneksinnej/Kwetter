/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Language;
import domain.User;
import java.util.List;

public interface UserDao {

    public void createUser(User u);

    public List<User> getUsers();
    
    public User findUser(String username);
    
    public void followUser(User currentUser, User followUser);

    public void editUser(User u);
    
    public void removeUser(User u);
    
    
}
