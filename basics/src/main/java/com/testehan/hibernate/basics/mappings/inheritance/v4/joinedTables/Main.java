package com.testehan.hibernate.basics.mappings.inheritance.v4.joinedTables;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

/*
The fourth option is to represent inheritance relationships as SQL foreign key associations.
Every class/subclass that declares persistent properties—including abstract classes and even interfaces—has its own table.

Unlike the table-per-concrete-class strategy we mapped first, the table of a concrete @Entity here contains columns
only for each non-inherited property, declared by the subclass itself, along with a primary key that is also a foreign
key of the superclass table

The primary advantage of this strategy is that it normalizes the SQL schema.
Schema evolution and integrity-constraint definition are straightforward. A foreign key referencing the table of a
particular subclass may represent a polymorphic association to that particular subclass.

As you can see, this mapping strategy is more difficult to implement by hand—even ad hoc reporting is more complex.
This is an important consideration if you plan to mix Hibernate code with handwritten SQL.
Furthermore, even though this mapping strategy is deceptively simple, our experience is that performance can be
unacceptable for complex class hierarchies. Queries always require a join across many tables, or many sequential reads.

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
        System.out.println("Notice that 1 select is sent to the DB, that uses a JOIN");
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            /*
            (we need the fully qualified name for this polymorphic query to work)
             */
            Query query = session.createQuery("from com.testehan.hibernate.basics.mappings.inheritance.v4.joinedTables.BillingDetails");

            List<BillingDetails> billingDetails = query.list();
            for (BillingDetails bd : billingDetails)
                System.out.println(bd);

            session.getTransaction().commit();
        }
    }

}
