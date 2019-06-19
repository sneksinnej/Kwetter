/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.HashtagDao;
import dao.JPA;
import dao.KweetDao;
import dao.UserDao;
import domain.Hashtag;
import domain.Kweet;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class KweetService {

    @Inject
    private TextParserService textParserService;

    @Inject
    @JPA
    private UserDao userDao;

    @Inject
    @JPA
    private KweetDao kweetDao;

    @Inject
    @JPA
    private HashtagDao hashtagDao;

    public Kweet createKweet(String text, User kweetUser) {
        List<User> mentions = this.getUsersMentions(textParserService.getMentions(text));
        System.out.println("Mentions: " + mentions);
        if (text.getBytes().length <= 140) {
            Kweet k = new Kweet(text, kweetUser, mentions);
            kweetDao.createKweet(k);
            userDao.editUser(kweetUser);
            for (User u : mentions) {
                u.addMention(k);
            }

            for (String hashtag : textParserService.getHashtags(text)) {
                Hashtag h = hashtagDao.findHashtag(hashtag);
                if (h == null) {
                    h = new Hashtag(hashtag, k);
                    hashtagDao.createHashtag(h);
                } else {
                    h.addKweet(k);
                }
                k.addHashtag(h);
            }
            return k;
        } else {
            throw new IllegalArgumentException("Kweets cannot exceed 140 char limit");
        }
    }

    public List<Kweet> getKweets() {
        return kweetDao.getKweets();
    }
    
    public Kweet getKweet(String id){
        return kweetDao.findKweet(id);
    }

    public void likeKweet(Kweet kweet, User likeUser) {
        if (kweet.getLikes().contains(likeUser)) {
            kweet.removeLike(likeUser);
            likeUser.removeLike(kweet);
            userDao.editUser(likeUser);
            kweetDao.editKweet(kweet);
        } else {
            kweet.addLike(likeUser);
            likeUser.addLike(kweet);
            userDao.editUser(likeUser);
            kweetDao.editKweet(kweet);
        }
    }

    private List<User> getUsersMentions(List<String> mentions) {
        List<User> mentionList = new ArrayList<User>();
        for(String mention : mentions){
            User u = userDao.findUser(mention);
            if (u != null) {
                mentionList.add(u);
            } else {
                System.out.println("User with username: " + mention + " not found");
            }
        }
        return mentionList;
    }
    
    public void removeKweet(Kweet kweet){
        this.kweetDao.removeKweet(kweet);
    }

}
