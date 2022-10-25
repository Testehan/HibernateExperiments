package com.testehan.hibernate.caching.level2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class MainQuery {

    // this example shows the Level 2 cache where entities are obtained with queries
    // in order for the caching to work in this case, we need to set a new property with true
    // hibernate.cache.use_query_cache + setCacheable is set on the Query objects;
    // otherwise the DB will be hit for every query execution
    public static void main(String[] args) {
        System.out.println("Starting the Hibernate App");
        Alien alien = null;

        Configuration conf = new Configuration().configure().addAnnotatedClass(Alien.class);
        SessionFactory sessionFactory = conf.buildSessionFactory();
        Session session1 = sessionFactory.openSession();

        Transaction tx = session1.beginTransaction();

        Query query1 = session1.createQuery("from Alien where alienId=2");
        query1.setCacheable(true);
        alien = (Alien)query1.uniqueResult();

        System.out.println("DB is hit with a select query");
        System.out.println(alien);

        alien = (Alien)query1.uniqueResult();
        System.out.println("DB is not hit with a select, because object is retrieved from cache");
        System.out.println(alien);

        tx.commit();
        session1.close();

        Session session2 = sessionFactory.openSession();
        tx = session2.beginTransaction();

        Query query2 = session2.createQuery("from Alien where alienId=2");
        query2.setCacheable(true);
        alien = (Alien)query2.uniqueResult();

        System.out.println("We are using a different session but second level caching is enabled + hibernate.cache.use_query_cache + setCacheable, DB will NOT be hit with a new select query1");
        System.out.println(alien);


        tx.commit();
        session2.close();


    }
}
