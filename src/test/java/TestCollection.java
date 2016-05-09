import model.Comment;
import model.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by rtsy on 16.01.2016.
 */
public class TestCollection extends BaseConfig {
    @Test
    public void testCollectionProxied()
            throws SQLException {
        Item item = new Item();
        Set images = new HashSet<>();
        images.add("test1");
        images.add("test2");
        images.add("test3");
        item.setImages(images);

        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(item);
        long id = item.getId();
        tx.commit();
        session.close();

        session = getSessionFactory().openSession();
//        assertTrue(session.contains(item));
        item = (Item) session.load(Item.class, id);
        assertNotNull(item);
        assertEquals(item.getId(), Long.valueOf(id));
        assertTrue(item.getImages().size() == 3);
        session.close();

    }
}