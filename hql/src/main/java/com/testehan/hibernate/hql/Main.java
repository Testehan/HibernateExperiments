package com.testehan.hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        System.out.println( "Starting the application" );

        Configuration conf = new Configuration().configure()
                .addAnnotatedClass(Student.class);
        SessionFactory sf = conf.buildSessionFactory();
        Session session = sf.openSession();

//        insert50Students(session);

        printStudentsWithMarksOver90(session);
        printStudentWithId5(session);
        printSomeFieldsForStudentWithId7(session);
        printNameAndMarkOfPassingStudents(session);

        printSumOfAllMarksOfStudentsThatPassed(session);

        int minimumMark = 70;
        parameterizedQueries_2Approaches_sumOfAllMarksBiggerThan(session,minimumMark);

        clasicSQLQuery_printAllStudentsWithMarksOver90(session);

        usingResultTransformerForProcessingNativeQueryResults_printNamesAndMarksForBestStudents(session);

    }

    private static void usingResultTransformerForProcessingNativeQueryResults_printNamesAndMarksForBestStudents(Session session) {
        session.beginTransaction();
        // see tutorials file for more information about the deprecated ResultTransformer and ListResultTransformer was added
        NativeQuery query = session.createSQLQuery("select name,mark from student where mark > 90");
        query.setResultTransformer((ListResultTransformer) (tuples, aliases) ->
                {
                    Map<String, Object> result = new HashMap();
                    result.put(aliases[0],tuples[0]);
                    result.put(aliases[1],tuples[1]);
                    return result;
                }
        );
        List<Map> students = query.list();
        for (Map student : students) {
            System.out.println(student.get("name") + "   " + student.get("mark"));
        }

        session.getTransaction().commit();
    }

    private static void clasicSQLQuery_printAllStudentsWithMarksOver90(Session session) {
        session.beginTransaction();
        // in this type we can put actual SQL queries
        NativeQuery query = session.createSQLQuery("select * from student where mark > 90");
        query.addEntity(Student.class);
        List<Student> students = query.list();
        for (Student s : students)
            System.out.println(s);

        session.getTransaction().commit();
    }

    private static void parameterizedQueries_2Approaches_sumOfAllMarksBiggerThan(Session session, int minimumMark) {
        session.beginTransaction();

        Query query2 = session.createQuery("select sum(mark) from Student s where s.mark> ?1");
        query2.setParameter(1,minimumMark);
        long sum = (Long) query2.uniqueResult();
        System.out.println("Sum of all marks of students that passed with min mark " + minimumMark + " : " + sum);

        // another way of providing the parameter value
        Query query3 = session.createQuery("select sum(mark) from Student s where s.mark> :minimumM");
        query3.setParameter("minimumM",minimumMark);
        sum = (Long) query3.uniqueResult();
        System.out.println("Sum of all marks of students that passed with min mark " + minimumMark + " : " + sum);


        session.getTransaction().commit();
    }

    private static void printSumOfAllMarksOfStudentsThatPassed(Session session) {
        session.beginTransaction();

        Query query2 = session.createQuery("select sum(mark) from Student s where s.mark>50");
        long sum = (Long) query2.uniqueResult();
        System.out.println("Sum of all marks of students that passed :" + sum);

        session.getTransaction().commit();
    }

    private static void printNameAndMarkOfPassingStudents(Session session) {
        session.beginTransaction();

        Query query2 = session.createQuery("select name, mark from Student s where s.mark>50");
        List<Object[]> queryResults = (List<Object[]>)query2.list();
        System.out.println("Name and mark of passing students ");
        for (Object[] current : queryResults){
            System.out.println(current[0] + "  " + current[1]);
        }

        session.getTransaction().commit();
    }

    private static void printSomeFieldsForStudentWithId7(Session session) {
        session.beginTransaction();

        Query query2 = session.createQuery("select studentId, name, mark from Student where studentId=7");
        Object[] queryResults = (Object[])query2.uniqueResult();
        System.out.println("Information about student with id 7 = "
                + queryResults[0] + " " + queryResults[1] + " " + queryResults[2]);

        session.getTransaction().commit();
    }

    private static void printStudentWithId5(Session session) {
        session.beginTransaction();

        Query query2 = session.createQuery("from Student where studentId=5");
        Student student = (Student)query2.uniqueResult();
        System.out.println("Student with id 5 = " + student);

        session.getTransaction().commit();
    }

    private static void printStudentsWithMarksOver90(Session session) {
        session.beginTransaction();

        Query query = session.createQuery("from Student where mark>90");
        List<Student> students = query.list();
        for (Student student: students) {
            System.out.println(student);
        }

        session.getTransaction().commit();
    }

    private static void insert50Students(Session session) {
        session.beginTransaction();

        Random nr = new Random();
        for (int i = 1; i<=50; i++){
           Student student = new Student();
           student.setStudentId(i);
           student.setName("name " + i);
           student.setMark(nr.nextInt(100));

           session.save(student);
        }

        session.getTransaction().commit();
    }
}
