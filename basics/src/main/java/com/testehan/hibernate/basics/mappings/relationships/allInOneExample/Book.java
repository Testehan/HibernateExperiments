package com.testehan.hibernate.basics.mappings.relationships.allInOneExample;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Book {

    @Id
    private int bookId;
    private String bookName;
    private String bookAuthor;
    // using this annotation + (mappedBy = "student") (from Student class) will make the mapping happen in the
    // book table with a new column, instead of a student_book table
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

    // Primary key values never change, so you shouldn’t allow modification of the identifier property value.
    // this makes the examples easier but in productive code you shouldn't need a setter
    public void setBookId(int bookId) {
        this.bookId = bookId;
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
