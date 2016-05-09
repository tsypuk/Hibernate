/**
 * Created by rtsy on 13.12.2015.
 */

import model.inheritance.perHierarchy.Contract_Employee;
import model.inheritance.perHierarchy.Employee;
import model.inheritance.perHierarchy.Regular_Employee;
import org.hibernate.*;
import org.hibernate.cfg.*;

public class TablePerHierarchyAnnotations {
    public static void main(String[] args) {
        AnnotationConfiguration cfg = new AnnotationConfiguration();
        Session session = cfg.configure("hibernate_perHierarchy.cfg.xml").buildSessionFactory().openSession();

        Transaction tx = session.beginTransaction();

        Employee e1 = new Employee();
        e1.setName("Employer Name");

        Regular_Employee e2 = new Regular_Employee();
        e2.setName("Regular Name");
        e2.setSalary(50000);
        e2.setBonus(5);

        Contract_Employee e3 = new Contract_Employee();
        e3.setName("Contract Name");
        e3.setPay_per_hour(1000);
        e3.setContract_duration("15 hours");

        session.persist(e1);
        session.persist(e2);
        session.persist(e3);

        tx.commit();
        session.close();
        System.out.println("success");
    }
}
