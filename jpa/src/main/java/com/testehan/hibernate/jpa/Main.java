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
        // Your application code shares the EntityManagerFactory, representing one
        //	persistence unit, or one logical database.
        // Most applications have only one shared EntityManagerFactory.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");

        // Begin a new session with the database by creating an EntityManager. This is your context
        // for all persistence operations;
        // You use the EntityManager for a single unit of work in a single thread, and it’s inexpensive to create
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            Message message = new Message("Hello World!");
            Attachment attachment = new Attachment();
            attachment.setAttachmentFile("simple file.txt");
            attachment.setMessage(message);
            message.getAttachments().add(attachment);
            Attachment attachment2 = new Attachment();
            attachment2.setAttachmentFile("virus.exe");
            attachment2.setMessage(message);
            message.getAttachments().add(attachment2);

            // Hibernate now knows that you wish to store that data, but it doesn't necessarily call
            // the database immediately
            entityManager.persist(message);
            System.out.println(message);

            Message message2 = new Message();
            // next line will throw an exception PropertyValueException: not-null property references a null or
            // transient value : com.testehan.hibernate.jpa.Message.text. Because I added an Basic annotation that
            // required that the text field contains a value not null. Better to get these exceptions in the java code, than
            // to hit the DB and
//        entityManager.persist(message2);

            // Commit the transaction. Hibernate automatically checks the persistence context and
            // executes the necessary SQL INSERT statement.
            entityManager.getTransaction().commit();


        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

//        selectAndPrintAllMessages(entityManagerFactory);
//        printAndDeleteAllMessagesAndAttachments(entityManagerFactory);
//        removingAttachmentFromListAlsoDeletesItFromDB(entityManagerFactory,attachment2);
    }

    private static void selectAndPrintAllMessages(EntityManagerFactory entityManagerFactory)
    {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            List<Message> messages = entityManager.createQuery("from Message").getResultList();
            for (Message m : messages) {
                System.out.println(m);
            }

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    /*
    "This deletion process is inefficient: Hibernate must always load the collection and delete each Attachment individually.
	A single SQL statement would have the same effect on the database: delete from BID where ITEM_ID = ?.
	You know this because nobody in the database has a foreign key reference on the
	BID table."
     */
    private static void printAndDeleteAllMessagesAndAttachments(final EntityManagerFactory entityManagerFactory)
    {
        EntityManager entityManager = null;
        try {
            EntityManager em = entityManagerFactory.createEntityManager();

            em.getTransaction().begin();

            List<Message> messages = em.createQuery("from Message").getResultList();
            for (Message m : messages) {
                System.out.println(m);
                em.remove(m);// because of  CascadeType.REMOVE set in Message on the list of Attachments, they will also be deleted
            }

            em.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static void removingAttachmentFromListAlsoDeletesItFromDB(final EntityManagerFactory entityManagerFactory, final Attachment attachment2) {

        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            Message messages = (Message) entityManager.createQuery("from Message").getSingleResult();
            System.out.println(messages);
            // because orphanRemoval = true when an obkect is removed from the list, it will also be removed from the DB
            messages.getAttachments().remove(attachment2);
            System.out.println(messages);

            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }
}
