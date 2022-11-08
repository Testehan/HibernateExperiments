package com.testehan.transactions.versioning.ex2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

public class Main
{
    public static void createSmsAndSetLength(String[] args )
    {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");

        createSmsAndSetLength(entityManagerFactory);

        entityManagerFactory.close();

    }

    private static void createSmsAndSetLength(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        Sms message = null;
        Long messageId = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            message = new Sms("Hello World!");

            entityManager.persist(message);
            messageId = message.getId();
            System.out.println(message);

            entityManager.getTransaction().commit();
            entityManager.close();



            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            message = entityManager.find(Sms.class, messageId);

            message.setLength(1_000_000l);

            // if you check the generated SQL you will see that the where clause contains all the old field values
            // with the intention of finding the corect unmodified (by another parallel transaction) row
            entityManager.getTransaction().commit();
            entityManager.close();


        } catch (RollbackException e){
            System.out.println("You modified in the DB directly dummy...so obviously you get an exception");
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

}
