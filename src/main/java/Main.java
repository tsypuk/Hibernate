import model.inheritance.perHierarchy.BillingDetails;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import model.component.Address;
import model.component.User;
import model.inheritance.perHierarchy.BankAccount;
import model.inheritance.perHierarchy.CreditCard;

/**
 * Created by rtsy on 12.12.2015.
 */
public class Main {
    private static final SessionFactory ourSessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession()
            throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args)
            throws Exception {
        final Session session = getSession();
        try {
//            System.out.println("querying all the managed entities...");
//            final Map metadataMap = session.getSessionFactory().getAllClassMetadata();
//            for (Object key : metadataMap.keySet()) {
//                final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
//                final String entityName = classMetadata.getEntityName();
//                final Query query = session.createQuery("from " + entityName);
//                System.out.println("executing: " + query.getQueryString());
//                for (Object o : query.list()) {
//                    System.out.println("  " + o);
//                }
//
//            }

            Transaction tx = session.beginTransaction();
            CreditCard creditCard = generateCreditCard();
            session.save(creditCard);
            BankAccount bankAccount = generateBankAccount();
            session.save(bankAccount);
//
            BillingDetails billingDetails = new BillingDetails();
            billingDetails.setLim("none");
            billingDetails.setOwner("billing");
            session.save(billingDetails);
            tx.commit();
            final Query query = session.createQuery("from BillingDetails");
                System.out.println("executing: " + query.getQueryString());
            for (Object billingDetail : query.list()) {
                System.out.println((BillingDetails)billingDetail);
            }

//            System.out.println(session.createQuery("from / ").list());
        } finally {
            session.close();
        }
    }

    private static BankAccount generateBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner("Arnold2");
        bankAccount.setAccount("7772");
        bankAccount.setBankName("SWIFT2");
        bankAccount.setLim("333");
        return bankAccount;
    }

    private static CreditCard generateCreditCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.setOwner("Arnold");
        creditCard.setNumber("12345");
        creditCard.setLim("0");
        return creditCard;
    }

    private static User generateUser(Session session) {
        User user = new User();
        user.setFirstname("TEST2");
        Address address = new Address();
        address.setCity("LV2");
        address.setStreet("De2");
        address.setZipCode("0422");
        user.setAddress(address);

        return user;
    }
}
