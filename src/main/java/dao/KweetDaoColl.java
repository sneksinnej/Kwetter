/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Hashtag;
import domain.Kweet;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

@ApplicationScoped
@Alternative
public class KweetDaoColl implements KweetDao {

    private static long lastId = 0;
    private ArrayList<Kweet> kweets = new ArrayList<Kweet>();

    @Override
    public List<Kweet> getKweets() {
        return kweets;
    }

//    @Override
//    public Kweet createKweet(String text, User owner, List<User> mentions) {
//
//        if (text.getBytes().length <= 140) {
//
//            Kweet k = new Kweet(lastId++, text, owner, mentions);
//            owner.addKweet(k);
//            for (User u : mentions) {
//                u.addMention(k);
//            }
//            this.kweets.add(k);
//            return k;
//        } else {
//            throw new IllegalArgumentException("Kweets cannot exceed 140 char limit");
//        }
//
//    }

//    @Override
//    public void likeKweet(Kweet kweet, User likeUser) {
//        if (likeUser.getLikes().contains(kweet)) {
//            kweet.removeLike(likeUser);
//            likeUser.removeLike(kweet);
//        } else {
//            kweet.addLike(likeUser);
//            likeUser.addLike(kweet);
//        }
//    }

    @Override
    public void createKweet(Kweet k) {
        if(!this.kweets.contains(k)){
            this.kweets.add(k);
        }
    }

    @Override
    public Kweet findKweet(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editKweet(Kweet k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeKweet(Kweet k) {
        if(this.kweets.contains(k)){
            this.kweets.remove(k);
        }
    }
}
