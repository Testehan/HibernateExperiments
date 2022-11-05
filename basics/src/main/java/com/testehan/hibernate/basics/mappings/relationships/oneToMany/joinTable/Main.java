package com.testehan.hibernate.basics.mappings.relationships.oneToMany.joinTable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
    In the current case, you have a one-to-many multiplicity, so only the BOOK_ID primary key column has to
    be unique: only one Student can buy any given Book, once. The BUYER_ID column isn’t unique because a Student
    can buy many Books.

    This is now a clean, optional one-to-many/many-to-one relationship. If a Book hasn’t been bought, there is
    no corresponding row in the join table BOOK_BUYER. You don’t have any problematic nullable columns in your
    schema.

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

        Student student = session.get(Student.class,2);
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
//            student.getBooks().add(book1);
            book1.setBuyer(student);

            session.beginTransaction();

            session.save(book1);
            session.persist(student);



            session.getTransaction().commit();

        }
    }
}
