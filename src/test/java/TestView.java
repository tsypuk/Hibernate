import model.Report;
import model.ReportView;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by rtsy on 17.01.2016.
 */
public class TestView extends BaseConfig {

    @Test
    public void testView() {
        String hql = "from ReportView";
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(hql);
        List<ReportView> result = query.list();
//        assertTrue(result.size() > 0);
        int count = result.size();
        for (ReportView reportView : result) {
            reportView.setName("PATCHED");
        }
        tx.commit();
        session.close();
        if (result.size() > 0) {
            session = getSessionFactory().openSession();
            tx = session.beginTransaction();

            assertTrue(count == session.createQuery(hql).list().size());

            tx.commit();
            session.close();
        }
    }

    @Test
    public void testViewWithNativeSQL() {
        String sql = "select * from reportsview";
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createSQLQuery(sql);
        List<Report> list = query.list();
        assertTrue(list.size() > 0);
        tx.commit();
        session.close();
    }
}