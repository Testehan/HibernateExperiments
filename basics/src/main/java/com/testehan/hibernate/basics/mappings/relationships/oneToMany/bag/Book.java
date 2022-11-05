package com.testehan.hibernate.basics.mappings.relationships.oneToMany.bag;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private int bookId;
    private String bookName;
    private String bookAuthor;

    @ManyToOne
    // You can override the foreign key column with the @JoinColumn annotation. We
    // used it here for a different reason: to make the foreign key column NOT NULL when
    //  Hibernate generates the SQL schema. A book always has to have a reference to an student (at least in our example);
    @JoinColumn(name = "studentId", nullable = false)
    private Student student; // eager by default

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
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
