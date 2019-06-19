/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Hashtag;
import domain.Kweet;
import java.util.List;

public interface HashtagDao {
    
    public void createHashtag(Hashtag h);
    
    public void removeHashtag(Hashtag h);

    public List<Hashtag> getHashtags();
    
    public Hashtag findHashtag(String text);

    public void editHashtag(Hashtag h);
}
