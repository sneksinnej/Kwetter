package dao;


import dao.KweetDao;
import dao.KweetDaoColl;
import dao.UserDao;
import dao.UserDaoColl;
import domain.Kweet;

import domain.Language;
import domain.User;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ModelKweetTest {
    
    KweetDao kweetDao;
    UserDao userDao;
    User user1;
    
    @Before
    public void setupDao(){
        kweetDao = new KweetDaoColl();
        userDao = new UserDaoColl();
        User henk = new User("Henk", "HenkBio", new byte[4], "TestOmgeving", "www.testen.nl", "DitIsEenTestWachtwoord", Language.Dutch);
        userDao.createUser(henk);
        user1 = userDao.findUser("Henk");
    }
    
    @Test
    public void createKweetTest(){
        Kweet kweet1 = new Kweet("Dit is een test kweet", user1,new ArrayList<User>());
        kweetDao.createKweet(kweet1);
        assertEquals("1 kweet", kweetDao.getKweets().size() , 1);
    }
    
    @Test
    public void getKweetTest(){
        Kweet kweet1 = new Kweet("Dit is een test kweet", user1,new ArrayList<User>());
        Kweet kweet2 = new Kweet((long)10,"Dit is een andere test kweet", user1,new ArrayList<User>());
        kweetDao.createKweet(kweet1);
        assertEquals("1 kweet", kweetDao.getKweets().size() , 1);
        kweetDao.createKweet(kweet2);
        assertEquals("2 kweet", kweetDao.getKweets().size() , 2);
    }
}
