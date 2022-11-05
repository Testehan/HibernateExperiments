package com.testehan.hibernate.basics.mappings.relationships.oneToMany.list;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private int studentId;
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "STUDENT_ID",
            nullable = false
    )
    @OrderColumn(name = "BOOK_POSITION",
            nullable = false
    )
    private List<Book> books = new ArrayList();


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
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


    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}
