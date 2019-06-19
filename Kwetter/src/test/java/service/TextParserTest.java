/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Dennis
 */
public class TextParserTest {
    
    @Test
    public void mentionTextTest() {

        List<String> mention1 = TextParserService.getMentions("@User1");
        assertEquals(Arrays.asList("User1"), mention1);

        List<String> mention2 = TextParserService.getMentions("Dit is een test Kweet @User1 hallo");
        assertEquals(Arrays.asList("User1"), mention2);
        
        List<String> mention3 = TextParserService.getMentions("Dit is een test Kweet @User1 hallo @User2");
        assertEquals(Arrays.asList("User1","User2"), mention3);
    }

    @Test
    public void hashTagTextTest() {
        List<String> hashtag1 = TextParserService.getHashtags("Dit is een test #Kweet @User1 hallo @User2");
        assertEquals(Arrays.asList("Kweet"), hashtag1);
        
        List<String> hashtag2 = TextParserService.getHashtags("Dit is een test #Kweet @User1 #hallo @User2");
        assertEquals(Arrays.asList("Kweet","hallo"), hashtag2);
        
         List<String> hashtag3 = TextParserService.getHashtags("Dit is een test #Kweet @User1 #hallo #Cool @User2");
        assertEquals(Arrays.asList("Kweet","hallo","Cool"), hashtag3);

    }

}
