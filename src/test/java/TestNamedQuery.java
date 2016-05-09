import model.Comment;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by rtsy on 29.12.2015.
 */
public class TestNamedQuery extends BaseConfig {

    @Test
    public void testNameQueryAnnotation() {
        Session session = getSessionFactory().openSession();
        Query namedQuery = session.getNamedQuery("findUniqueNameComments");
        List<Comment> commentList = namedQuery.list();
        System.out.println(commentList + " size: " + commentList.size());
        session.close();
    }

    @Test
    public void testNameQueryMapping() {
        Session session = getSessionFactory().openSession();
        Query namedQuery = session.getNamedQuery("getCommentsCount");
        // Not good practice in tutorials
        long count = (long) namedQuery.list().get(0);
        // Better to call uniqueResult
        // @throws NonUniqueResultException if there is more than one matching result
        long count2 = (long) namedQuery.uniqueResult();
        assertTrue(count == count2);
        System.out.println("count: " + count);
        assertTrue(count > 0);
        session.close();
    }

//    http://stackoverflow.com/questions/10912320/named-query-with-like-in-where-clause
    @Test
    public void testNameQueryMappingLike() {
        Session session = getSessionFactory().openSession();
        Query namedQuery = session.getNamedQuery("getCommentsNameLike")
                .setString("value", "%SESS%");
        List<Comment> comments = namedQuery.list();
        System.out.println("count: " + comments.size());
        System.out.println(comments);
        session.close();
    }
}
