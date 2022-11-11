package com.testehan.hibernate.loading.nPlusOnePrb;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student2 {

    @Id
    @GeneratedValue
    private int studentId;
    private String name;
    private int mark;

    @OneToOne(fetch=FetchType.LAZY, optional=false, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private IQ2 studentIq;

    @OneToOne(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Laptop2 laptop;

    @OneToMany(mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.annotations.Fetch(
            org.hibernate.annotations.FetchMode.SUBSELECT
    )
    private List<Book2> books = new ArrayList();

    public List<Book2> getBooks() {
        return books;
    }

    public void setBooks(List<Book2> books) {
        this.books = books;
    }

    public IQ2 getStudentIq() {
        return studentIq;
    }

    public void setStudentIq(IQ2 studentIq) {
        this.studentIq = studentIq;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Laptop2 getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop2 laptop) {
        this.laptop = laptop;
    }

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

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", mark=" + mark +
                '}';
    }
}
