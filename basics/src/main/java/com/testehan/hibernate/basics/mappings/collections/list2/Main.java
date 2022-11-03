package com.testehan.hibernate.basics.mappings.collections.list2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/*
@OrderBy is used to order the elements of a list when the list is fetched from the DB
 */

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting the Hibernate App");

        Configuration conf = new Configuration().configure().addAnnotatedClass(Alien.class);
        SessionFactory sessionFactory = conf.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // clean up Alien's class table
//        Transaction tx = session.beginTransaction();
//        session.createQuery("delete from Alien").executeUpdate();
//        tx.commit();

//        tx = session.beginTransaction();

        Alien alien = new Alien();
        alien.setColour("green");
        alien.addImage("et3.jpg");
        alien.addImage("et1.jpg");
        alien.addImage("et2.jpg");       // 3 images will be present in the DB, as images is a List..


//        long generatedId = (Long)session.save(alien);
//        tx.commit();

        Alien readFromDB = session.get(Alien.class,1l);
        // list of images is ordered when doing a fetch from the DB. if object is returned from the hibernate cache the
        // elements will not be ordered
        System.out.println("Reading alien from the DB " + readFromDB);

        session.close();

        sessionFactory.close();
    }

}
