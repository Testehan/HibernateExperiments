package com.testehan.hibernate.query.basics;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "findStudentById",
                query = "select i from Student i where i.studentId = :id"
        )
})
@Entity
public class Student {

    @Id
    @GeneratedValue
    private int studentId;
    private String name;
    private int mark;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Laptop laptop;


    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", mark=" + mark +
                '}';
    }
}
