package com.testehan.hibernate.loading.cartesianProblem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student3 {

    @Id
    @GeneratedValue
    private int studentId;
    private String name;
    private int mark;


    // we can't have 2 collections with FetchType.Eager -> cannot simultaneously fetch multiple bags: error
    // however if we add the Fetch with FetchMode.Select it will work, because it will not use a join to perform the operation
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}) //removed fetch = FetchType.EAGER
    @org.hibernate.annotations.Fetch(
            org.hibernate.annotations.FetchMode.SELECT
    )
    private List<Book3> books = new ArrayList();

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Notebook3> notebooks = new ArrayList();

    public List<Notebook3> getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(List<Notebook3> notebooks) {
        this.notebooks = notebooks;
    }
    public List<Book3> getBooks() {
        return books;
    }

    public void setBooks(List<Book3> books) {
        this.books = books;
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
                ", books=" + books +
                '}';
    }
}
