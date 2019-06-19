/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Dennis
 */
public class UserTest {

    private final User user1;
    private final User user2;
    private final User user3;
    private final User user4;
    private final User user5;
    private final User user6;
    private final User user7;
    private final User user8;
    private final User user9;
    private final User user10;

    private final Kweet kweet1;
    private final Kweet kweet2;
    private final Kweet kweet3;
    private final Kweet kweet4;
    private final Kweet kweet5;
    private final Kweet kweet6;
    private final Kweet kweet7;
    private final Kweet kweet8;
    private final Kweet kweet9;
    private final Kweet kweet10;

   
    public UserTest() {
        this.user1 = new User("username1", "bio", new byte[1], "location", "website", "wachtwoord", Language.English);
        this.user2 = new User((long)1,"username2", "bio", new byte[1], "location", "website", "wachtwoord", Language.English);
        this.user3 = new User((long)2,"username3", "bio", new byte[1], "location", "website", "wachtwoord", Language.English);
        this.user4 = new User((long)3,"username4", "bio", new byte[1], "location", "website", "wachtwoord", Language.English);
        this.user5 = new User((long)4,"username5", "bio", new byte[1], "location", "website", "wachtwoord", Language.English);
        this.user6 = new User((long)5,"username6", "bio", new byte[1], "location", "website", "wachtwoord", Language.English);
        this.user7 = new User((long)6,"username7", "bio", new byte[1], "location", "website", "wachtwoord", Language.English);
        this.user8 = new User((long)7,"username8", "bio", new byte[1], "location", "website", "wachtwoord", Language.English);
        this.user9 = new User((long)8,"username9", "bio", new byte[1], "location", "website", "wachtwoord", Language.English);
        this.user10 = new User((long)9,"username10", "bio", new byte[1], "location", "website", "wachtwoord", Language.English);
        List<User> mentions = new ArrayList<User>();
        this.kweet1 = new Kweet((long)10,"kweet1", this.user1, mentions);
        this.kweet2 = new Kweet((long)11,"kweet2", this.user1, mentions);
        this.kweet3 = new Kweet((long)12, "kweet3", this.user2, mentions);
        this.kweet4 = new Kweet((long)13, "kweet4", this.user2,mentions);
        this.kweet5 = new Kweet((long)14, "kweet5",this.user3,mentions);
        this.kweet6 = new Kweet((long)15,"kweet6",this.user4,mentions );
        this.kweet7 = new Kweet((long)16,"kweet7",this.user4, mentions);
        this.kweet8 = new Kweet((long)17, "kweet8",this.user4,mentions);
        this.kweet9 = new Kweet((long)18,"kweet9",this.user4,mentions );
        this.kweet10 = new Kweet((long)19,"kweet10",this.user5,mentions );
    }


    @Test
    public void kweetTest() {
        assertEquals(Arrays.asList(this.kweet1, this.kweet2), this.user1.getKweets());
        assertEquals(Arrays.asList(this.kweet3, this.kweet4), this.user2.getKweets());
        assertEquals(Arrays.asList(this.kweet5), this.user3.getKweets());
        assertEquals(Arrays.asList(this.kweet6, this.kweet7, this.kweet8, this.kweet9), this.user4.getKweets());
        assertEquals(Arrays.asList(this.kweet10), this.user5.getKweets());
        
        // Duplicate kweet
        this.user1.addKweet(this.kweet1);
        assertEquals(Arrays.asList(this.kweet1, this.kweet2), this.user1.getKweets());

    }

    @Test
    public void mentionTest() {
        this.user1.addMention(this.kweet1);
        assertEquals(Arrays.asList(this.kweet1), this.user1.getMentions());

        this.user2.addMention(this.kweet2);
        assertEquals(Arrays.asList(this.kweet2), this.user2.getMentions());

        this.user3.addMention(this.kweet3);
        this.user3.addMention(this.kweet4);
        assertEquals(Arrays.asList(this.kweet3, this.kweet4), this.user3.getMentions());

        // Duplicate addMention
        this.user1.addMention(this.kweet1);
        assertEquals(Arrays.asList(this.kweet1), this.user1.getMentions());
    }


    @Test
    public void followerTest() {
        this.user3.addFollower(user1);
        this.user5.addFollower(user1);
        this.user7.addFollower(user1);
        this.user9.addFollower(user1);

        assertEquals(Arrays.asList(user1), this.user3.getFollowers());
        assertEquals(Arrays.asList(user1), this.user5.getFollowers());
        assertEquals(Arrays.asList(user1), this.user7.getFollowers());
        assertEquals(Arrays.asList(user1), this.user9.getFollowers());

        this.user4.addFollower(user2);
        this.user6.addFollower(user2);
        this.user8.addFollower(user2);

        assertEquals(Arrays.asList(user2), this.user4.getFollowers());
        assertEquals(Arrays.asList(user2), this.user6.getFollowers());
        assertEquals(Arrays.asList(user2), this.user8.getFollowers());

        this.user1.addFollower(user10);
        this.user2.addFollower(user10);
        this.user3.addFollower(user10);
        this.user4.addFollower(user10);
        this.user5.addFollower(user10);
        this.user6.addFollower(user10);
        this.user7.addFollower(user10);
        this.user8.addFollower(user10);
        this.user9.addFollower(user10);

        assertEquals(Arrays.asList(user10), this.user1.getFollowers());
        assertEquals(Arrays.asList(user10), this.user2.getFollowers());
        assertEquals(Arrays.asList(user1, user10), this.user3.getFollowers());
        assertEquals(Arrays.asList(user2, user10), this.user4.getFollowers());
        assertEquals(Arrays.asList(user1, user10), this.user5.getFollowers());
        assertEquals(Arrays.asList(user2, user10), this.user6.getFollowers());
        assertEquals(Arrays.asList(user1, user10), this.user7.getFollowers());
        assertEquals(Arrays.asList(user2, user10), this.user8.getFollowers());
        assertEquals(Arrays.asList(user1, user10), this.user9.getFollowers());
        
        // Duplicate follower
        this.user3.addFollower(user1);
        assertEquals(Arrays.asList(user1, user10), this.user3.getFollowers());
    }
    
        
    @Test
    public void CreateUser(){
        User testUser1 = new User("username1", "bio1", new byte[1], "location1", "website1", "wachtwoord1", Language.English);
        User testUser2 = new User("username2", "bio2", new byte[1], "location2", "website2", "wachtwoord2", Language.English);
        assertEquals("username1", testUser1.getUsername());
        assertEquals("username2", testUser2.getUsername());
        assertEquals("bio1" , testUser1.getBio());
        assertEquals("location2", testUser2.getLocation());
        assertEquals("website1", testUser1.getWebsite());
        assertEquals(Language.English, testUser1.getLanguage());
    }

}
