package com.testehan.hibernate.events.jpaListenerCallback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


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


        int studentId = insertStudent(entityManagerFactory);
        updateStudent(entityManagerFactory,studentId);
        deleteStudent(entityManagerFactory,studentId);
        entityManagerFactory.close();
    }

    private static void deleteStudent(final EntityManagerFactory entityManagerFactory, final int studentId)
    {
        System.out.println("Inside deleteStudent ------------------");

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Student student = entityManager.find(Student.class,studentId);
            entityManager.remove(student);

            entityManager.getTransaction().commit();


        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static void updateStudent(final EntityManagerFactory entityManagerFactory, final int studentId)
    {
        System.out.println("Inside modifyStudent ------------------");

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Student student = entityManager.find(Student.class,studentId);
            student.setMark(0);


            System.out.println("Method @PreUpdate  will be called ");
            entityManager.getTransaction().commit();
            System.out.println("Method @PostUpdate  was called ");

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static int insertStudent(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Inside insertStudent ------------------");
        EntityManager entityManager = null;
        int studentId = 0;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Student student = new Student();
            student.setName("dan");
            student.setMark(7);

            Student student2 = new Student();
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
