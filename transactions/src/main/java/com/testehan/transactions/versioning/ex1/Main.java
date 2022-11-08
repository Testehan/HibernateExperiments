package com.testehan.transactions.versioning.ex1;

import javax.persistence.*;

public class Main
{
    public static void main( String[] args )
    {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");

//        simpleVersionExample(entityManagerFactory);
//        optimisticLockExceptionIsThrownIfDbRowIsUpdatedByOtherTransaction(entityManagerFactory);
        noRollbackExceptionIsThrownIfFiledIsMarkedAsExcluded(entityManagerFactory);

        entityManagerFactory.close();

    }

    private static void noRollbackExceptionIsThrownIfFiledIsMarkedAsExcluded(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        Message message = null;
        Long messageId = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            message = new Message("Hello World!");

            entityManager.persist(message);
            messageId = message.getId();
            System.out.println(message);

            entityManager.getTransaction().commit();
            entityManager.close();



            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            message = entityManager.find(Message.class, messageId);

            System.out.println("Version is 0 " + (message.getVersion() == 0));

            message.setLength(1_000_000l);

            entityManager.getTransaction().commit();
            entityManager.close();



            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            message = entityManager.find(Message.class, messageId);

            System.out.println("Version is still 0 because field length is marked as excluded from versioning" + (message.getVersion() == 0));

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

    private static void optimisticLockExceptionIsThrownIfDbRowIsUpdatedByOtherTransaction(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = null;
        Message message = null;
        Long messageId = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            message = new Message("Hello World!");

            entityManager.persist(message);
            messageId = message.getId();
            System.out.println(message);

            entityManager.getTransaction().commit();




            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            message = entityManager.find(Message.class, messageId);

            System.out.println("Version is 0 " + (message.getVersion() == 0));

            message.setText("New Text 2");
// put a breakpoint on the next line, and modify in the DB the entity so that it has another version
            entityManager.getTransaction().commit();

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
