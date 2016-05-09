import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;

import java.sql.SQLException;

/**
 * Created by rtsy on 20.12.2015.
 */
public class BaseConfig {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/study_innodb";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";
    private static final String HIBERNATE_CFG = "hibernate_test.cfg.xml";

    private BasicDataSource dataSource;
    private SessionFactory sessionFactory;

    protected BasicDataSource getDataSource() {
        return dataSource;
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected void init() {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(JDBC_USER);
        dataSource.setDefaultAutoCommit(false);
        dataSource.setPassword(JDBC_PASSWORD);
        dataSource.setAccessToUnderlyingConnectionAllowed(true);
//        This is old-style call. Since 4.0 it is deprecated
//        sessionFactory = new Configuration().configure(HIBERNATE_CFG).buildSessionFactory();
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG);
        sessionFactory = configuration.buildSessionFactory(
                new ServiceRegistryBuilder().applySettings(
                        configuration.getProperties()). buildServiceRegistry()
        );
    }

    protected void destroy()
            throws SQLException {
        dataSource.close();
        dataSource = null;
        sessionFactory.close();
        sessionFactory = null;
    }

    @Before
    public void setUp()
            throws SQLException {
        init();
    }

    @After
    public void tearDown()
            throws SQLException {
        destroy();
    }
}