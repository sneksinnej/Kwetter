/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import domain.Kweet;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author Dennis
 */
public class KweetJPATest {

    private static final Logger LOGGER = Logger.getLogger(UserJPATest.class.getName());

    @Rule
    public final EntityManagerProvider provider = EntityManagerProvider.withUnit("KweetTestPU");
    private CriteriaQuery<Kweet> kweetQuery;
    private CriteriaQuery<User> userQuery;
    private User user1;

    @Before
    public void setup() {

        user1 = new User("user1", "testWachtwoord");
        CriteriaBuilder builder = this.provider.em().getCriteriaBuilder();
        kweetQuery = builder.createQuery(Kweet.class);
        Root<Kweet> root = kweetQuery.from(Kweet.class);
        kweetQuery.select(root);
        userQuery = builder.createQuery(User.class);
        Root<User> root1 = userQuery.from(User.class);
        userQuery.select(root1);
    }

    @Test
    public void testCreateKweet() {
        this.provider.begin();
        this.provider.em().persist(user1);
        List<User> mentions = new ArrayList<User>();
        Kweet kweet1 = new Kweet("kweet1", user1, mentions);
        this.provider.em().persist(kweet1);
        List<Kweet> resultKweetList = this.provider.em().createQuery(kweetQuery).getResultList();
        assertEquals(1, resultKweetList.size());
        for (Kweet k : resultKweetList) {
            assertNotNull(k.getId());
            LOGGER.log(Level.INFO, k.toString());
        }
        List<User> resultUserList = this.provider.em().createQuery(userQuery).getResultList();
        assertEquals(1, resultUserList.size());
        for (User u : resultUserList) {
            assertNotNull(u.getId());
            assertEquals(1, u.getKweets().size());
            LOGGER.log(Level.INFO, u.toString());
        }
        this.provider.em().remove(kweet1);
        this.provider.commit();
    }

    @After
    public void breakdown() {
        this.provider.begin();
        this.provider.em().remove(user1);
        this.provider.commit();
    }
}
