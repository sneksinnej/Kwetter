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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@JPA
public class HashtagDaoJPA implements HashtagDao {

    @PersistenceContext(unitName = "KwetterPU")
    EntityManager em;

    @Override
    public List<Hashtag> getHashtags() {
        Query query = em.createQuery("SELECT h FROM Hashtag h");
        return new ArrayList<>(query.getResultList());
    }

    @Override
    public Hashtag findHashtag(String text) {
        TypedQuery<Hashtag> query = em.createNamedQuery("hashtag.findBytext", Hashtag.class);
        query.setParameter("text", text);
        List<Hashtag> result = query.getResultList();
        System.out.println("Trying to find hashtag in database: " + text);
        if (result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

//    @Override
//    public Hashtag createHashtag(String text, Kweet kweet) {
//        if (this.findHashtag(text) == null) {
//            Hashtag hashtag = new Hashtag(text, kweet);
//            em.persist(hashtag);
//            System.out.println("persisting hashtag: " + text);
//            return hashtag;
//        } else {
//            throw new IllegalArgumentException("The Hashtag is already in the existing.");
//        }
//    }
    @Override
    public void removeHashtag(Hashtag h) {
        em.remove(em.merge(h));
    }

    @Override
    public void createHashtag(Hashtag h) {
        em.persist(h);
    }

    @Override
    public void editHashtag(Hashtag h) {
        em.merge(h);
    }

    public void changeEntityManager(EntityManager em) {
        this.em = em;
    }

}
