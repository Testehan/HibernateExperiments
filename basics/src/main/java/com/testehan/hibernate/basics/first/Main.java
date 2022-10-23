package com.testehan.hibernate.basics.first;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting the Hibernate App");

        Configuration conf = new Configuration().configure().addAnnotatedClass(Alien.class);
        SessionFactory sf = conf.buildSessionFactory();
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();

        Alien alien = new Alien();
        alien.setAlienId(2);
        AlienName name = new AlienName();
        name.setFirstName("Thanos");
        name.setLastName("Avenger");
        name.setMiddleName("Unbeaten");
        alien.setName(name);
        alien.setColour("green");

        session.save(alien);
        tx.commit();

        Alien readFromDB = session.get(Alien.class,2);
        System.out.println("Reading alien with id 2 from the DB " + readFromDB);




    }
}
