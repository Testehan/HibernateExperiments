package com.testehan.hibernate.basics.mappings.inheritance.v2.union;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

/* (see first advantage in comment from method printAllBillingDetails)
Another much more important advantage is the ability to handle polymorphic associations; for example, an association
mapping from User to BillingDetails would now be possible. Hibernate can use a UNION query to simulate a single table
as the target of the association mapping
 */

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting the Hibernate App");

        Configuration conf = new Configuration().configure().addAnnotatedClass(BankAccount.class).addAnnotatedClass(CreditCard.class);
        SessionFactory sessionFactory = conf.buildSessionFactory();
        Session session = sessionFactory.openSession();


        Transaction tx = session.beginTransaction();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner("Dan");
        bankAccount.setAccountNumber("12341235436324356");
        bankAccount.setBankName("Wells Fargo");
        bankAccount.setSwift("WF12313");

        CreditCard creditCard = new CreditCard();
        creditCard.setOwner("Dan");
        creditCard.setCardNumber("1234-5678-0000-1234");
        creditCard.setExpMonth("july");
        creditCard.setExpYear(2025);


        long idBankAccount = (Long)session.save(bankAccount);
        long idCreditCard = (Long)session.save(creditCard);
        tx.commit();

        tx = session.beginTransaction();

        BankAccount bankAccountRead = session.get(BankAccount.class,idBankAccount);
        System.out.println("Reading BankAccount from the DB " + bankAccountRead);

        CreditCard creditCardRead = session.get(CreditCard.class,idCreditCard);
        System.out.println("Reading CreditCard from the DB " + creditCardRead);

        tx.commit();
        session.close();


        printAllBillingDetails(sessionFactory);

        sessionFactory.close();
    }

    private static void printAllBillingDetails(SessionFactory sf) {
        System.out.println("Printing all BillingDetails from the DB tables.");
        System.out.println("Notice that 1 select is sent to the DB, UNION is being used");
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            /*
            You may ask whether this query will really perform better than two separate statements. Here you can let the
            database optimizer find the best execution plan to combine rows from several tables,
            instead of merging two result sets in memory as Hibernate’s polymorphic loader engine would do.

            (we need the fully qualified name for this polymorphic query to work)
             */
            Query query = session.createQuery("from com.testehan.hibernate.basics.mappings.inheritance.v2.union.BillingDetails");

            List<BillingDetails> billingDetails = query.list();
            for (BillingDetails bd : billingDetails)
                System.out.println(bd);

            session.getTransaction().commit();
        }
    }

}
