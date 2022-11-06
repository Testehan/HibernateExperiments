package com.testehan.hibernate.basics.mappings.relationships.manyToMany.intermediaryEntity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
    You may always represent a many-to-many association as two many-to-one associations to
    an intervening class. You don’t hide the link table but represent it with a Java class.
    This model is usually more easily extensible, so we tend not to use regular many-to-many
    associations in applications. It’s a lot of work to change code later, when inevitably
    more columns are added to a link table; so before you map an @ManyToMany as shown in the noJoinTableClass
    package example, consider this alternative

    To create a link, you instantiate and persist a CourseStudent. If you want to break a link, you remove
    the CourseStudent. The constructor of CourseStudent requires that you provide already persistent
    Couse and Student instances

     If bidirectional navigation is required, map an @OneToMany collection in Course and/or Student:
        with the @OneToMany and also add in the constructor from CourseStudent :
            "course.getCourseStudents().add(this);"
 */

public class Main {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration().configure()
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(CourseStudent.class);
        SessionFactory sf = conf.buildSessionFactory();


        insertStudentCourse(sf);

        try (Session session = sf.openSession()) {
            session.beginTransaction();

            Student student = session.get(Student.class, 1);
            System.out.println(student);

            session.getTransaction().commit();

        }
        sf.close();

    }

    private static void insertStudentCourse(SessionFactory sf ) {
        try (Session session = sf.openSession()) {
            Student student = new Student();
            student.setName("dan");

            Course course = new Course();
            course.setName("Data structures and Algorithms");

            session.beginTransaction();

            session.save(student);
            session.save(course);

            CourseStudent courseStudent = new CourseStudent(course,student);
            session.save(courseStudent);

            session.getTransaction().commit();

        }
    }
}
