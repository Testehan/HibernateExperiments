package com.testehan.hibernate.basics.mappings.relationships.manyToMany.map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
     Here we show a representation of the relationship with a Map, instead of an additional Java class.
     (bare in mind that in this example we have a relationship between 3 parties : Students, Courses and Teachers)
 */

public class Main {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration().configure()
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Teacher.class);
        SessionFactory sf = conf.buildSessionFactory();


        insertStudentCourseTeacher(sf);

        try (Session session = sf.openSession()) {
            session.beginTransaction();

            Student student = session.get(Student.class, 1);
            System.out.println(student);

            session.getTransaction().commit();

        }
        sf.close();

    }

    private static void insertStudentCourseTeacher(SessionFactory sf ) {
        try (Session session = sf.openSession()) {
            Student student = new Student();
            student.setName("dan");
            Student student2 = new Student();
            student2.setName("Julia");

            Teacher teacher = new Teacher();
            teacher.setName("Mrs Wolf");

            Course course = new Course();
            course.setName("Data structures and Algorithms");

            session.beginTransaction();

            session.save(student);
            session.save(student2);
            session.save(course);
            session.save(teacher);

            course.getStudentsAddedBy().put(student,teacher);
            course.getStudentsAddedBy().put(student2,teacher);


            session.getTransaction().commit();

        }
    }
}
