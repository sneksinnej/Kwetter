/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Kweet;
import domain.Language;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

//@ApplicationScoped
//@Default
@Stateless
@JPA
public class UserDaoJPA implements UserDao {

    @PersistenceContext(unitName = "KwetterPU")
    EntityManager em;

//    @Override
//    public User createUser(String username, String bio, byte[] photo, String location, String website, String wachtwoord, Language language) {
//        System.out.println("Trying to create user in database: " + username);
//        if (this.findUser(username) == null) {
//            User u = new User(username, bio, photo, location, website, wachtwoord, language);
//            em.persist(u);
//            return u;
//        } else {
//            throw new IllegalArgumentException("The user with username: " + username + " is already in the existing.");
//        }
//    }

    @Override
    public List<User> getUsers() {
        Query query = em.createQuery("SELECT u FROM User u");
        return new ArrayList<>(query.getResultList());
    }

    @Override
    public User findUser(String username) {
            TypedQuery<User> query = em.createNamedQuery("user.findByusername", User.class);
            query.setParameter("username", username);
            List<User> result = query.getResultList();
            System.out.println("Trying to find user in database: " + username);
            if(result.size() == 0){
                System.out.println("User with username: " + username + " not found");
                return null;
            }
            return result.get(0);
    }

    @Override
    public void followUser(User currentUser, User followUser) {
       if (currentUser.getFollowing().contains(followUser)) {
            currentUser.removeFollowing(followUser);
            followUser.removeFollower(currentUser);
        } else {
            currentUser.addFollowing(followUser);
            followUser.addFollower(currentUser);
        }
       this.editUser(followUser);
       this.editUser(currentUser);
    }

    public int countUsers() {
        return getUsers().size();
    }

    @Override
    public void createUser(User u) {
       em.persist(u);
    }

    @Override
    public void editUser(User u) {
        em.merge(u);
    }

    @Override
    public void removeUser(User u) {
//        for(Kweet k : u.getKweets()){
//           em.remove(em.merge(k));
//        }
        em.remove(em.merge(u));
    }
    
    public void changeEntityManager(EntityManager em){
        this.em = em;
    }

}
