package com.testehan.hibernate.loading.lazyAndEager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.List;

/*
    Example with Lazy and Eager loading...and some issues that might appear
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


        int studentId = insertStudentBookLaptopCourse(entityManagerFactory);
        printWhatFieldsAreLoaded(entityManagerFactory,studentId);
        youCantAccessLazyLoadedFieldsIfDetached(entityManagerFactory,studentId);

        cartesianProductProblem(entityManagerFactory);

        entityManagerFactory.close();
    }

    private static void cartesianProductProblem(EntityManagerFactory entityManagerFactory) {
        System.out.println("Starting method cartesianProductProblem ---------------------------------------");

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            // select * from Student
            List<Student1> students = entityManager.createQuery("from Student1").getResultList();
            /*
                If you also put the books field from Student to be eagerly loaded, then the app will not run and you will
                see the error : cannot simultaneously fetch multiple bags

                Hibernate doesn't allow fetching more than one bag because that would generate a Cartesian product.
                See more info on what should one do in this case here (basically one would need to load those collections
                that are set to eagerly manually by doing a query....and one should set those collections to LAZY):
                https://stackoverflow.com/questions/4334970/hibernate-throws-multiplebagfetchexception-cannot-simultaneously-fetch-multipl

             */

            for (Student1 student : students){
                System.out.println("for each student, no select is performed because the notebooks were already loaded");

                System.out.println(student.getName() + " has notebooks =  " + student.getNotebooks());
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


    private static void youCantAccessLazyLoadedFieldsIfDetached(final EntityManagerFactory entityManagerFactory, int studentId)
    {

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Student1 student = entityManager.find(Student1.class, studentId);

            PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();
            System.out.println("books field from Student isLoaded should return false :" + persistenceUtil.isLoaded(student, "books"));

            entityManager.detach(student);

            List<Book1> books = student.getBooks();
            System.out.println(books);

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("Books was not loaded initially,(we by default told the collection to be loaded lazily, and ");
            System.out.println("since we were in detached mode and now we tried to access it");
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

    }

    private static void printWhatFieldsAreLoaded(final EntityManagerFactory entityManagerFactory, int studentId)
    {

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Student1 student = entityManager.find(Student1.class, studentId);

            List<Book1> books = student.getBooks();
            PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();
            System.out.println("books field from Student isLoaded should return false :" + persistenceUtil.isLoaded(student, "books"));
            System.out.println("course field from Student isLoaded should return false :" + persistenceUtil.isLoaded(student, "course"));
            System.out.println("laptop field from Student isLoaded should return true :" + persistenceUtil.isLoaded(student, "laptop"));
            System.out.println("studentIq field from Student isLoaded should return false :" + persistenceUtil.isLoaded(student, "studentIq"));
            System.out.println("pens field from Student isLoaded should return false :" + persistenceUtil.isLoaded(student, "pens"));

            /*
                These special collections can detect when you access them and load their data at that time. As soon as
                you start iterating through the bids, the collection and all bids made for the item are loaded
            */
            System.out.println("books is not a usual list " + books.getClass());

            // see LazyCollectionOption.EXTRA in Student placed on pens ;)
            student.getBooks().size();  // will trigger select * from Book...
            System.out.println("books field after calling size -> isLoaded should return true :" + persistenceUtil.isLoaded(student, "books"));
            student.getPens().size();   // will trigger select count(*) from Student_Pen..
            System.out.println("pens field after calling size -> isLoaded should return false :" + persistenceUtil.isLoaded(student, "pens"));

            // this was eagerly loaded
            System.out.println("notebooks field from Student isLoaded should return true :" + persistenceUtil.isLoaded(student, "notebooks"));

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

    }

    private static int insertStudentBookLaptopCourse(final EntityManagerFactory entityManagerFactory)
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

            Course1 course = new Course1();
            course.setName("Data structures and Algorithms");
            course.getStudents().add(student);
            student.getCourse().add(course);

            IQ1 studIq = new IQ1();
            studIq.setValue(93);
            student.setStudentIq(studIq);

            IQ1 studIq2 = new IQ1();
            studIq2.setValue(120);
            student2.setStudentIq(studIq2);

            Pen1 pen1 = new Pen1();
            pen1.setColour("RED");
            Pen1 pen2 = new Pen1();
            pen2.setColour("YELLOW");
            student.getPens().add(pen1);
            student.getPens().add(pen2);

            Notebook1 notebook = new Notebook1();
            notebook.setName("math notebook");
            notebook.setStudent(student);
            student.getNotebooks().add(notebook);

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
