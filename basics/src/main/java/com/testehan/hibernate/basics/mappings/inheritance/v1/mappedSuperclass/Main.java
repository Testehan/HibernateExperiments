package com.testehan.hibernate.basics.mappings.inheritance.v1.mappedSuperclass;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

/*
We recommend this approach (only) for the top level of your class hierarchy,
where polymorphism isn’t usually required, and when modification of the superclass
in the future is unlikely.
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
        System.out.println("Notice that 2 selects are sent to the DB, one for each concrete class table");
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            /*
            Note that this also works for any superclass or interface, whether it's a @MappedSuperclass or not.
            The difference from a usual HQL query is that we have to use the fully qualified name since they are not
            Hibernate-managed entities.
             */
            Query query = session.createQuery("from com.testehan.hibernate.basics.mappings.inheritance.v1.mappedSuperclass.BillingDetails");

            List<BillingDetails> billingDetails = query.list();
            for (BillingDetails bd : billingDetails)
                System.out.println(bd);

            query = session.createQuery("from com.testehan.hibernate.basics.mappings.inheritance.v1.mappedSuperclass.RandomUselessInterface");

            billingDetails = query.list();
            for (BillingDetails bd : billingDetails)
                System.out.println(bd);

            session.getTransaction().commit();
        }
    }

}
