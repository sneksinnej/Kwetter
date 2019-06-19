/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.Language;
import domain.User;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class Mock {
    
    @Inject
    KweetService kweetService;
    
    @Inject
    UserService userService;

    @PostConstruct
    void initKwetter() {
        userService.createUser("Henk", "Dit is een henk biografie" , new byte[4],"Eindhoven","Website.com","wachtwoordSAFEWOWOWO",Language.Dutch);
        User user1 = userService.findUser("Henk");
        userService.createUser("Piet", "Dit is een piet biografie" , new byte[4],"Eindhoven","Website.com","wachtwoordSAFEWOWOWO",Language.Dutch);
        User user2 = userService.findUser("Piet");        
        userService.followUser(user1, user2);
        
        String kweetString = "derpLambok @Piet @Henk #test #Test1 #Cool";
        System.out.println("Users created");
        kweetService.createKweet(kweetString, user1);
        String kweetString2 ="derptweet2 #Cool";
        kweetService.createKweet(kweetString2, user2);
        
        kweetService.likeKweet(kweetService.getKweets().get(0), user2);
        
        
    }

}
