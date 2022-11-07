package com.testehan.hibernate.basics.mappings.relationships.oneToMany.joinTable;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private int bookId;
    private String bookName;
    private String bookAuthor;

    /*
        This is the usual read-only side of a bidirectional association, with the actual mapping
        to the schema on the “mapped by” side, the Book#buyer:
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "BOOK_BUYER",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "studentId", nullable = false)
    )
    private Student buyer;

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


    public Student getBuyer() {
        return buyer;
    }

    public void setBuyer(Student buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getBookId() == book.getBookId() && Objects.equals(getBookName(), book.getBookName()) && Objects.equals(getBookAuthor(), book.getBookAuthor()) && Objects.equals(getBuyer(), book.getBuyer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getBookName(), getBookAuthor(), getBuyer());
    }
}
