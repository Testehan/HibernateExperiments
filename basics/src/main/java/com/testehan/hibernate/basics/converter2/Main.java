package com.testehan.hibernate.basics.converter2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting the Hibernate App");

        Configuration conf = new Configuration().configure().addAnnotatedClass(Alien.class).addAnnotatedClass(ZipcodeConverter.class);
        SessionFactory sessionFactory = conf.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // clean up Alien's class table
        Transaction tx = session.beginTransaction();
        session.createQuery("delete from Alien").executeUpdate();
        tx.commit();

        tx = session.beginTransaction();

        Alien alien = new Alien();
        Address address = new Address();
        address.setZipcode(new GermanZipcode("12345"));
        alien.setAddress(address);

        UUID generatedId = (UUID)session.save(alien);
        tx.commit();

        Alien readFromDB = session.get(Alien.class,generatedId);
        System.out.println("Reading alien from the DB " + readFromDB);

        session.close();

        sessionFactory.close();
    }
}
