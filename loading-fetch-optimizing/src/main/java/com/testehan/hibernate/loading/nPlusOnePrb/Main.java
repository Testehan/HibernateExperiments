package com.testehan.hibernate.loading.nPlusOnePrb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/*

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


        int studentId = insertStudentData(entityManagerFactory);

        nPlus1SelectsProblem(entityManagerFactory);
        nPlus1SelectsFIX(entityManagerFactory);
        nPlus1SelectsFIX2(entityManagerFactory);

        entityManagerFactory.close();
    }

    private static void nPlus1SelectsFIX2(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Starting method nPlus1SelectsFIX ---------------------------------------");

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            // select * from Student
            List<Student2> students = entityManager.createQuery("from Student2").getResultList();
            System.out.println("Lets try 2nd solution, by using a subselect to get books");
            // if you check the logs you will see 1 subselect and 1 query
            for (Student2 student : students){
                System.out.println(student.getName() + " has books =  " + student.getBooks());
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

    private static void nPlus1SelectsFIX(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Starting method nPlus1SelectsFIX ---------------------------------------");

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            // select * from Student
            List<Student2> students = entityManager.createQuery("from Student2").getResultList();
            System.out.println("Lets try one solution, by prefatching data in batches. Only 1 select will be performed");
            // if we would of had more than 10 students, 2 selects would have been performed see @BatchSize(size = 10)
            // on Laptop2 class
            for (Student2 student : students){
                System.out.println(student.getName() + " has IQ =  " + student.getLaptop().getLaptopName());
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

    private static void nPlus1SelectsProblem(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Starting method nPlus1SelectsProblem ---------------------------------------");

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            // select * from Student
            List<Student2> students = entityManager.createQuery("from Student2").getResultList();
            for (Student2 student : students){
                System.out.println("for each student, 1 select is performed to get the IQ entity");
                /*
                    This amounts to one query for the Item plus n queries depending on how many items you
                    have and whether a particular User is selling more than one Item. Obviously, this is a
                    very inefficient strategy if you know you’ll access the seller of each Item.
                    If you have 100 items, you’ll execute 101 SQL queries!
                 */
                System.out.println(student.getName() + " has IQ =  " + student.getStudentIq().getValue());
            }

            System.out.println("Lets try one solution, by prefatching data in batches");

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

    }

    private static int insertStudentData(final EntityManagerFactory entityManagerFactory)
    {

        EntityManager entityManager = null;
        int studentId = 0;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();


            Student2 student = new Student2();
            student.setName("dan");
            student.setMark(7);

            Student2 student2 = new Student2();
            student2.setName("ioana");
            student2.setMark(7);

            IQ2 studIq = new IQ2();
            studIq.setValue(93);
            student.setStudentIq(studIq);

            IQ2 studIq2 = new IQ2();
            studIq2.setValue(120);
            student2.setStudentIq(studIq2);

            Laptop2 laptop = new Laptop2();
            laptop.setLaptopName("macbook");
            Laptop2 laptop2 = new Laptop2();
            laptop2.setLaptopName("HP");
            student.setLaptop(laptop);
            student2.setLaptop(laptop2);

            Book2 book = new Book2();
            book.setBookName("HarryPotter");
            book.setBookAuthor("JK Rowling");
            book.setStudent(student);

            Book2 book2 = new Book2();
            book2.setBookName("HarryPotter Chamber of secrets");
            book2.setBookAuthor("JK Rowling");
            book2.setStudent(student);

            Book2 book3 = new Book2();
            book3.setBookName("Lord of the rings");
            book3.setBookAuthor("JRR Tolkien");
            book3.setStudent(student2);

            student.getBooks().add(book);
            student.getBooks().add(book2);
            student2.getBooks().add(book3);

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
