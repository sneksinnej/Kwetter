package dao;

import dao.HashtagDao;
import dao.HashtagDaoColl;
import dao.KweetDao;
import dao.KweetDaoColl;
import domain.Hashtag;
import domain.Kweet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ModelHashtagTest {

    KweetDao kweetDao;
    HashtagDao hashtagDao;

    @Before
    public void setupDao() {
        hashtagDao = new HashtagDaoColl();
        kweetDao = new KweetDaoColl();
    }

    @Test
    public void createHashtag() {
        Hashtag hashtag1 = new Hashtag("hashtag1", null);
        hashtagDao.createHashtag(hashtag1);

        assertEquals(Arrays.asList(hashtag1), hashtagDao.getHashtags());
        List<Kweet> kweets = new ArrayList<Kweet>();
        Hashtag hashtag2 = new Hashtag((long)1 ,"hashtag2", kweets );
        hashtagDao.createHashtag(hashtag2);

        assertEquals(Arrays.asList(hashtag1,hashtag2), hashtagDao.getHashtags());
        
        assertEquals(hashtag1,hashtagDao.findHashtag("hashtag1"));
    }

}
