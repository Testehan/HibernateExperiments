package com.testehan.hibernate.audit;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditQuery;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

/*

*/

public class Main {

    private static final Date TIMESTAMP_CREATE = new Date(1668266065420l);
    private static final Date TIMESTAMP_UPDATE = new Date(1668266065467l);
    private static final Date TIMESTAMP_DELETE = new Date(1668266065485l);

    public static void main(String[] args)
    {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();


            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }


        int studentId = 1;
        //insertStudentBookLaptop(entityManagerFactory);
//        updateStudent(entityManagerFactory,studentId);
//        deleteStudent(entityManagerFactory,studentId);
        findingRevisions(entityManagerFactory,studentId);
        rollBackToAPreviousVersion(entityManagerFactory,studentId);

        entityManagerFactory.close();
    }

    private static void rollBackToAPreviousVersion(EntityManagerFactory entityManagerFactory, int studentId) {
        System.out.println("Inside rollBackToAPreviousVersion ------------------------");

        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            AuditReader auditReader = AuditReaderFactory.get(entityManager);

            Number revisionCreate = auditReader.getRevisionNumberForDate(TIMESTAMP_CREATE);
            Student1 user = auditReader.find(Student1.class, studentId, revisionCreate);
//            entityManager.unwrap(Session.class).replicate(user, ReplicationMode.OVERWRITE);
//            book example does now work..

            // this will add user and books to the DB...Not sure if it is correct, and couldn't find another example
            // online of such a retrieval. Good enough for now ;)
            entityManager.merge(user);

            entityManager.flush();
            entityManager.clear();

            user = entityManager.find(Student1.class, studentId);
            System.out.println("Reverted to " + user);

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static void findingRevisions(EntityManagerFactory entityManagerFactory, int studentId) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            AuditReader auditReader = AuditReaderFactory.get(entityManager);

            // Method 1. : Given a timestamp, you can find the revision number of a change set made before or
            //on that timestamp
            Number revisionCreate = auditReader.getRevisionNumberForDate(TIMESTAMP_CREATE);
            Number revisionUpdate = auditReader.getRevisionNumberForDate(TIMESTAMP_UPDATE);
            Number revisionDelete = auditReader.getRevisionNumberForDate(TIMESTAMP_DELETE);

            // Method 2. If you don’t have a timestamp, you can get all revision numbers in which a particular
            //audited entity instance was involved. This operation finds all change sets where the
            //given Item was created, modified, or deleted. In our example, we created, modified,
            //and then deleted the Item. Hence, we have three revisions.
            List<Number> itemRevisions = auditReader.getRevisions(Student1.class, studentId);
            System.out.println("Number of revisions is " + itemRevisions.size());  //3
            for (Number number : itemRevisions){
                System.out.println(number);
            }

            // Method 3
            // If you don’t know modification timestamps or revision numbers, you can write a query
            //with forRevisionsOfEntity() to obtain all audit trail details of a particular entity.
            AuditQuery query = auditReader.createQuery()
                    .forRevisionsOfEntity(Student1.class, false, false);
            List<Object[]> result = query.getResultList();
            for (Object[] tuple : result) {
                Student1 item = (Student1) tuple[0];
                DefaultRevisionEntity revision = (DefaultRevisionEntity)tuple[1];
                RevisionType revisionType = (RevisionType)tuple[2];
                if (revision.getId() == 5) {
                    System.out.println(revisionType); //, RevisionType.ADD
                    System.out.println(item.getName()); //"ioana"  followed by "dan"
                } else if (revision.getId() == 6) {
                    System.out.println(revisionType);   //, RevisionType.MOD);
                    System.out.println(item.getName()); // , "updated !!!");
                } else if (revision.getId() == 7) {
                    System.out.println(revisionType); //, RevisionType.DEL);
                    System.out.println(item);  //null
                }
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

    private static void deleteStudent(EntityManagerFactory entityManagerFactory, int studentId) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();


            Student1 student1 = entityManager.find(Student1.class,studentId);
            entityManager.remove(student1);

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

    }

    private static int updateStudent(final EntityManagerFactory entityManagerFactory, final int studentId)
    {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();


            Student1 student1 = entityManager.find(Student1.class,studentId);
            student1.setName("updated !!!");

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

        return studentId;
    }

    private static int insertStudentBookLaptop(final EntityManagerFactory entityManagerFactory)
    {

        EntityManager entityManager = null;
        int studentId = 0;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Laptop1 laptop = new Laptop1();
            laptop.setLaptopName("dell");

            Book1 book1 = new Book1();
            book1.setBookName("HarryPotter");
            book1.setBookAuthor("JK Rowling");

            Student1 student = new Student1();
            student.setName("dan");
            student.setMark(7);
            student.setLaptop(laptop);
            student.getBooks().add(book1);
            book1.setStudent(student);

            Student1 student2 = new Student1();
            student2.setName("ioana");
            student2.setMark(7);


            entityManager.persist(student);
            entityManager.persist(student2);
            studentId = student.getStudentId();

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

        return studentId;
    }
}
