package com.testehan.transactions.versioning.ex3Manual;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import java.util.List;

/*

Let’s say you want to sum up all item Attachment sizes in several Email. This requires a
query for all Attachments in each Email, to add up the sizes. The problem is, what happens if someone moves an
Attachment from one Email to another Email while you’re
still querying and iterating through all the Emails and Attachments? With read-committed
isolation, the same Attachment might show up twice while your procedure runs!
To make the “get items in each category” reads repeatable, JPA’s Query interface
has a setLockMode() method. Look at the procedure in the following listing.

*/


public class Main
{
    public static void main( String[] args )
    {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");

        EntityManager entityManager = null;
        Email email = null;
        Long emailId = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            email = new Email("Hello World!");
            Attachment attachment = new Attachment();
            attachment.setAttachmentFile("simple file.txt");
            attachment.setEmail(email);
            attachment.setSize(100l);
            email.getAttachments().add(attachment);
            Attachment attachment2 = new Attachment();
            attachment2.setAttachmentFile("virus.exe");
            attachment2.setEmail(email);
            attachment2.setSize(150l);
            email.getAttachments().add(attachment2);

            entityManager.persist(email);
            System.out.println(email);
            emailId = email.getId();

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

        optimisticLock_calculateSizeOfAllAttachments(entityManagerFactory,email);
        pessimisticReadLock_calculateSizeOfAllAttachments(entityManagerFactory,email);

        entityManagerFactory.close();
    }

    private static void optimisticLock_calculateSizeOfAllAttachments(EntityManagerFactory entityManagerFactory, Email email) {
        EntityManager entityManager = null;
        long totalSize = 0;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            List<Attachment> attachments =
                    entityManager.createQuery("select a from Attachment a where a.email.id = :emailId")
                            .setLockMode(LockModeType.OPTIMISTIC)
                            .setParameter("emailId", email.getId())
                            .getResultList();
            for (Attachment attachment : attachments) {
                totalSize = totalSize + attachment.getSize();
            }

            /*
            For each Attachment loaded earlier with the locking query, Hibernate executes a SELECT during flushing.
            It checks whether the database version of each Attachment row is still the same as when it was loaded.
            If any ITEM row has a different version or the row no longer exists, an OptimisticLockException is thrown

            !!! You’ll have to enable versioning on the Attachment entity class as explained earlier (see ex1 maybe also
            ex2); otherwise, you can’t use the optimistic LockModeTypes with Hibernate.
             */

            entityManager.getTransaction().commit();


            entityManager.close();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

        System.out.println("Total size of all attachments is " + totalSize);
    }

    private static void pessimisticReadLock_calculateSizeOfAllAttachments(EntityManagerFactory entityManagerFactory, Email email) {
        EntityManager entityManager = null;
        long totalSize = 0;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            List<Attachment> attachments =
                    entityManager.createQuery("select a from Attachment a where a.email.id = :emailId")
                            .setLockMode(LockModeType.PESSIMISTIC_READ)
                            .setHint("javax.persistence.lock.timeout", 5000)
                            .setParameter("emailId", email.getId())
                            .getResultList();
            for (Attachment attachment : attachments) {
                totalSize = totalSize + attachment.getSize();
            }

            /*
                Query all Attachment instances in PESSIMISTIC_READ lock mode. Hibernate locks the rows in the database
                with the SQL query. If possible, wait 5 seconds if another transaction holds a conflicting lock. If
                the lock can’t be obtained, the query throws an exception.
                If the query returns successfully, you know that you hold an exclusive lock on the data
                and no other transaction can access it with an exclusive lock or modify it until this transaction commits.
                Your locks are released after commit, when the transaction completes.
             */

            entityManager.getTransaction().commit();
            /*
             PostgreSQL, on the other hand, supports shared read locks: the PESSIMISTIC_READ
            mode appends FOR SHARE to the SQL query. PESSIMISTIC_WRITE uses an exclusive
            write lock with FOR UPDATE    (if you check the sql query for the Attachments select you will see "for share"
            in it.)
             */

            entityManager.close();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

        System.out.println("Total size of all attachments is " + totalSize);
    }

}
