
import model.inheritance.perHierarchy.BillingDetails;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * Created by rtsy on 12.12.2015.
 */
public class TestTablePerHierarchy extends BaseConfig {

    @Test
    public void testPersistance()
            throws SQLException {
        BillingDetails billingDetails = new BillingDetails();
        billingDetails.setOwner("ME");
        billingDetails.setLim("LIM");

        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(billingDetails);
        Long id = billingDetails.getId();
        tx.commit();
        session.close();

        Connection connection = getDataSource().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(*) from billing_details where BILLING_DETAIL_ID=" + id);
        resultSet.next();
        System.out.println("cool");
        assertEquals(1, resultSet.getLong(1));
    }

    @Test
    public void testIdentity() {
        Session session1 = getSessionFactory().openSession();
        Transaction tx1 = session1.beginTransaction();
        BillingDetails a = (BillingDetails) session1.load(BillingDetails.class, new Long(1));
        BillingDetails b = (BillingDetails) session1.load(BillingDetails.class, new Long(1));

        assertEquals(a, b);
        assertTrue(a == b);

        tx1.commit();
        session1.close();

        Session session2 = getSessionFactory().openSession();
        Transaction tx2 = session2.beginTransaction();

//        Check the sessions
        assertNotEquals(session1, session2);
        assertTrue(session1.hashCode() != session2.hashCode());
        assertTrue(session1 != session2);

        BillingDetails a2 = (BillingDetails) session2.load(BillingDetails.class, new Long(1));

        assertNotEquals(a, a2);
        assertTrue(a != a2);
        tx2.commit();
        session2.close();

        assertTrue(a.getId() == b.getId());

//        This objects are from different session they have diff in memory references
        assertFalse(a.getId() == a2.getId());
        assertEquals(a.getId(), a2.getId());
    }
}