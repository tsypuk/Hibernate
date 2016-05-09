import model.Comment;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

/**
 * Created by rtsy on 29.12.2015.
 */
public class TestProjection extends BaseConfig {

    @Test
    public void testProjection() {
        //Trying the existing name that is only one in table
        String userInput = "'TEST_SESSION'";
        Session session = getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Comment.class);
        int a1 = criteria.list().size();
        System.out.println(a1);
        criteria.setProjection(Projections.distinct(Projections.property("_name")));
        criteria.list();
        int a2 = criteria.list().size();
        System.out.println(a2);
        assertTrue(a1 > a2);
        session.close();
    }

}
