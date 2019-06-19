/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Hashtag;
import domain.Kweet;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

@ApplicationScoped
@Default
public class HashtagDaoColl implements HashtagDao {

    
    private static long lastId = 0;
    private List<Hashtag> hashtags = new ArrayList<Hashtag>();

//    @Override
//    public Hashtag createHashtag(String text, Kweet kweet) {
//        if(this.findHashtag(text)== null){
//            Hashtag hashtag = new Hashtag(lastId++,text, kweet);
//            hashtags.add(hashtag);
//            return hashtag;
//        } else {
//            throw new IllegalArgumentException("The Hashtag is already in the existing.");
//        }
//    }
    
    @Override
    public void removeHashtag(Hashtag h) {
        if(h != null){
        hashtags.remove(h);
        } else{
            throw new IllegalArgumentException("The Hashtag does not exist");
        }
    }

    @Override
    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    @Override
    public Hashtag findHashtag(String text) {
        //todo clean create nullobject for hashtag?
        for(Hashtag h : hashtags){
            if(h.getText().equals(text)){
                return h;
            }
        }
        //throw new NoSuchElementException("No hashtag with the text: " + text);
        return null;
    }

    @Override
    public void createHashtag(Hashtag h) {
        if(!this.hashtags.contains(h)){
            this.hashtags.add(h);
        }
    }

    @Override
    public void editHashtag(Hashtag h) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
