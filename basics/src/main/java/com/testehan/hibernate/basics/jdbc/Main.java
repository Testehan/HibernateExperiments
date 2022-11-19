package com.testehan.hibernate.basics.jdbc;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/*
    How to make jdbc calls by using Hibernate infrastructure
*/

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting the Hibernate App");

        Configuration conf = new Configuration().configure().addAnnotatedClass(Alien.class);
        SessionFactory sessionFactory = conf.buildSessionFactory();

        Long alienId = insertAlienData(sessionFactory);


        mapJDBCQueryDataToEntityClass(sessionFactory, alienId);
        useWorkInterface(sessionFactory, alienId);
        useReturnWorkInterface(sessionFactory, alienId);

        sessionFactory.close();
    }

    private static void mapJDBCQueryDataToEntityClass(SessionFactory sessionFactory, Long alienId) {
        System.out.println("Inside mapJDBCQueryDataToEntityClass -------------");
        try (Session session = sessionFactory.openSession()) {

            Transaction tx = session.beginTransaction();

            SQLQuery query = session.createSQLQuery("select * from Alien");
            query.addEntity(Alien.class);

            // The returned Alien instances are in persistent state, managed by the current persistence
            // context. The result is therefore the same as with the JPQL query select i from Item i.
            List<Alien> aliens = query.list();
            for (Alien a : aliens){
                System.out.println(a);
            }

            tx.commit();
        }
    }

    private static void useReturnWorkInterface(SessionFactory sessionFactory, Long alienId) {
        System.out.println("Inside useReturnWorkInterface -------------");
        QueryAlienReturnWork alienReturnWork = new QueryAlienReturnWork(alienId);
        try (Session session = sessionFactory.openSession()) {
            Alien a = (Alien) session.doReturningWork(alienReturnWork);
            System.out.println("Alien obtained from the doReturningWork method");
            System.out.println(a);
        }
    }

    private static void useWorkInterface(SessionFactory sessionFactory, Long alienId) {
        System.out.println("Inside useWorkInterface -------------");
        QueryAlienWork alienWork = new QueryAlienWork(alienId);
        try (Session session = sessionFactory.openSession()) {
            session.doWork(alienWork);
        }
    }

    private static Long insertAlienData(SessionFactory sessionFactory) {
        Long aliendId = null;
        try (Session session = sessionFactory.openSession()) {

            // clean up Alien's class table
            Transaction tx = session.beginTransaction();
            session.createQuery("delete from Alien").executeUpdate();
            tx.commit();

            tx = session.beginTransaction();

            Alien alien = new Alien();
            alien.setName("Thanos");

            aliendId = (Long) session.save(alien);
            tx.commit();
        }
        return aliendId;
    }
}
