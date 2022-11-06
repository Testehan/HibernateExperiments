package com.testehan.hibernate.basics.mappings.relationships.manyToMany.noJoinTableClass;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration().configure()
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class);
        SessionFactory sf = conf.buildSessionFactory();


        insertStudentCourse(sf);

        try (Session session = sf.openSession()) {
            session.beginTransaction();

            Student student = session.get(Student.class, 1);
            System.out.println(student.getName());
            System.out.println(student.getCourse());

            session.getTransaction().commit();

        }
        sf.close();

    }

    private static void insertStudentCourse(SessionFactory sf ) {
        try (Session session = sf.openSession()) {
            Student student = new Student();
            student.setStudentId(1);
            student.setName("dan");

            Course course = new Course();
            course.setCourseId(101);
            course.setName("Data structures and Algorithms");
            course.getStudents().add(student);
            student.getCourse().add(course);

            session.beginTransaction();

            session.save(student);
            session.save(course);


            session.getTransaction().commit();

        }
    }
}
