/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.HashtagDao;
import dao.JPA;
import dao.UserDao;
import domain.User;
import java.util.ArrayList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TextParserService {

    public static List<String> getHashtags(String text) {

        String regex = "(?<=^|(?<=[^a-zA-Z0-9-_\\\\.]))#([A-Za-z][A-Za-z0-9_]+)";//todo see if I can clean the regex (currently found my regex online) https://stackoverflow.com/questions/40321718/java-parsing-mentions-from-tweet
        Matcher matcher = Pattern.compile(regex).matcher(text);
        List<String> hashtags = new ArrayList<String>();
        while(matcher.find()) {
            String match = matcher.group(0).substring(1);
            hashtags.add(match);
            
        }
        System.out.println("Hashtags: " + hashtags);
        return hashtags;
    }

    public static List<String> getMentions(String text) {

        String regex = "(?<=^|(?<=[^a-zA-Z0-9-_\\\\.]))@([A-Za-z][A-Za-z0-9_]+)"; //todo see if I can clean the regex (currently found my regex online) https://stackoverflow.com/questions/40321718/java-parsing-mentions-from-tweet
        Matcher matcher = Pattern.compile(regex).matcher(text);
        List<String> mentions = new ArrayList<String>();
        while(matcher.find()) {
            String match = matcher.group(0).substring(1);
            mentions.add(match);
            
        }
        return mentions;
//        List<User> mentions = new ArrayList<User>();
//        while(matcher.find()) {
//            String match = matcher.group(0).substring(1);
//            User u = userDao.findUser(match);
//            if(u != null){
//                mentions.add(u);
//            } else {
//                System.out.println("User with username: " + match + " not found");
//            }
//        }
//        return mentions;
    }
}
