package com.testehan.hibernate.audit;

import javax.persistence.*;

@Entity
@org.hibernate.envers.Audited
public class Book1 {

    @Id
//    @GeneratedValue
    private int bookId;
    private String bookName;
    private String bookAuthor;

    @ManyToOne
    @JoinColumn(name = "studentId", nullable = false)
    private Student1 student;

    public Student1 getStudent() {
        return student;
    }

    public void setStudent(Student1 student) {
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
