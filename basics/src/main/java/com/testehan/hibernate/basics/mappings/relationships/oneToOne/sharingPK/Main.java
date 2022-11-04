package com.testehan.hibernate.basics.mappings.relationships.oneToOne.sharingPK;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration().configure()
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Laptop.class);
        SessionFactory sf = conf.buildSessionFactory();
        Session session = sf.openSession();

        insertStudentLaptop(session);

        session = sf.openSession() ;
        session.beginTransaction();

        Student student = session.get(Student.class,1);
        System.out.println(student.getName());
        System.out.println(student.getLaptop());


        session.getTransaction().commit();

        session.close();
        sf.close();

    }

    private static void insertStudentLaptop(Session session) {

        session.beginTransaction();

        Laptop laptop = new Laptop();
        laptop.setLaptopName("dell");
        int generatedId = (Integer)session.save(laptop);

        /*
        This means you’re responsible for setting the identifier value of a User instance correctly before you
        save it, to the identifier value of the linked Address instance
         */
        Student student = new Student(generatedId, "dan");
        student.setLaptop(laptop);
        session.persist(student);


        session.getTransaction().commit();

        session.close();
    }
}
