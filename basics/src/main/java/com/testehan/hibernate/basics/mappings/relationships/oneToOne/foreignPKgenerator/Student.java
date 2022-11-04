package com.testehan.hibernate.basics.mappings.relationships.oneToOne.foreignPKgenerator;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    private int studentId;  // Uses application-assigned identifier value
    private String name;

    /*
    the mappedBy option, telling Hibernate that the lower-level details are now mapped by the “property on the
    other side" ,named "student"
    you enable CascadeType.PERSIST; transitive persistence will make it easier to save the instances in the
    right order. When you make the Student persistent, Hibernate makes the laptop persistent and generates the
    identifier for the primary key automatically.
     */
    @OneToOne(mappedBy = "student", cascade = CascadeType.PERSIST)
    private Laptop laptop;

    public Student() {}

    public Student(String name) {
        this.name = name;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", laptop=" + laptop +
                '}';
    }
}
