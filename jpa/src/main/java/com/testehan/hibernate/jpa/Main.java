package com.testehan.hibernate.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main
{
    public static void main( String[] args )
    {

        //Once it starts, your application should create the EntityManagerFactory; the factory is
        //thread-safe, and all code in your application that accesses the database should share it.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");

        // Begin a new session with the database by creating an EntityManager. This is your context
        // for all persistence operations
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        Message message = new Message("Hello World!");

        // Hibernate now knows that you wish to store that data, but it doesn't necessarily call
        // the database immediately
        em.persist(message);


        Message message2 = new Message();
        // next line will throw an exception PropertyValueException: not-null property references a null or
        // transient value : com.testehan.hibernate.jpa.Message.text. Because I added an Basic annotation that
        // required that the text field contains a value not null. Better to get these exceptions in the java code, than
        // to hit the DB and
//        em.persist(message2);

        // Commit the transaction. Hibernate automatically checks the persistence context and
        // executes the necessary SQL INSERT statement.
        em.getTransaction().commit();
        em.close();

        selectAndPrintAllMessages(entityManagerFactory);
    }

    private static void selectAndPrintAllMessages(EntityManagerFactory entityManagerFactory) {

        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        List<Message> messages = em.createQuery("from Message").getResultList();
        for (Message m: messages) {
            System.out.println(m);
        }

        em.getTransaction().commit();
        em.close();
    }
}
