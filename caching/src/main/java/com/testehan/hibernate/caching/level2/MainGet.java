package com.testehan.hibernate.caching.level2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MainGet {

    // this example shows the Level 2 (meaning for sessions obtained from the same sessionFactory)
    // with "get" methods
    public static void main(String[] args) {
        System.out.println("Starting the Hibernate App");
        Alien alien = null;

        Configuration conf = new Configuration().configure().addAnnotatedClass(Alien.class);
        SessionFactory sessionFactory = conf.buildSessionFactory();
        Session session1 = sessionFactory.openSession();

        Transaction tx = session1.beginTransaction();

        alien = session1.get(Alien.class, 2);
        System.out.println("DB is hit with a select query");
        System.out.println(alien);

        alien = session1.get(Alien.class, 2);
        System.out.println("DB is not hit with a select, because object is retrieved from Level 1 hibernate cache");
        System.out.println(alien);

        tx.commit();
        session1.close();

        Session session2 = sessionFactory.openSession();
        tx = session2.beginTransaction();

        alien = session2.get(Alien.class, 2);
        System.out.println("We are using a different session but second level caching is enabled, DB will NOT be hit with a new select query");
        System.out.println(alien);

        tx.commit();
        session2.close();
        sessionFactory.close();

    }
}
