package com.testehan.hibernate.basics.mappings.relationships.oneToMany.bag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private int studentId;
    private String name;

    @OneToMany(mappedBy = "student", fetch=FetchType.EAGER,  cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Collection<Book> books = new ArrayList();


    public Collection<Book> getBooks() {
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
