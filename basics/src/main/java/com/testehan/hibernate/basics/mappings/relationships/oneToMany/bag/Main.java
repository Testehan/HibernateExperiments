package com.testehan.hibernate.basics.mappings.relationships.oneToMany.bag;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
    Bags have the most efficient performance characteristics of all the collections you
    can use for a bidirectional one-to-many entity association. By default, collections in
    Hibernate are loaded when they’re accessed for the first time in the application.
    Because a bag doesn’t have to maintain the index of its elements (like a list) or check
    for duplicate elements (like a set), you can add new elements to the bag without triggering the loading.
    This is an important feature if you’re going to map a possibly large collection of entity references
 */

public class Main {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration().configure()
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Book.class);
        SessionFactory sf = conf.buildSessionFactory();

        insertStudentBookLaptopCourse(sf);

        Session session = sf.openSession() ;
        session.beginTransaction();

        Student student = session.get(Student.class,1);
        System.out.println(student.getName());
        System.out.println(student.getBooks());

        session.getTransaction().commit();

        session.close();
        sf.close();

    }

    private static void insertStudentBookLaptopCourse(SessionFactory sf) {
        try (Session session = sf.openSession()) {

            Book book1 = new Book();
            book1.setBookName("HarryPotter");
            book1.setBookAuthor("JK Rowling");

            Student student = new Student();
            student.setName("dan");
            student.getBooks().add(book1);
            book1.setStudent(student);

            session.beginTransaction();

            session.persist(student);       // by using persist instead of save, the  cascade = CascadeType.PERSIST works
//        session.save(book1);          (book is persisted without saving the object explicitly on this line)


            session.getTransaction().commit();

        }
    }
}
