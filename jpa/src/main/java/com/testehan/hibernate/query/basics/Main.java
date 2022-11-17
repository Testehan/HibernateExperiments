package com.testehan.hibernate.query.basics;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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


        int studentId = insertStudent(entityManagerFactory);
        Laptop laptop = addLaptopToStudent(entityManagerFactory,studentId);

        selectStudentInfoInDTOObjects(entityManagerFactory);                    // 8
//        selectingStudentWithIdUsingProgramaticallyCreatedQuery(entityManagerFactory, studentId); // 7
//        selectingStudentUsingNamedQuery(entityManagerFactory,studentId);        // 6
//        selectingAllStudentsUsingHibernateNamedQueries(entityManagerFactory);   // 5
//        selectStudentsWithPagination(entityManagerFactory);                    // 4
//        selectStudentNameOfStudentWithId(entityManagerFactory,studentId);      // 3
//        selectStudentThatOwnsLaptop(entityManagerFactory,laptop);              // 2
//        selectingStudentNameWithQuery(entityManagerFactory);                   // 1

        entityManagerFactory.close();
    }

    private static void selectStudentInfoInDTOObjects(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Inside selectStudentInfoInDTOObjects ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            System.out.println("Printing student DTO objects where their mark is greater than or equal to 8: ");

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<StudentSummaryDTO> criteria = criteriaBuilder.createQuery(StudentSummaryDTO.class);
            Root<Student> s = criteria.from(Student.class);
            criteria
                    .select(criteriaBuilder.construct(
                        StudentSummaryDTO.class,
                        s.get("name"), s.get("mark")))
                    .where(criteriaBuilder.greaterThanOrEqualTo(s.get("mark"), 8));

            TypedQuery<StudentSummaryDTO> query = entityManager.createQuery(criteria);
            List<StudentSummaryDTO> results = query.getResultList();

            for (StudentSummaryDTO dto : results){
                System.out.println(dto);
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

    private static void selectingStudentWithIdUsingProgramaticallyCreatedQuery(final EntityManagerFactory entityManagerFactory, final int studentId)
    {
        System.out.println("Inside selectingStudentWithIdUsingProgramaticallyCreatedQuery ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();


            TypedQuery<Student> select = entityManager.createQuery("select s from Student s where s.studentId = :id", Student.class);
            // This registers your query with the persistence unit, the EntityManagerFactory, and make it reusable as a
            // named query.
            entityManager.getEntityManagerFactory().addNamedQuery("savedFindStudentByIdQuery", select);


            TypedQuery<Student> selectObtainedByName = entityManager.createNamedQuery("savedFindStudentByIdQuery",Student.class);
            selectObtainedByName.setParameter("id",studentId);

            Student students = selectObtainedByName.getSingleResult();
            System.out.println(students);


            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

    }

    private static void selectingAllStudentsUsingHibernateNamedQueries(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Inside selectingAllStudentsUsingHibernateNamedQueries ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            System.out.println("Printing student obtained from named query execution: ");

            TypedQuery<Student> select = entityManager.createNamedQuery("findStudentsOrderByMark", Student.class)
                    .setHint(org.hibernate.annotations.QueryHints.COMMENT,"Custom SQL comment written by Dan");
                    // THIS hint appears in the console...could be helpful for various debugging purposes

            List<Student> students = select.getResultList();
            for (Student s : students){
                System.out.println(s);
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

    private static void selectingStudentUsingNamedQuery(final EntityManagerFactory entityManagerFactory, final int studentId)
    {
        System.out.println("Inside selectingStudentUsingNamedQuery ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            System.out.println("Printing student obtained from named query execution: ");

            TypedQuery<Student> select = entityManager.createNamedQuery("findStudentById", Student.class);
            select.setParameter("id",studentId);
            Student student = select.getSingleResult();
            System.out.println(student);

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static void selectStudentsWithPagination(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Inside selectStudentsWithPagination ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            System.out.println("Printing student with pagination: ");

            TypedQuery<Student> select = entityManager.createQuery("select s from Student s order by studentid", Student.class);
            select.setFirstResult(10).setMaxResults(6);
            List<Student> students = select.getResultList();
            for (Student s : students){
                System.out.println(s);
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

    private static void selectStudentThatOwnsLaptop(final EntityManagerFactory entityManagerFactory, final Laptop laptop) {
        System.out.println("Inside selectStudentThatOwnsLaptop ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            System.out.println("Printing student that owns laptop: ");
            // !! interesting thing here is that we provide an entity to the setParameter method (not the usual string or int)

            // Method 2
            TypedQuery<Student> select2 = entityManager.createQuery("select s from Student s where s.laptop = :laptop", Student.class);
            select2.setParameter("laptop",laptop);
            System.out.println(select2.getSingleResult());


            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static void selectStudentNameOfStudentWithId(final EntityManagerFactory entityManagerFactory, final int studentId) {
        System.out.println("Inside selectinStudentNameOfStudentWithId ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            System.out.println("Printing student name: ");

            // Method 1
            Query select = entityManager.createQuery("select s.name from Student s where s.studentId = :id");
            select.setParameter("id",studentId);
            String studentName = (String) select.getSingleResult();
            System.out.println(studentName);

            /*
                Our recommendation is to avoid positional parameters. They may be more convenient if you build complex
                queries programmatically, but the CriteriaQuery API is a much better alternative for that purpose.
             */
            Query select3 = entityManager.createQuery("select s.name from Student s where s.studentId = ?1");
            select3.setParameter(1,studentId);
            studentName = (String) select3.getSingleResult();
            System.out.println(studentName);

            // Method 2
            TypedQuery<String> select2 = entityManager.createQuery("select s.name from Student s where s.studentId = :id",String.class);
            select2.setParameter("id",studentId);
            studentName = select2.getSingleResult();
            System.out.println(studentName);

            // Method 3 - let's print the entire student
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Student> criteria = criteriaBuilder.createQuery(Student.class);
            Root<Student> s = criteria.from(Student.class);
            criteria.select(s).where(criteriaBuilder.equal(s.get("studentId"), studentId));
            TypedQuery<Student> query = entityManager.createQuery(criteria);
            Student result = query.getSingleResult();
            System.out.println(result);

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static void selectingStudentNameWithQuery(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Inside selectingStudentNameWithQuery ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            System.out.println("Printing student names: ");
            Query select = entityManager.createQuery("select s.name from Student s");
            List<String> students = select.getResultList();
            for (String name : students){
                System.out.println(name);
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

    private static Laptop addLaptopToStudent(EntityManagerFactory entityManagerFactory, int studentId) {
        System.out.println("Inside addLaptopToStudent ------------------");
        EntityManager entityManager = null;
        Laptop laptop = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            laptop = new Laptop();
            laptop.setLaptopName("MacBook PRO");
            Student student = entityManager.find(Student.class,studentId);
            student.setLaptop(laptop);

            entityManager.persist(student);

            studentId = student.getStudentId();

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

        return laptop;
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

            for (int i = 0 ; i<50; i++){
                Student student3 = new Student();
                student3.setName("ioana " + i);
                student3.setMark(i);
                entityManager.persist(student3);
            }


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
