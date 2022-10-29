package com.testehan.hibernate.basics.first3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting the Hibernate App");

        Configuration conf = new Configuration().configure().addAnnotatedClass(Alien.class);
        SessionFactory sessionFactory = conf.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // clean up Alien's class table
        Transaction tx = session.beginTransaction();
        session.createQuery("delete from Alien").executeUpdate();
        tx.commit();

        tx = session.beginTransaction();

        Alien alien = new Alien();
        alien.setColour("green");
        alien.setWeightKg(100);

        long generatedId = (Long)session.save(alien);
        tx.commit();

        tx = session.beginTransaction();
        Thread.sleep(5000);
        alien.setCreateDate(new Date()); // column will not update because i've set updatable = false; setter exists for demo purposes
        alien.setModifyDate(new Date());

        tx.commit();

        Alien readFromDB = session.get(Alien.class,generatedId);
        System.out.println("Reading alien from the DB " + readFromDB);

        session.close();

        sessionFactory.close();
    }

}
