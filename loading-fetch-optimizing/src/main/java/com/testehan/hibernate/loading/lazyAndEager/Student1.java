package com.testehan.hibernate.loading.lazyAndEager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student1 {

    @Id
    @GeneratedValue
    private int studentId;
    private String name;
    private int mark;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Laptop1 laptop;

    @OneToOne(fetch=FetchType.LAZY, optional=false, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private IQ1 studentIq;

    // lazy loaded
    @OneToMany(mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Book1> books = new ArrayList();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    /*
        With LazyCollectionOption.EXTRA, the collection supports operations that don’t
        trigger initialization. For example, you could ask for the collection’s size
     */
    @org.hibernate.annotations.LazyCollection(
            org.hibernate.annotations.LazyCollectionOption.EXTRA
    )
    private List<Pen1> pens = new ArrayList();
    // lazy loaded
    @ManyToMany(mappedBy = "students", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Course1> course = new ArrayList();

    /*
        Unlike FetchType.LAZY, which is a hint the JPA provider can ignore, a FetchType.EAGER is a hard
        requirement. The provider has to guarantee that the data is loaded and available in detached
        state; it can’t ignore the setting
     */
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Notebook1> notebooks = new ArrayList();

    public List<Notebook1> getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(List<Notebook1> notebooks) {
        this.notebooks = notebooks;
    }

    public List<Course1> getCourse() {
        return course;
    }

    public void setCourse(List<Course1> course) {
        this.course = course;
    }

    public IQ1 getStudentIq() {
        return studentIq;
    }

    public void setStudentIq(IQ1 studentIq) {
        this.studentIq = studentIq;
    }

    public List<Pen1> getPens() {
        return pens;
    }

    public void setPens(List<Pen1> pens) {
        this.pens = pens;
    }

    public List<Book1> getBooks() {
        return books;
    }

    public void setBooks(List<Book1> books) {
        this.books = books;
    }

    public Laptop1 getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop1 laptop) {
        this.laptop = laptop;
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

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", mark=" + mark +
                ", laptop=" + laptop +
                ", books=" + books +
                ", course=" + course +
                '}';
    }
}
