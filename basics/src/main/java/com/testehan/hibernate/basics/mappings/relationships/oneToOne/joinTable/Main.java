package com.testehan.hibernate.basics.mappings.relationships.oneToOne.joinTable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
    You’ve probably noticed that nullable columns can be problematic. Sometimes a better solution for optional
    values is an intermediate table, which contains a row if a link is present, or doesn’t if not.

    To summarize One-to-One relationships, use a shared primary key association if one of the two entities
	is always stored before the other and can act as the primary key source. Use a foreign key association
	in all other cases, and a hidden intermediate join table when your one-to-one association is optional.

 */
public class Main {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration().configure()
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Laptop.class);
        SessionFactory sf = conf.buildSessionFactory();

        insertStudentLaptop(sf);

        Session session = sf.openSession() ;
        session.beginTransaction();

        Student student = session.get(Student.class,3);
        System.out.println(student.getName());


        session.getTransaction().commit();

        session.close();
        sf.close();

    }

    private static void insertStudentLaptop(SessionFactory sf) {
        try (Session session = sf.openSession()) {

            session.beginTransaction();

            Student student = new Student("dan");
            Laptop laptop = new Laptop("dell",student);
            Laptop laptop2 = new Laptop();      // persisting a laptop that doesn not have an assigned student
            laptop2.setLaptopName("macbook");

            session.persist(laptop2);
            session.persist(laptop);
            session.persist(student);

            session.getTransaction().commit();

        }
    }
}
