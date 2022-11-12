package com.testehan.hibernate.loading.cartesianProblem;

import javax.persistence.*;

@Entity
public class Book3 {

    @Id
    @GeneratedValue
    private int bookId;
    private String bookName;
    private String bookAuthor;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student3 student;

    public Student3 getStudent() {
        return student;
    }

    public void setStudent(Student3 student) {
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
