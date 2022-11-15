package com.testehan.hibernate.dynamicFilter;

import org.hibernate.Filter;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/*
    Using dynamic data filters, Hibernate can automatically append arbitrary SQL restrictions to queries it generates.
*/

public class Main {

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

        int studentId = insertStudentBookLaptop(entityManagerFactory);
        printBooksOfStudentsWithReputationHigherThan(studentId, 10f, entityManagerFactory);

        entityManagerFactory.close();
    }

    private static void printBooksOfStudentsWithReputationHigherThan(final int studentId, final float studentReputation, final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Inside printBooksOfStudentsWithReputationHigherThan -------------------------");
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            /*
                Now, every JPQL or criteria query that you execute on the filtered persistence context
                restricts the returned Book2 instances
             */
            Filter filter = entityManager.unwrap(Session.class).enableFilter("limitByReputation");
            filter.setParameter("currentReputation", studentReputation);
            filter.validate();  // to see if everything is ok from the filters point of view

            // 1.filter is on for the next approach
            List<Book2> books = entityManager.createQuery("select s from Book2 s").getResultList();
            for (Book2 book2 : books){
                System.out.println(book2);
            }

            // 2.filter is on for the next approach
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery criteria = cb.createQuery();
            criteria.select(criteria.from(Book2.class));
            books = entityManager.createQuery(criteria).getResultList();
            for (Book2 book2 : books){
                System.out.println(book2);
            }

            // 3. filter will not work out of the box: Hibernate doesn’t apply filters to
            //retrieval by identifier operations. One of the reasons is that data-filter conditions are
            //SQL fragments, and lookup by identifier may be resolved completely in memory, in
            //the first-level persistence context cache.

            // in order for this to work, I had to add the filter also on on the books collection from Student2
            Student2 student2 = entityManager.find(Student2.class, studentId);
            for (Book2 book2 : student2.getBooks()){
                System.out.println(book2);
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

    private static int insertStudentBookLaptop(final EntityManagerFactory entityManagerFactory)
    {
        EntityManager entityManager = null;
        int studentId = 0;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Book2 book1 = new Book2();
            book1.setBookName("HarryPotter");
            book1.setBookAuthor("JK Rowling");

            Book2 book2 = new Book2();
            book2.setBookName("Lord of the Rings");
            book2.setBookAuthor("JRR Tolkien");

            Book2 book3 = new Book2();
            book3.setBookName("Antifragile");
            book3.setBookAuthor("N Tableb");

            Book2 book4 = new Book2();
            book4.setBookName("Black swans");
            book4.setBookAuthor("N Tableb");

            Student2 student = new Student2();
            student.setName("dan");
            student.setReputation(9.99f);
            student.getBooks().add(book1);
            student.getBooks().add(book2);
            book1.setStudent(student);
            book2.setStudent(student);

            Student2 student2 = new Student2();
            student2.setName("ioana");
            student2.setReputation(15.99f);
            student2.getBooks().add(book3);
            book3.setStudent(student2);

            Student2 student3 = new Student2();
            student3.setName("ana");
            student3.setReputation(25f);
            student3.getBooks().add(book4);
            book4.setStudent(student3);

            entityManager.persist(student);
            entityManager.persist(student2);
            entityManager.persist(student3);
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
