package com.testehan.hibernate.caching.level1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

    // this example shows the Level 1 Caching that hibernate offers. It works as expected as long as the Level 2 caching
    // is not configured
    public static void main(String[] args) {
        System.out.println("Starting the Hibernate App");
        Alien alien = null;

        Configuration conf = new Configuration().configure().addAnnotatedClass(Alien.class);
        SessionFactory sf = conf.buildSessionFactory();
        Session session1 = sf.openSession();

        Transaction tx = session1.beginTransaction();

        alien = session1.get(Alien.class, 2);
        System.out.println("DB is hit with a select query");
        System.out.println(alien);

        alien = session1.get(Alien.class, 2);
        System.out.println("DB is not hit with a select, because object is retrieved from Level 1 hibernate cache");
        System.out.println(alien);

        tx.commit();
        session1.close();

        Session session2 = sf.openSession();
        tx = session2.beginTransaction();

        alien = session2.get(Alien.class, 2);
        System.out.println("Since we are using a different session, DB will be hit with a new select query");
        System.out.println(alien);

        tx.commit();
        session2.close();

        sf.close();
    }
}
