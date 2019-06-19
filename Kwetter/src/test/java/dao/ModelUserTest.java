package dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.UserDao;
import dao.UserDaoColl;

import domain.Language;
import domain.User;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ModelUserTest {
    
    UserDao userDao;
    
    
    @Before
    public void setupDao(){
        userDao = new UserDaoColl();
    }
    
    @Test
    public void createAndFindUserTest(){
        User user1 = new User("Henk", "HenkBio", new byte[4], "TestOmgeving", "www.testen.nl", "DitIsEenTestWachtwoord", Language.Dutch);
        userDao.createUser(user1);
        User henkUser = userDao.findUser("Henk");
        assertEquals("Check user", henkUser.getBio(), "HenkBio");
        User user2 = new User((long)12,"Piet", "PietBio", new byte[4], "TestOmgeving", "www.testen.nl", "DitIsEenTestWachtwoord", Language.Dutch);
        userDao.createUser(user2);
        User pietUser = userDao.findUser("Piet");
        assertEquals("Check user", pietUser.getBio(), "PietBio");
        User henkUser2 = userDao.findUser("Henk");
        assertEquals("Check user", henkUser2.getBio(), "HenkBio");
    }
    
    @Test
    public void getUsersTest(){
        User henkUser = new User("Henk", "HenkBio", new byte[4], "TestOmgeving", "www.testen.nl", "DitIsEenTestWachtwoord", Language.Dutch);
        User pietUser = new User((long)12,"Piet", "PietBio", new byte[4], "TestOmgeving", "www.testen.nl", "DitIsEenTestWachtwoord", Language.Dutch);
        userDao.createUser(henkUser);
        userDao.createUser(pietUser);
        assertEquals("Check user", userDao.getUsers().size(), 2);
    }
}
