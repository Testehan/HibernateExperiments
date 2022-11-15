package com.testehan.hibernate.dynamicFilter;

import javax.persistence.*;

/*
    The condition is an SQL expression that’s passed through directly to the database system, so you can use any SQL
    operator or function. It must evaluate to true if a record should pass the filter. In this example, you use a
    subquery to obtain the rank of the seller of the item.

*/


@org.hibernate.annotations.Filter(name = "limitByReputation")
@Entity
public class Book2 {

    @Id
    @GeneratedValue
    private int bookId;
    private String bookName;
    private String bookAuthor;

    @ManyToOne
    @JoinColumn(name = "studentId", nullable = false)
    private Student2 student;

    public Student2 getStudent() {
        return student;
    }

    public void setStudent(Student2 student) {
        this.student = student;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                '}';
    }
}
