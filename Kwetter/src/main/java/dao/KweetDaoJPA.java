/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Kweet;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

//@ApplicationScoped
//@Default
@Stateless
@JPA
public class KweetDaoJPA implements KweetDao {

    @PersistenceContext(unitName = "KwetterPU")
    EntityManager em;

//    @Override
//    public Kweet createKweet(String text, User owner, List<User> mentions) {
//        if (text.getBytes().length <= 140) {
//
//            Kweet k = new Kweet(text, owner, mentions);
//            owner.addKweet(k);
//            for (User u : mentions) {
//                u.addMention(k);
//                em.merge(u);
//            }
//            em.persist(k);
//            return k;
//        } else {
//            throw new IllegalArgumentException("Kweets cannot exceed 140 char limit");
//        }
//    }
    @Override
    public List<Kweet> getKweets() {

        Query query = em.createQuery("SELECT k FROM Kweet k");
        return new ArrayList<>(query.getResultList());
    }

    @Override
    public void createKweet(Kweet k) {
        em.persist(k);
    }

    @Override
    public Kweet findKweet(String input) {
        TypedQuery<Kweet> query = em.createNamedQuery("Kweet.find", Kweet.class);
        Long inputId = Long.parseLong(input);
        query.setParameter("id", inputId);
        List<Kweet> result = query.getResultList();
        System.out.println("Trying to find kweet in database: " + input);
        if (result.size() == 0) {
            System.out.println("kweet with id: " + input + " not found");
            return null;
        }
        return result.get(0);
    }

    @Override
    public void editKweet(Kweet k) {
        em.merge(k);
    }

    @Override
    public void removeKweet(Kweet k) {
        em.remove(em.merge(k));
    }

    public void changeEntityManager(EntityManager em) {
        this.em = em;
    }
}
