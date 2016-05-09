import model.Comment;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by rtsy on 28.12.2015.
 */
public class TestBinding extends BaseConfig {

    @Test
    public void testMalwareText() {
        //Trying the existing name that is only one in table
        String userInput = "'TEST_SESSION'";
        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("from Comment c where c._name =" + userInput);

        assertTrue(query.list().size()> 0);
        assertTrue(query.list().size()< 2);

        session.close();
    }

    @Test
    public void testSQLInjection() {
        //Trying SQL injection
        Session session = getSessionFactory().openSession();
        String sqlInjection = "'HACK' OR 1 = 1";
        Query query = session.createQuery("from Comment c where c._name =" + sqlInjection);
        for (Object comment : query.list()) {
            System.out.println(comment);
        }
        assertTrue(query.list().size() > 0);
        session.close();
    }

    @Test
    public void testSQLInjectionWithNamedBinding() {
        //Injection does not work with binding
        Session session = getSessionFactory().openSession();
        String sqlInjection = "'HACK' OR 1 = 1";
        Query query = session.createQuery("from Comment c where c._name = :input");
        query.setString("input", sqlInjection);
        assertFalse(query.list().size() > 0);

        session.close();
    }

    @Test
    public void testSQLInjectionWithPositionalBinding() {
        //Injection does not work with binding
        Session session = getSessionFactory().openSession();
        String sqlInjection = "'HACK' OR 1 = 1";
        Query query = session.createQuery("from Comment c where c._name = ?");
        query.setString(0, sqlInjection);
        assertFalse(query.list().size() > 0);

        session.close();
    }
}