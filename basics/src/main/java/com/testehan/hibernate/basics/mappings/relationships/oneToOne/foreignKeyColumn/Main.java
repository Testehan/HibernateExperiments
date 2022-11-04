package com.testehan.hibernate.basics.mappings.relationships.oneToOne.foreignKeyColumn;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
    Instead of sharing a primary key, two rows can have a relationship based on a simple
    additional foreign key column. One table has a foreign key column that references
    the primary key of the associated table.
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

        Student student = session.get(Student.class,1);
        System.out.println(student.getName());
        System.out.println(student.getLaptop());


        session.getTransaction().commit();

        session.close();
        sf.close();

    }

    private static void insertStudentLaptop(SessionFactory sf) {
        try (Session session = sf.openSession()) {

            session.beginTransaction();

            Student student = new Student("dan");
            Laptop laptop = new Laptop("dell");
            student.setLaptop(laptop);

            session.persist(student);
            session.getTransaction().commit();

        }
    }
}
