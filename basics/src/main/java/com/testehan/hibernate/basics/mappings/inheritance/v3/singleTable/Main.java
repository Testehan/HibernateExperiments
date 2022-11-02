package com.testehan.hibernate.basics.mappings.inheritance.v3.singleTable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

/* This mapping strategy is a winner in terms of both performance and simplicity. It’s the
best-performing way to represent polymorphism—both polymorphic and nonpolymorphic queries perform well—and it’s even
easy to write queries by hand. Ad hoc reporting is possible without complex joins or unions. Schema evolution is
straightforward.

 There is one major problem: data integrity. You must declare columns for properties
declared by subclasses to be nullable. If your subclasses each define several nonnullable properties, the loss of NOT
NULL constraints may be a serious problem from the
point of view of data correctness. Imagine that an expiration date for credit cards is
required, but your database schema can’t enforce this rule because all columns of the
table can be NULL. A simple application programming error can lead to invalid data.

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
        System.out.println("Notice that 1 select is sent to the DB, which selects all fields from the table in order to reconstruct the objects");
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            /*
            (we need the fully qualified name for this polymorphic query to work)
             */
            Query query = session.createQuery("from com.testehan.hibernate.basics.mappings.inheritance.v3.singleTable.BillingDetails");

            List<BillingDetails> billingDetails = query.list();
            for (BillingDetails bd : billingDetails)
                System.out.println(bd);

            session.getTransaction().commit();
        }
    }

}
