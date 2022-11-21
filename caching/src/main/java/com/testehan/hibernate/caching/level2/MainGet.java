package com.testehan.hibernate.caching.level2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MainGet {

    // this example shows the Level 2 (meaning for sessions obtained from the same sessionFactory)
    // with "get" methods
    public static void main(String[] args) {
        System.out.println("Starting the Hibernate App");
        Alien alien;
        int alienId =0;

        Configuration conf = new Configuration().configure().addAnnotatedClass(Alien.class).addAnnotatedClass(Weapon.class);
        SessionFactory sessionFactory = conf.buildSessionFactory();

        alienId = insertAlien(sessionFactory);


        try (Session session1 = sessionFactory.openSession()) {

            Transaction tx = session1.beginTransaction();

            alien = session1.get(Alien.class, alienId);
            System.out.println("DB is hit with a select query only if the method insertAlien is commented out and the current cache doesn't know about the alien");
            System.out.println(alien);

            alien = session1.get(Alien.class, alienId);
            System.out.println("DB is not hit with a select, because object is retrieved from Level 1 hibernate cache");
            System.out.println(alien);

            tx.commit();
        }

        try (Session session2 = sessionFactory.openSession())
        {
            Transaction tx2 = session2.beginTransaction();

            alien = session2.get(Alien.class, alienId);
            System.out.println("We are using a different session but second level caching is enabled, DB will NOT be hit with a new select query");
            System.out.println(alien);

            tx2.commit();
        }

        // select will be triggered for the first time when items are loaded
        printAlienWeapons(alienId, sessionFactory);
        // no select now
        printAlienWeapons(alienId, sessionFactory);


        sessionFactory.close();

    }

    private static void printAlienWeapons(int alienId, SessionFactory sessionFactory) {
        Alien alien;
        try (Session session2 = sessionFactory.openSession())
        {
            Transaction tx2 = session2.beginTransaction();

            alien = session2.get(Alien.class, alienId);
            for (Weapon w : alien.getWeapons()){
                System.out.println(w);
            }

            tx2.commit();
        }
    }

    private static int insertAlien(SessionFactory sessionFactory) {
        System.out.println("Inside insertAlien ----------------------------");

        int alienId;
        Alien alien;
        try (Session session1 = sessionFactory.openSession()) {

            Transaction tx = session1.beginTransaction();

            alien = new Alien();
            alien.setName(new AlienName("Thanos", "The great", ""));
            alien.setColour("purple");

            Weapon weapon = new Weapon();
            weapon.setWeaponName("blade of glory");
            weapon.setDmg(100);
            weapon.setAlienOwner(alien);

            Weapon weapon2 = new Weapon();
            weapon2.setWeaponName("gauntlet of stones");
            weapon2.setDmg(100000);
            weapon2.setAlienOwner(alien);

            alien.getWeapons().add(weapon);
            alien.getWeapons().add(weapon2);

            alienId = (int)session1.save(alien);
            session1.save(weapon);
            session1.save(weapon2);

            System.out.println("Alien was added in DB : " + alien);

            tx.commit();

            for (Weapon w : alien.getWeapons()){
                System.out.println(w);
            }
        }
        return alienId;
    }
}
