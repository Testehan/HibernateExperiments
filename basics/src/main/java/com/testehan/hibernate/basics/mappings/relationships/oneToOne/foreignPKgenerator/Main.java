package com.testehan.hibernate.basics.mappings.relationships.oneToOne.foreignPKgenerator;

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

        insertStudentLaptop(sf);

        Session session = sf.openSession() ;
        session.beginTransaction();

        Student student = session.get(Student.class,0);
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
            Laptop laptop = new Laptop("dell", student);
            student.setLaptop(laptop);

            session.persist(student);
            session.getTransaction().commit();

        }
    }
}
