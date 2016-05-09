import junit.framework.Assert;
import junit.framework.TestCase;
import model.Comment;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Collection;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by rtsy on 26.12.2015.
 */
public class TestSessionFirstLevelCache extends BaseConfig {

    @Test
    public void testSessionFirstLevelCache() {
        Session session = getSessionFactory().openSession();
        assertEquals(0, session.getStatistics().getEntityCount());

        Collection<Comment> commentList = queryComments(session);
        assertEquals(commentList.size(), session.getStatistics().getEntityCount());

        int sessionCacheSize = commentList.size();

        for (Comment comment : commentList) {
            session.evict(comment);
            sessionCacheSize--;
            assertEquals(sessionCacheSize, session.getStatistics().getEntityCount());
        }

        session.clear();
        assertEquals(0, session.getStatistics().getEntityCount());
        commentList = queryComments(session);
        assertFalse(session.isDirty());
        commentList.forEach(comment -> {
            comment.setName("MODIFIED");
        });
        assertTrue(session.isDirty());
        session.close();

    }

    private Collection<Comment> queryComments(Session session) {
        final Query query = session.createQuery("from Comment");
        System.out.println("executing: " + query.getQueryString());
        Collection<Comment> comments = query.list();
        comments.forEach(comment -> {
            System.out.println(comment);
        });
        return comments;
    }
}