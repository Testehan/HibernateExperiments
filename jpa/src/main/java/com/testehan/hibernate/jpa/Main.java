package com.testehan.hibernate.jpa;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
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
        Message message = null;
        Long messageId = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            message = new Message("Hello World!");
            Attachment attachment = new Attachment();
            attachment.setAttachmentFile("simple file.txt");
            attachment.setMessage(message);
            message.getAttachments().add(attachment);
            Attachment attachment2 = new Attachment();
            attachment2.setAttachmentFile("virus.exe");
            attachment2.setMessage(message);
            message.getAttachments().add(attachment2);

            // Hibernate now knows that you wish to store that data, but it doesn't necessarily call the database immediately
            entityManager.persist(message);
            System.out.println(message);
            messageId = message.getId();

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

           mergeMethod(entityManagerFactory,message);
//        accessHibernateClasses(entityManagerFactory,messageId);
//        refreshData(entityManagerFactory,messageId);
//        findReferenceToMessageById(entityManagerFactory,messageId);
//        findMessageByIdAndChangeText(entityManagerFactory,messageId);
//        printVariousStatesAnObjectIs(entityManagerFactory);
//        selectAndPrintAllMessages(entityManagerFactory);
//        printAndDeleteAllMessagesAndAttachments(entityManagerFactory);
//        removingAttachmentFromListAlsoDeletesItFromDB(entityManagerFactory,attachment2);
    }

    private static void mergeMethod(EntityManagerFactory entityManagerFactory, Message detachedMessage) {
        System.out.println("Inside mergeMethod");
        System.out.println("the goal is record the new text of the detached Message.");
        detachedMessage.setText("Setting some new text on this detached instance");

        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Message attachedMessage = entityManager.merge(detachedMessage);
            // make sure not to have the  @Immutable annotation the Message class when doing the test, otherwise that
            // annotation will be the one that will be preventing the saving of the new name in the DB

            // name will be the one set here : detachedMessage.setText("Setting some new text on this detached instance");
            // or we could set whatever else we want on the attachedMessage instance

            Message notInTheDbMessage = new Message();
            notInTheDbMessage.setText("Freshly created");
            // the merge() operation can handle detached and transient entity instances.
            //	Hibernate always returns the result to you as a persistent instance.
            Message persistendMessage = entityManager.merge(notInTheDbMessage); // will also be in the DB

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    // by using method unwrap you can access JPA implementation classes, in this case from hibernate
    // below we are accessing the Session class to make a persistence context to read-only mode
    // you would do such a setting when doing optimizations when you are sure that you will just be performing reads
    private static void accessHibernateClasses(EntityManagerFactory entityManagerFactory, Long messageId) {
        System.out.println("Inside accessHibernateClasses");

        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.unwrap(Session.class).setDefaultReadOnly(true);       // setting read only

            entityManager.getTransaction().begin();

            Message message = entityManager.find(Message.class,messageId);
            // make sure not to have the  @Immutable annotation the Message class when doing the test, otherwise that
            // annotation will be the one that will be preventing the saving of the new name in the DB
            message.setText("Some Name");

            entityManager.getTransaction().commit();    // no update will be performed because we are in read only

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static void refreshData(EntityManagerFactory entityManagerFactory, Long messageId) {
        System.out.println("Inside refreshData");

        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();


            Message message = entityManager.find(Message.class,messageId);
            message.setText("Some Name");

            // Someone updates this row in the database
            String oldName = message.getText();
            /*
                Calling refresh() causes Hibernate to execute a SELECT to read and marshal a whole result set, overwriting
                changes you already made to the persistent instance in application memory.
             */
            entityManager.refresh(message);
            System.out.println("Old text : " + oldName + " vs refreshed text : " +message.getText());

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static void findReferenceToMessageById(EntityManagerFactory entityManagerFactory, Long messageId) {
        System.out.println("Inside findReferenceToMessageById");
        final PersistenceUnitUtil persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            /*
                If you don’t want to hit the database when loading an entity instance, because you aren’t sure you need a
                fully initialized instance, you can tell the EntityManager to attempt the retrieval of a hollow placeholder—a proxy

                for more info see the tutorials : https://thorben-janssen.com/jpa-getreference/
             */
            Message message = entityManager.getReference(Message.class,messageId);
            // JPA offers PersistenceUnitUtil helper methods such as isLoaded() to detect
            //whether you’re working with an uninitialized proxy
            System.out.println("Message is not loaded : " + !persistenceUnitUtil.isLoaded(message));
            System.out.println("We actually get a hibernate proxy object, not our usuall Message typed object :" +message.getClass().getName());
            System.out.println("We can use however the id of the object for various things " + message.getId());
            System.out.println("Message is loaded when you try to access other fields " + message);

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static void findMessageByIdAndChangeText(final EntityManagerFactory entityManagerFactory, final Long messageId) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            Message message = entityManager.find(Message.class,messageId);
            System.out.println("Retrieved message is " + message);

            //The retrieved entity instance is in persistent state, and you can now modify it inside the unit of work
            // make sure not to have the  @Immutable annotation the Message class
            if (message != null) {
                message.setText("new text setted in findMessageByIdAndChangeText");
            }

            entityManager.getTransaction().commit();
            // message will have new text in the DB after commit

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static void printVariousStatesAnObjectIs(final EntityManagerFactory entityManagerFactory){
        // we need this object to determine the state of our object
        final PersistenceUnitUtil persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();

        EntityManager entityManager = null;
        Message message = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            message = new Message("Hello World!");

            System.out.println("Object message is in transient state " + (persistenceUnitUtil.getIdentifier(message)==null));
            entityManager.persist(message);
            System.out.println("Object message is in persistent state " + entityManager.contains(message));
            System.out.println(message);

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

        System.out.println("Object message is in detached state :" + (persistenceUnitUtil.getIdentifier(message)!=null));
    }

    private static void selectAndPrintAllMessages(final EntityManagerFactory entityManagerFactory)
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
