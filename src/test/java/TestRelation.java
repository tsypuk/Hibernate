import model.Address;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by rtsy on 17.01.2016.
 */
public class TestRelation extends BaseConfig {

    @Test
    public void testAddressUser() {
        Address address = new Address();
        address.setCity("City");
        address.setStreet("Long Street");
        address.setZipcode("123456");

        User user = new User();
        user.setName("UserName");

        Session session = getSessionFactory().openSession();
        user.setHomeAddress(address);

        Transaction tx = session.beginTransaction();
        session.save(user);
        assertTrue(user.getId() != null);

//        address.setUser(user);
        assertTrue(address.getId() != null);
        tx.commit();
        session.close();
    }
}