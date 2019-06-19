/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import domain.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class UserJPATest {
    
    private static final Logger LOGGER = Logger.getLogger(UserJPATest.class.getName());

    @Rule
    public final EntityManagerProvider provider = EntityManagerProvider.withUnit("KweetTestPU");
    private CriteriaQuery<User> query;
    private User user1;

    @Before
    public void setup() {
        
        user1 = new User("user1", "testWachtwoord");
        CriteriaBuilder builder = this.provider.em().getCriteriaBuilder();
        query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
    }

    @Test
    public void testSavingNewAccount() {
        this.provider.begin();
        this.provider.em().persist(user1);
        List<User> resultList = this.provider.em().createQuery(query).getResultList();
        assertEquals(1, resultList.size());
        for (User u : resultList) {
            assertNotNull(u.getId());
            LOGGER.log(Level.INFO, u.toString());
        }
        this.provider.commit();
    }
    
    @After
    public void breakdown() {
        this.provider.begin();
        this.provider.em().remove(user1);
        this.provider.commit();
    }
        
}
