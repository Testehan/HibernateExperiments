package com.testehan.hibernate.audit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@org.hibernate.envers.Audited
public class Student1 {

    @Id
//    @GeneratedValue
    private int studentId;
    private String name;
    private int mark;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.envers.NotAudited
    private Laptop1 laptop;

    // lazy loaded
    @OneToMany(mappedBy = "student", cascade = {CascadeType.ALL})
    private List<Book1> books = new ArrayList();

    public List<Book1> getBooks() {
        return books;
    }

    public void setBooks(List<Book1> books) {
        this.books = books;
    }

    public Laptop1 getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop1 laptop) {
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
                ", laptop=" + laptop +
                ", books=" + books +
                '}';
    }
}
