import model.Comment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by rtsy on 26.12.2015.
 */
public class TestVersioning extends BaseConfig {
    public static final String INIT_OBJECT = "Init object";
    public static final String FIRST_UPDATE = "First Update";
    public static final String SECOND_UPDATE = "Second Update";
    public static final String DETACHED_UPDATE = "Detached update";
    private long id;

    @Test
    public void testVersionCommentUpdateSession()
            throws SQLException {
        Comment comment = new Comment();
        comment.setName(INIT_OBJECT);

        Session session = getSessionFactory().openSession();
        System.out.println(session.getStatistics());
        Transaction tx = session.beginTransaction();
        session.save(comment);
        this.id = comment.getId();
        assertEquals(0, comment.getVersion());
        tx.commit();
        session.close();

        session = getSessionFactory().openSession();
        tx = session.beginTransaction();
        comment = (Comment) session.get(Comment.class, id);
        comment.setName(FIRST_UPDATE);
        session.save(comment);
        tx.commit();
        assertEquals(1, comment.getVersion());
        assertEquals(FIRST_UPDATE, comment.getName());

        tx = session.beginTransaction();
        comment.setName(SECOND_UPDATE);
        assertEquals(id , (long) comment.getId());
        assertTrue(session.contains(comment));
        session.save(comment);
        tx.commit();
        session.close();
        assertEquals(2, comment.getVersion());
        assertEquals(SECOND_UPDATE, comment.getName());
        assertEquals(id , (long) comment.getId());

        /*
        This is fully detached object. After modifying it out of session scope, it save like a
        another object with. Changes its ID.
        */
        comment.setName(DETACHED_UPDATE);
        assertEquals(2, comment.getVersion());
        session = getSessionFactory().openSession();
        tx = session.beginTransaction();
/*
     * Update the persistent instance with the identifier of the given detached
	 * instance. If there is a persistent instance with the same identifier,
	 * an exception is thrown. This operation cascades to associated instances
	 * if the association is mapped with {@code cascade="save-update"}
	 *
 */
        session.update(comment);

        tx.commit();
        session.close();
        assertEquals(3, comment.getVersion());
        assertEquals(DETACHED_UPDATE, comment.getName());
        //ID had changed this is another object that was inserted
        assertEquals(this.id, (long) comment.getId());
    }

    @Test
    public void testVersionCommentSessionSave()
            throws SQLException {
        // Creating Transient comment.
        Comment comment = new Comment();
        comment.setName(INIT_OBJECT);

        Session session = getSessionFactory().openSession();
        System.out.println(session.getStatistics());
        Transaction tx = session.beginTransaction();
        // Persistent.
        session.save(comment);
        this.id = comment.getId();
        assertEquals(0, comment.getVersion());
        tx.commit();
        session.close();

        session = getSessionFactory().openSession();
        tx = session.beginTransaction();
        // We are loading this Persistent Entity.
        comment = (Comment) session.get(Comment.class, id);
        assertFalse(session.isDirty());
        comment.setName(FIRST_UPDATE);
        assertTrue(session.isDirty());
        // Modifying it. Only after commit the version value is increasing.
        session.save(comment);
        tx.commit();
        assertEquals(1, comment.getVersion());
        assertEquals(FIRST_UPDATE, comment.getName());
        assertEquals(Long.valueOf(id), comment.getId());

        tx = session.beginTransaction();
        comment.setName(SECOND_UPDATE);
        assertEquals(id , (long) comment.getId());
        assertTrue(session.contains(comment));
        session.save(comment);
        tx.commit();
        session.close();
        assertEquals(2, comment.getVersion());
        assertEquals(SECOND_UPDATE, comment.getName());
        assertEquals(Long.valueOf(id) , comment.getId());

        /*
        This is fully Detached object. Session is closed. After modifying it out of session scope, it save like a
        another object with. Changes its ID.
        */

        comment.setName(DETACHED_UPDATE);
        assertEquals(2, comment.getVersion());
        session = getSessionFactory().openSession();
        tx = session.beginTransaction();
/*
     * Persist the given transient instance, first assigning a generated identifier. (Or
	 * using the current value of the identifier property if the <tt>assigned</tt>
	 * generator is used.) This operation cascades to associated instances if the
	 * association is mapped with {@code cascade="save-update"}
 */
        session.save(comment);
        tx.commit();
        // Save generates new ID for this Detached Object and save it. It is new Persistent object.
        session.close();
        assertEquals(2, comment.getVersion());
        assertEquals(DETACHED_UPDATE, comment.getName());
        //ID had changed this is another object that was inserted
        assertNotEquals(this.id, (long) comment.getId());
    }
}