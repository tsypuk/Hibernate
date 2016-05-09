import model.Comment;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertTrue;

/**
 * Created by rtsy on 10.01.2016.
 */
public class TestCriteria extends BaseConfig{
    private Session _session;
    @Before
    public void init2(){
        _session = getSessionFactory().openSession();
    }

    @After
    public void destroy2() {
        _session.close();
    }

    @Test
    public void testComparisonBetween() {
        Criteria criteria = _session.createCriteria(Comment.class);
        criteria.add(Restrictions.between("_id", new Long(10), new Long(15)));
        assertTrue(criteria.list().size() > 0);
    }

    @Test
    public void testComparisonGreater() {
        Criteria criteria = _session.createCriteria(Comment.class);
        criteria.add(Restrictions.gt("_id", new Long(10)));
        assertTrue(criteria.list().size() > 0);
    }

    @Test
    public void testComparisonIn() {
        Criteria criteria = _session.createCriteria(Comment.class);
        Long[] ids = {10L, 11L, 12L};
        criteria.add(Restrictions.in("_id", ids));
        assertTrue(criteria.list().size() > 0);
    }

    @Test
    public void testComparisonIsNull() {
        Criteria criteria = _session.createCriteria(Comment.class);
             criteria.add(Restrictions.isNull("_id"));
        assertTrue(criteria.list().size() == 0);
    }

    @Test
    public void testComparisonIsNotNull() {
        Criteria criteria = _session.createCriteria(Comment.class);
        criteria.add(Restrictions.isNotNull("_name"));
        assertTrue(criteria.list().size() > 0);
    }

    @Test
    public void testComparisonLike() {
        Criteria criteria = _session.createCriteria(Comment.class);
        List<Comment> list = criteria.add(Restrictions.like("_name", "%second%")).list();
        assertTrue(list.stream().map(comment -> comment.getName()).collect(Collectors.toList()).contains("Second Update"));
        assertTrue(criteria.list().size() > 0);

        criteria = _session.createCriteria(Comment.class);
        list = criteria.add(Restrictions.like("_name", "S", MatchMode.START)).list();
        assertTrue(list.size() > 0);
    }

    @Test
    public void testComparisonIgnoreCase() {
        Criteria criteria = _session.createCriteria(Comment.class);
        List<Comment> list = criteria.add(Restrictions.eq("_name", "sEcOnD uPDaTE")).list();
        // Hibernate still searches using ignorecase ? Mysql by default is not case insansitve
        // Here needs to be done check of rdbms configuration does it support case insansitive search.
        assertTrue(list.size() > 0);

        criteria = _session.createCriteria(Comment.class);
        list = criteria.add(Restrictions.eq("_name", "sEcOnD uPDaTE").ignoreCase()).list();

        assertTrue(criteria.list().size() > 0);
    }
}