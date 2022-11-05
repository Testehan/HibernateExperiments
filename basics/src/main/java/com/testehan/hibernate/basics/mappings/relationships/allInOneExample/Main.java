package com.testehan.hibernate.basics.mappings.relationships.allInOneExample;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args)
    {
        Configuration conf = new Configuration().configure()
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Laptop.class)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Course.class);
        SessionFactory sf = conf.buildSessionFactory();
        Session session = sf.openSession();

        insertStudentBookLaptopCourse(session);

        session = sf.openSession() ;
        session.beginTransaction();

        Student student = session.get(Student.class,1);
        System.out.println(student.getName());
        System.out.println(student.getLaptop());
        System.out.println(student.getBooks());
        System.out.println(student.getCourse());

        session.delete(student);

//        Book book = session.get(Book.class,1);
//        System.out.println(book.getBookName());
//        Student student= book.getStudent();
//        System.out.println(student.getName());

//        for (Course c :student.getCourse()) {
//            System.out.println(c);
//        }

        session.getTransaction().commit();

        session.close();
        sf.close();

    }

    private static void insertStudentBookLaptopCourse(Session session) {
        Laptop laptop = new Laptop();
        laptop.setLaptopId(1);
        laptop.setLaptopName("dell");

        Book book1 = new Book();
        book1.setBookId(1);
        book1.setBookName("HarryPotter");
        book1.setBookAuthor("JK Rowling");

        Student student = new Student();
        student.setStudentId(1);
        student.setName("dan");
        student.setMark(7);
        student.setLaptop(laptop);
        student.getBooks().add(book1);
        book1.setStudent(student);

        Course course = new Course();
        course.setCourseId(101);
        course.setName("Data structures and Algorithms");
        course.getStudents().add(student);
        student.getCourse().add(course);

        session.beginTransaction();

        session.save(course);
        session.persist(student);       // by using persist instead of save, the  cascade = CascadeType.PERSIST works
//        session.save(book1);          (book is persisted without saving the object explicitly on this line)
        session.save(laptop);


        session.getTransaction().commit();

        session.close();
    }
}
