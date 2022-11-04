package com.testehan.hibernate.basics.mappings.relationships.oneToOne.sharingPK;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    private int studentId;  // Uses application-assigned identifier value
    private String name;

    //The optional=false switch defines that a Student must have a laptop.
    // The Hibernate-generated database schema reflects this with a foreign key constraint.
    @OneToOne(fetch = FetchType.LAZY, optional = false )
    @PrimaryKeyJoinColumn   // Selects shared primary key strategy
    private Laptop laptop;

    public Student() {}

    public Student(int studentId, String name) {
        this.studentId = studentId;
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
