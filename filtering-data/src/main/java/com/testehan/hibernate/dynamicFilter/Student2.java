package com.testehan.hibernate.dynamicFilter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Student2 {

    @Id
    @GeneratedValue
    private int studentId;
    private String name;

    private float reputation;

    // lazy loaded
    @OneToMany(mappedBy = "student", cascade = {CascadeType.ALL})
    @org.hibernate.annotations.Filter(
            name = "limitByReputation",
            condition = ":currentReputation in ( 10,20,50 )"    // overwritten the condition to ONLY read books
    )                                                           // for students  where these reputations
    private List<Book2> books = new ArrayList();

    public List<Book2> getBooks() {
        return books;
    }

    public void setBooks(List<Book2> books) {
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

    public float getReputation() {
        return reputation;
    }

    public void setReputation(float reputation) {
        this.reputation = reputation;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", books=" + books +
                ", reputation=" + reputation +
                '}';
    }
}
