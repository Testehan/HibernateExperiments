package com.testehan.hibernate.basics.first1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.UUID;

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
        AlienName name = new AlienName();
        name.setFirstName("Thanos");
        name.setLastName("Avenger");
        name.setMiddleName("Unbeaten");
        alien.setName(name);
        alien.setColour("green");

        UUID generatedId = (UUID)session.save(alien);
        tx.commit();

        Alien readFromDB = session.get(Alien.class,generatedId);
        System.out.println("Reading alien from the DB " + readFromDB);

        session.close();

        hibernateObjectStatesExample(sessionFactory);
        curretSessionReturnsSameSessionObject(sessionFactory);

        sessionFactory.close();
    }

    private static void curretSessionReturnsSameSessionObject(SessionFactory sessionFactory)
    {
        // you need to configure current_session_context_class in order for this to work
        // see book notes as to when this is usefull.
        Session s1 = sessionFactory.getCurrentSession();
        System.out.println(s1);
        Session s2 = sessionFactory.getCurrentSession();
        System.out.println(s2);
    }

    // TODO the tutorial followed for the below method was not the best..will return to topic from another source
    private static void hibernateObjectStatesExample(SessionFactory sf) {
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();

        Alien alien = new Alien();
        alien.setColour("green");
        session.save(alien);
        // now the DB and the alien object will have the same value for colour

        alien.setColour("purple"); // will be persisted since a commit follows

        tx.commit();
        // now the DB and the alien object will have the same value for colour

        // The hibernate session stores update actions until a commit occurs. since there is none after
        // this line, alien color will remain purple in DB and black in java memory
        alien.setColour("black");



        Alien readFromDB = session.get(Alien.class, alien.getAlienId());
        System.out.println("Reading alien from the DB " + readFromDB);
        // here the color in the DB will be "purple" and in the print from above it will be black, because
        // probably the object is held in hibernate memory and is retrieved from cache. hence it has one value
        // there, and another in the DB

        session.detach(alien);
        readFromDB = session.get(Alien.class,alien.getAlienId());
        System.out.println("Reading alien from the DB " + readFromDB);
        // here the color is "purple" because "black" was discarded when I called detach


        session.close();

    }
}
