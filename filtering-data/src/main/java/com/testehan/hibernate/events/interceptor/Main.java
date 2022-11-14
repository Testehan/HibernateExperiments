package com.testehan.hibernate.events.interceptor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionImplementor;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*

Let’s assume that you want to write an audit log of data modifications in a separate
database table. For example, you may record information about creation and update
events for each Book. The audit log includes the user, the date and time of the event,
what type of event occurred, and the identifier of the Book that was changed.
Audit logs are often handled using database triggers. On the other hand, it’s sometimes better for the
application to take responsibility, especially if portability between
different databases is required.

*/

public class Main {

    public static void main(String[] args)
    {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");

        Long bookId = insertBook(entityManagerFactory);
        updateBook(entityManagerFactory,bookId);

        entityManagerFactory.close();
    }

    private static void updateBook(final EntityManagerFactory entityManagerFactory, final Long bookId)
    {
        System.out.println("Inside updateBook ------------------");
        SessionFactory sessionFactory = entityManagerFactory.unwrap( SessionFactory.class );
        try (Session session = sessionFactory
                     .withOptions()
                     .interceptor(new AuditLogInterceptor())
                     .openSession())
        {
            AuditLogInterceptor interceptor =
                    (AuditLogInterceptor) ((SessionImplementor) session).getInterceptor();
            interceptor.setCurrentSession(session);
            interceptor.setCurrentUsername("DanTe");

            session.getTransaction().begin();

            Book book = session.find(Book.class,bookId);
            book.setBookAuthor("The Joker");

            session.getTransaction().commit();

        }
    }

    private static Long insertBook(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Inside insertBook ------------------");

        Long bookId = 0l;
        SessionFactory sessionFactory = entityManagerFactory.unwrap( SessionFactory.class );
        try (Session session = sessionFactory.withOptions().interceptor(new AuditLogInterceptor())
                     .openSession())
        {

            AuditLogInterceptor interceptor =
                    (AuditLogInterceptor) ((SessionImplementor) session).getInterceptor();
            interceptor.setCurrentSession(session);
            interceptor.setCurrentUsername("DanTe");

            session.getTransaction().begin();

            Book book = new Book();
            book.setBookName("Harry Potter");
            book.setBookAuthor("JK Rowling");

            Book book2 = new Book();
            book2.setBookName("Lord of the rings : Fellowship of the ring");
            book2.setBookAuthor("JRR Tolkien");

            session.persist(book);
            session.persist(book2);
            bookId = book.getId();

            session.getTransaction().commit();

        }

        return bookId;
    }

}
