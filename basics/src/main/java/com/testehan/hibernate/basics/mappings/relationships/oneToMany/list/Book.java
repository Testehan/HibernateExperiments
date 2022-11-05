package com.testehan.hibernate.basics.mappings.relationships.oneToMany.list;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private int bookId;
    private String bookName;
    private String bookAuthor;

    /*  TODO too complicated for me now... in case i get into such a situation this is the relevant part of the bok
        You probably expected different code—maybe @ManyToOne(mappedBy="bids") and no additional @JoinColumn
        annotation. But @ManyToOne doesn’t have a mappedBy attribute: it’s always the “owning” side of the
        relationship. You’d have to make the other side, @OneToMany, the mappedBy side. Here you run into a
        conceptual problem and some Hibernate quirks.
        The Student#books collection is no longer read-only, because Hibernate now has to store the index of each
        element. If the Book#student side was the owner of the relationship, Hibernate would ignore the collection
        when storing data and not write the element indexes. You have to map the @JoinColumn twice and then
        disable writing on the @ManyToOne side with updatable=false and insertable=false. Hibernate now
        considers the collection side when storing data, including the index of each element. The @ManyToOne is
        effectively read-only, as it would be if it had a mappedBy attribute
     */
    @ManyToOne
    @JoinColumn(
            name = "STUDENT_ID",
            updatable = false, insertable = false, nullable = false
    )
    private Student student;

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
