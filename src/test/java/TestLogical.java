import model.Comment;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by rtsy on 10.01.2016.
 */
public class TestLogical extends BaseConfig {
    private Session _session;

    @Before
    public void init2() {
        _session = getSessionFactory().openSession();
    }

    @After
    public void destroy2() {
        _session.close();
    }


    /**
     * select
     * this_.ID as ID1_1_0_,
     * this_.name as name2_1_0_,
     * this_.OPTLOCK as OPTLOCK3_1_0_
     * from
     * comments this_
     * where
     * this_.ID>=?
     * and this_.name like ?
     * 20:09:56,537 TRACE BasicBinder:84 - binding parameter [1] as [BIGINT] - 10
     * 20:09:56,540 TRACE BasicBinder:84 - binding parameter [2] as [VARCHAR] - %Sec%
     */
    @Test
    public void testLogicalAnd() {
        Criteria criteria = _session.createCriteria(Comment.class);
        criteria.add(Restrictions.ge("_id", new Long(10)));
        criteria.add(Restrictions.like("_name", "%Sec%"));
        assertTrue(criteria.list().size() > 0);
    }

    /**
     * select
     * this_.ID as ID1_1_0_,
     * this_.name as name2_1_0_,
     * this_.OPTLOCK as OPTLOCK3_1_0_
     * from
     * comments this_
     * where
     * (
     * (
     * this_.ID>=?
     * and this_.name like ?
     * )
     * or this_.ID<?
     * )
     * 20:13:32,970 TRACE BasicBinder:84 - binding parameter [1] as [BIGINT] - 10
     * 20:13:32,973 TRACE BasicBinder:84 - binding parameter [2] as [VARCHAR] - %S%
     * 20:13:32,975 TRACE BasicBinder:84 - binding parameter [3] as [BIGINT] - 20
     */
    @Test
    public void testLogicalOr() {
        Criteria criteria = _session.createCriteria(Comment.class)
                .add(
                        Restrictions.or(
                                Restrictions.and(
                                        Restrictions.ge("_id", new Long(10)),
                                        Restrictions.like("_name", "%S%")
                                ),
                                Restrictions.lt("_id", new Long(20))
                        )
                );
        assertTrue(criteria.list().size() > 0);
    }

    /**
     * The same query can be applied using the conjunction/disjanction calls
     * select
     * this_.ID as ID1_1_0_,
     * this_.name as name2_1_0_,
     * this_.OPTLOCK as OPTLOCK3_1_0_
     * from
     * comments this_
     * where
     * (
     * (
     * this_.ID>=?
     * and this_.name like ?
     * )
     * or this_.ID<?
     * )
     * 20:23:38,529 TRACE BasicBinder:84 - binding parameter [1] as [BIGINT] - 10
     * 20:23:38,531 TRACE BasicBinder:84 - binding parameter [2] as [VARCHAR] - %S%
     * 20:23:38,532 TRACE BasicBinder:84 - binding parameter [3] as [BIGINT] - 20
     */
    @Test
    public void testLogicalDisjunction() {
        Criteria criteria = _session.createCriteria(Comment.class)
                .add(
                        Restrictions.disjunction()
                                .add(Restrictions.conjunction()
                                                .add(
                                                        Restrictions.ge("_id", new Long(10)))
                                                .add(
                                                        Restrictions.like("_name", "%S%"))
                                ).add(
                                Restrictions.lt("_id", new Long(20))
                        )
                );
        assertTrue(criteria.list().size() > 0);
    }

    /**
     select
     this_.ID as ID1_1_0_,
     this_.name as name2_1_0_,
     this_.OPTLOCK as OPTLOCK3_1_0_
     from
     comments this_
     order by
     this_.ID asc
     */
    @Test
    public void testLogicalOrder() {
        Criteria criteria = _session.createCriteria(Comment.class);
        criteria.addOrder(Order.asc("_id"));
        Long id = -1L;
        List<Comment> list = criteria.list();
        for (Comment comment : list) {
            assertTrue(comment.getId() > id);
            id = comment.getId();
        }
    }
}