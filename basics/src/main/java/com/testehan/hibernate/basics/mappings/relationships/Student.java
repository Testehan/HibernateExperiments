package com.testehan.hibernate.basics.mappings.relationships;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    private int studentId;
    private String name;
    private int mark;
    @OneToOne
    private Laptop laptop;      // eager by default
    @OneToMany(mappedBy = "student", fetch=FetchType.EAGER) // LAZY by default need to specify
    private List<Book> books = new ArrayList();
    @ManyToMany(mappedBy = "students")          // LAZY by default need to specify
    private List<Course> course = new ArrayList();

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public int getStudentId() {
        return studentId;
    }

    // Primary key values never change, so you shouldn’t allow modification of the identifier property value.
    // this makes the examples easier but in productive code you shouldn't need a setter
    public void setStudentId(int studentId) {
        this.studentId = studentId;
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
                ", course=" + course +
                '}';
    }
}
