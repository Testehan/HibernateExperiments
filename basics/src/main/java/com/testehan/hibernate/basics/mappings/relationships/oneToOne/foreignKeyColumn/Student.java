package com.testehan.hibernate.basics.mappings.relationships.oneToOne.foreignKeyColumn;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private int studentId;
    private String name;

    /*

     */
    @OneToOne(fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.PERSIST)
    @JoinColumn(unique = true)      // Defaults to LAPTOP_ID column; a UNIQUE constraint, so no two users can reference the same laptop
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
