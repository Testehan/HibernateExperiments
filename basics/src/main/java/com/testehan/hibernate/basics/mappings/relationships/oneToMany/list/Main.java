package com.testehan.hibernate.basics.mappings.relationships.oneToMany.list;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
    This is a unidirectional mapping: there is no other “mapped by” side. The Book
    doesn’t have a @ManyToOne property. The new annotation @OrderColumn is required for persistent list indexes,
    where, as usual, you should make the column NOT NULL


    After that we made this mapping bidirectional, with a @ManyToOne property on the Book entity
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

            session.beginTransaction();

            session.persist(student);       // by using persist instead of save, the  cascade = CascadeType.PERSIST works
//        session.save(book1);          (book is persisted without saving the object explicitly on this line)


            session.getTransaction().commit();

        }
    }
}
