import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by rtsy on 20.12.2015.
 */
@RunWith (Suite.class)
@Suite.SuiteClasses ({
        TestBinding.class,
        TestHQLEffective.class,
        TestNamedQuery.class,
        TestProjection.class,
        TestSessionFirstLevelCache.class,
        TestSessionOperationsConfig.class,
        TestTablePerHierarchy.class,
        TestVersioning.class,
        TestQueryCache.class,
        TestCriteria.class,
        TestLogical.class,
        TestCollection.class,
        TestRelation.class,
        TestView.class,
        TestProjection.class
})
public class JunitTestSuite {
}