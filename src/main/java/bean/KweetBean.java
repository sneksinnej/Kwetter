/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domain.Kweet;
import domain.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import service.KweetService;
import service.UserService;

/**
 *
 * @author Dennis
 */
@Named(value = "kweetBean")
@SessionScoped
public class KweetBean implements Serializable {

    @Inject
    private KweetService kweetService;
    @Inject 
    private UserService userService;
    
    @Getter
    @Setter
    private String username;
    
    public List<Kweet> getKweets() {
        List<Kweet> kweets = new ArrayList<>();
        if (username != null) {
            User user = this.userService.findUser(username);
            kweets.addAll(user.getKweets());
        } else {
            kweets.addAll(this.kweetService.getKweets());
        }
        System.out.println(kweets);
        return kweets;
    }
    
    public void removeKweet(Kweet kweet) {
        this.kweetService.removeKweet(kweet);
    }
}

