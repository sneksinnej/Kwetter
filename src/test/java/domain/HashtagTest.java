/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Dennis
 */
public class HashtagTest {
    
    User henk;
    Kweet kweet1;
     
    @Before
    public void setupDao() {
        henk = new User("Henk", "HenkBio", new byte[4], "TestOmgeving", "www.testen.nl", "DitIsEenTestWachtwoord", Language.Dutch);
        List<User> mentions = new ArrayList<User>();
        kweet1 = new Kweet("kweet1", henk, mentions);
    }

    
    @Test
    public void addKweetTest() {
        Hashtag hashtag1 = new Hashtag("testHashtag", kweet1);
        assertEquals(Arrays.asList(kweet1), hashtag1.getKweets());
        assertEquals("testHashtag", hashtag1.getText());
    }

}
