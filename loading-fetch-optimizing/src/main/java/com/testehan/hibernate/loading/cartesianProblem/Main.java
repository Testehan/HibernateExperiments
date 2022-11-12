package com.testehan.hibernate.loading.cartesianProblem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


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
        cartesianProductProblem(entityManagerFactory);
        cartesianProductFIX(entityManagerFactory);

        entityManagerFactory.close();
    }

    private static void cartesianProductFIX(EntityManagerFactory entityManagerFactory) {
        System.out.println("Starting method cartesianProductFIX ---------------------------------------");

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            // select * from Student
            List<Student3> students = entityManager.createQuery("from Student3").getResultList();

            // BOOKS were loaded with 2 selects, one for each student, because of org.hibernate.annotations.Fetch
            // Notebooks were loaded with a join
            for (Student3 student : students){
                System.out.println("for each student, no select is performed because the BOOKS were already loaded");

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

    private static void cartesianProductProblem(EntityManagerFactory entityManagerFactory) {
        System.out.println("Starting method cartesianProductProblem ---------------------------------------");

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            // select * from Student
            List<Student3> students = entityManager.createQuery("from Student3").getResultList();
            /*
                If you also put the books field from Student to be eagerly loaded, then the app will not run and you will
                see the error : cannot simultaneously fetch multiple bags

                Hibernate doesn't allow fetching more than one bag because that would generate a Cartesian product.
                See more info on what should one do in this case here (basically one would need to load those collections
                that are set to eagerly manually by doing a query....and one should set those collections to LAZY):
                https://stackoverflow.com/questions/4334970/hibernate-throws-multiplebagfetchexception-cannot-simultaneously-fetch-multipl

             */

            for (Student3 student : students){
                System.out.println("for each student, no select is performed because the notebooks were already loaded");

                System.out.println(student.getName() + " has notebooks =  " + student.getNotebooks());
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


    private static int insertStudentBookLaptopCourse(final EntityManagerFactory entityManagerFactory)
    {

        EntityManager entityManager = null;
        int studentId = 0;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Book3 book1 = new Book3();
            book1.setBookName("HarryPotter");
            book1.setBookAuthor("JK Rowling");

            Student3 student = new Student3();
            student.setName("dan");
            student.setMark(7);
            student.getBooks().add(book1);
            book1.setStudent(student);

            Student3 student2 = new Student3();
            student2.setName("ioana");
            student2.setMark(7);


            Notebook3 notebook = new Notebook3();
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
