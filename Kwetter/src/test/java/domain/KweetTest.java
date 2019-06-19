/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Dennis
 */
public class KweetTest {

    User henk;
     
    @Before
    public void setupDao() {
        henk = new User("Henk", "HenkBio", new byte[4], "TestOmgeving", "www.testen.nl", "DitIsEenTestWachtwoord", Language.Dutch);
    }

    @Test
    public void createKweet(){
        Kweet k = new Kweet("Dit is een test kweet", henk,new ArrayList<User>());
        assertTrue(henk.getKweets().contains(k));
        assertEquals(k.getOwner(), henk);
        
    }

}
