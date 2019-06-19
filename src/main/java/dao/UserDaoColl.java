/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Language;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@ApplicationScoped
@Alternative
public class UserDaoColl implements UserDao {

    private static long lastId = 0;
    private ArrayList<User> users = new ArrayList<User>();

//    @Override
//    public User createUser(String username, String bio, byte[] photo, String location, String website, String wachtwoord, Language language){
//        if(this.findUser(username) == null){
//            User u = new User(lastId++,username,bio,photo,location,website,wachtwoord,language);
//            this.users.add(u);
//            return u;
//        } else {
//            throw new IllegalArgumentException("The user with username: " + username + " is already in the existing.");
//        }
//    }
    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User findUser(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void followUser(User currentUser, User followUser) {
        if (currentUser.getFollowing().contains(followUser)) {
            currentUser.removeFollowing(followUser);
            followUser.removeFollower(currentUser);
        } else {
            currentUser.addFollowing(followUser);
            followUser.addFollower(currentUser);
        }

    }

    @Override
    public void createUser(User u) {
        if (!this.users.contains(u)) {
            this.users.add(u);
        }
    }

    @Override
    public void editUser(User u) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeUser(User u) {
        if (this.users.contains(u)) {
            this.users.remove(u);
        }
    }
}
