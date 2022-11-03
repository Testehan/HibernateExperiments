package com.testehan.hibernate.basics.mappings.collections.bag;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
/*
A bag is an unordered collection that allows duplicate elements, like the
java.util.Collection interface. Curiously, the Java Collections framework doesn’t
include a bag implementation. You initialize the property with an ArrayList, and
Hibernate ignores the index of elements when storing and loading elements.
 */

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
        alien.addImage("et.jpg");
        alien.addImage("et.jpg");       // 2 images will be present in the DB, as images is a Collection

        long generatedId = (Long)session.save(alien);
        tx.commit();

        Alien readFromDB = session.get(Alien.class,generatedId);
        System.out.println("Reading alien from the DB " + readFromDB);

        session.close();

        sessionFactory.close();
    }

}
