/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.HashtagDao;
import dao.JPA;
import domain.Hashtag;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HashtagService {
    

    @Inject @JPA
    HashtagDao hashtagDao;
    
    public Hashtag findHashtag(String text){
        return hashtagDao.findHashtag(text);
    }
    
    public List<Hashtag> getHashtags(){
        return hashtagDao.getHashtags();
    }
    
}
