/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Hashtag;
import domain.Kweet;
import domain.User;
import java.util.List;


public interface KweetDao {

    public void createKweet(Kweet k);
    
    public List<Kweet> getKweets();
    
    public Kweet findKweet(String input);
    
    public void editKweet(Kweet k);
    
    public void removeKweet(Kweet k);
    
}
