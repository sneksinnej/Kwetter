/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.JPA;
import dao.KweetDao;
import dao.UserDao;
import domain.Kweet;
import domain.Language;
import domain.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {

    @Inject @JPA
    UserDao userDao;
    
    @Inject @JPA
    KweetDao kweetDao;

    public void createUser(String username, String bio, byte[] photo, String location, String website, String password, Language language) {
        if (this.findUser(username) == null) {
            User u = new User(username, bio, photo, location, website, password, language);
            userDao.createUser(u);
        } else {
            throw new IllegalArgumentException("The user with username: " + username + " is already in the existing.");
        }
    }
    
    public void createSimpleUser(String username, String password){
        if (this.findUser(username) == null) {
            User u = new User(username, password);
            userDao.createUser(u);
        } else {
            throw new IllegalArgumentException("The user with username: " + username + " is already in the existing.");
        }
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User findUser(String username) {
        return userDao.findUser(username);
    }

    public void followUser(User currentUser, User followUser) {
        userDao.followUser(currentUser, followUser);
    }
    
    
    //TODO: FIX
    public void removeUser(User user){
        for(Kweet k : user.getKweets()){
           kweetDao.removeKweet(k);
        }
        for(Kweet k : user.getMentions()){
            k.removeMention(user);
        }
        for(Kweet k : user.getLikes()){
            k.removeLike(user);
            user.removeLike(k);
        }
        userDao.removeUser(user);
    }
    
    public void editUser(User user){
        userDao.editUser(user);
    }

}
