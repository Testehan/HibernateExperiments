package com.testehan.hibernate.basics.mappings.collections.setOfComponents;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {
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
        alien.addImage(new Image("thanos","thanos.jpg",100,180));
        alien.addImage(new Image("thanos with gauntlet","gauntlet.jpg",100,180));
        alien.addImage(new Image("thanos","thanos.jpg",100,180)); // images is a set, so this will not be added

        long generatedId = (Long)session.save(alien);
        tx.commit();

        Alien readFromDB = session.get(Alien.class,generatedId);
        System.out.println("Reading alien from the DB " + readFromDB);

        session.close();

        sessionFactory.close();
    }

}
