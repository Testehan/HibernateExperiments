package com.testehan.hibernate.basics.mappings.relationships.oneToOne.joinTable;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private int studentId;
    private String name;

    public Student() {}

    public Student(String name) {
        this.name = name;
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
                '}';
    }
}
