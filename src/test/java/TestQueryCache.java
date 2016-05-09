import junit.framework.TestCase;
import model.Comment;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by rtsy on 10.01.2016.
 */
public class TestQueryCache extends BaseConfig {

    private static final String COMMENT_CACHE_REGION = "commentCacheRegion";
    private static final String COMMENT_CACHE_REGION_CRITERIA = "commentCacheRegionCriteria";

    @Test
    public void testSecondLevelQueryCache() {
        Session session = getSessionFactory().openSession();
        assertEquals(0, session.getStatistics().getEntityCount());
        assertFalse(getSessionFactory().getCache().containsQuery(COMMENT_CACHE_REGION));
        assertFalse(getSessionFactory().getCache().containsQuery(COMMENT_CACHE_REGION_CRITERIA));
        callQuery(session);
        assertTrue(getSessionFactory().getCache().containsQuery(COMMENT_CACHE_REGION));
        callCriteria(session);
        assertTrue(getSessionFactory().getCache().containsQuery(COMMENT_CACHE_REGION_CRITERIA));

        getSessionFactory().getCache().evictCollectionRegions();
        getSessionFactory().getCache().evictEntityRegions();
        getSessionFactory().getCache().evictQueryRegions();
//TODO Why after calling evict the 2 level cache still handle query cache ?
        getSessionFactory().getCache().evictEntityRegion(Comment.class);
        assertTrue(getSessionFactory().getCache().containsQuery(COMMENT_CACHE_REGION_CRITERIA));

        assertTrue(Arrays.asList(getSessionFactory().getStatistics().getSecondLevelCacheRegionNames())
                .contains(COMMENT_CACHE_REGION));
        assertTrue(Arrays.asList(getSessionFactory().getStatistics().getSecondLevelCacheRegionNames())
                .contains(COMMENT_CACHE_REGION_CRITERIA));

        session.close();
    }

    private void callQuery(Session session) {
        final Query query = session.createQuery("from Comment where ID > 10 AND _name = 'Detached update'")
//        We need to enable query caching and specify the region for storing results
                .setCacheable(true)
                .setCacheRegion(COMMENT_CACHE_REGION);
        query.list();
    }

    private void callCriteria(Session session) {
        Criteria criteria = session.createCriteria(Comment.class)
                .setCacheable(true)
                .setCacheRegion(COMMENT_CACHE_REGION_CRITERIA)
                .add(Restrictions.eq("_name", "Detached update"))
                .add(Restrictions.gt("_id", 11L));
        criteria.list();
    }
}