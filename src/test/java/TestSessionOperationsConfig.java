import org.hibernate.Hibernate;
import org.hibernate.LockOptions;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.proxy.HibernateProxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Report;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

public class TestSessionOperationsConfig extends BaseConfig {

    @Test
    public void testStoreInSet() throws Exception {

        Set<Report> reportsSet = new HashSet();
        List<Report> reportsList = new ArrayList<Report>();
        for (int i = 0; i < 5; i++) {
            Report report = generateReport(i);
            reportsSet.add(report);
            reportsList.add(report);
        }

        for (Report report : reportsList) {
             assertTrue(reportsSet.contains(report));
        }

        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        for (Report report : reportsSet) {
            session.save(report);
        }

        tx.commit();
        session.close();

        for (Report report : reportsList) {
            assertTrue(reportsSet.contains(report));
        }
    }

    private Report generateReport(int i) {
        Report report = new Report();
        report.setName("report:" + i);
        report.setDate(new Date(new Random(i).nextInt((i < 1) ? 1 : i),
                                (i < 11) ? i : 11,
                                (i < 30) ? i : 30));
        return report;
    }

    @Test
    public void testUpdate() {
        Report report = generateReport(1);
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(report);
        tx.commit();
        session.close();

        assertNotNull(report.getId());

        report.setName("UPDATE_READY");
        report.setDate(new Date(115, 11, 31));

        Session session2 = getSessionFactory().openSession();
        Transaction tx2 = session2.beginTransaction();
        session2.update(report);
        tx2.commit();
        session2.close();
    }

    @Test
    public void testLock() {
        Report report = generateReport(40);
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(report);
        tx.commit();
        session.close();

        assertNotNull(report.getId());

        report.setDate(new Date(316, 0, 1));
        report.setName("BEFORE_LOCK");

        //TODO Check why does this modifies object before lock
//        Changes made before the call to lock() aren't propagated to the database
        Session session2 = getSessionFactory().openSession();
        Transaction tx2 = session2.beginTransaction();
//        Session.LockRequest lockRequest = session2.buildLockRequest(LockOptions.NONE);
        Session.LockRequest lockRequest = session2.buildLockRequest(LockOptions.READ);
        lockRequest.lock(report);

//        This is deprecated syntax :) but it used in book
//        session2.lock(report, LockMode.NONE);
        report.setName("AFTER_LOCK");

        tx2.commit();
        session2.close();
    }

    @Test
    public void testGet() {
        Report report = generateReport(50);
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(report);
        tx.commit();
        session.close();

        assertNotNull(report.getId());
        Long ID = report.getId();

        Session session2 = getSessionFactory().openSession();
        Transaction tx2 = session2.beginTransaction();

//        Get always returns the instance of over type with no proxy
        Report report2 = (Report) session2.get(Report.class, ID);
        assertTrue(report2.equals(report));
        assertFalse(report2 instanceof HibernateProxy);

        tx2.commit();
        session2.close();
    }

    @Test
    public void testMergeOnSave() {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Report report = generateReport(11);
        Long id = (Long) session.save(report);
        report.setName("NEW CREATED");
        tx.commit();

        Transaction tx2 = session.beginTransaction();
        Report reportMerge = (Report) session.merge(report);

        assertEquals(report, reportMerge);
        assertTrue(report == reportMerge);

        report.setName("PRIMARY");
        assertEquals(reportMerge.getName(), "PRIMARY");
        reportMerge.setName("MERGE_RETURNED");
        assertEquals("MERGE_RETURNED", report.getName());
        tx2.commit();
        session.close();
    }

    @Test
    public void testMergeOnLoad() {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Report report = (Report) session.load(Report.class, 200L);
        assertFalse(Hibernate.isInitialized(report));
        assertFalse(Hibernate.isPropertyInitialized(report, "name"));
        report.setName("LOADED");
        tx.commit();

        Transaction tx2 = session.beginTransaction();
        Report reportMerge = (Report) session.merge(report);

        assertEquals(report, reportMerge);
        assertTrue(report != reportMerge);

        report.setName("PRIMARY");
        assertEquals(reportMerge.getName(), "PRIMARY");
        reportMerge.setName("MERGE_RETURNED");
        assertEquals("MERGE_RETURNED", report.getName());
        tx2.commit();
        session.close();
    }

    /**
     * If @Proxy(lazy=true) then exception is thrown only after get method invoke.
     * If @Proxy(lazy=false) then exception is thrown immediately.
     */
    @Test(expected = ObjectNotFoundException.class)
    public void testLoad() {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Object proxyReport = session.load(Report.class, -100L);

//        Method threw 'org.hibernate.ObjectNotFoundException' exception.
        assertNotNull(proxyReport);
        assertTrue(proxyReport instanceof HibernateProxy);
//  We are calling method on proxy object provoke Exception
        ((Report)proxyReport).getName();
        tx.commit();
        session.close();
    }

    @Test
    public void testGetException() {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        assertNull(session.get(Report.class, -1L));
        tx.commit();
        session.close();
    }
}