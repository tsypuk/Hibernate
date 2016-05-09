import model.inheritance.perSubclass.Contract_Employee;
import model.inheritance.perSubclass.Employee;
import model.inheritance.perSubclass.Regular_Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Created by rtsy on 13.12.2015.
 */
public class TablePerSubclassAnnotations {
    public static void main(String[] args) {
        AnnotationConfiguration cfg = new AnnotationConfiguration();
        Session session = cfg.configure("hibernate_perSubclass.cfg.xml").buildSessionFactory().openSession();

        Transaction t = session.beginTransaction();

        Employee e1 = new Employee();
        e1.setName("sonoo");

        Regular_Employee e2 = new Regular_Employee();
        e2.setName("Vivek Kumar");
        e2.setSalary(50000);
        e2.setBonus(5);

        Contract_Employee e3 = new Contract_Employee();
        e3.setName("Arjun Kumar");
        e3.setPay_per_hour(1000);
        e3.setContract_duration("15 hours");

        session.persist(e1);
        session.persist(e2);
        session.persist(e3);

        t.commit();
        session.close();
        System.out.println("success");
    }
}
