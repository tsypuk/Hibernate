import model.Comment;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by rtsy on 28.12.2015.
 */
public class TestHQLEffective extends BaseConfig {

    @Test
    public void testQuery() {
        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("from Comment c order by c._name")
                .setFirstResult(0)
                .setMaxResults(10);
        assertEquals(10, query.list().size());
        session.close();
    }

    @Test
    public void testCriteria() {
        Session session = getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Comment.class)
                .addOrder(Order.asc("_name"))
                .setMaxResults(10)
                .setFirstResult(0);
        assertEquals(10, criteria.list().size());
        session.close();
    }

    @Test(expected = NonUniqueResultException.class)
    public void testUniqueCriteria() {
        Session session = getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Comment.class)
                .addOrder(Order.asc("_name"))
                .setMaxResults(10);

        criteria.uniqueResult();
        session.close();
    }

    @Test(expected = NonUniqueResultException.class)
    public void testUniqueHQL() {
        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("from Comment com order by com._name desc ");

        query.uniqueResult();
        session.close();
    }

    @Test
    public void testPolymorphic() {
        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("from Comment");
        int commentsCount = query.list().size();

        query = session.createQuery("from java.lang.Object");
        int objectsCount = query.list().size();
        System.out.println("Objects===========");
        query.list().forEach(System.out::println);
        System.out.println("Objects===========");

        query = session.createQuery("from java.io.Serializable");
        int serializableCount = query.list().size();

        assertTrue(serializableCount > commentsCount);
        assertTrue(objectsCount > commentsCount);
        session.close();
    }

    @Test
    public void testRestriction() {
        Session session = getSessionFactory().openSession();
        Criterion nameEq = Expression.eq("_name", "TEST_NAME");
        Criteria criteria = session.createCriteria(Comment.class);
        criteria.add(nameEq);
        Comment comment = (Comment) criteria.uniqueResult();
        session.close();
    }
}