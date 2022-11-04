package com.testehan.hibernate.basics.mappings.relationships.oneToOne.foreignPKgenerator;

import javax.persistence.*;

@Entity
public class Laptop {

    /*
    When you persist an instance of Laptop, this special generator grabs the value of the student property and
    takes the identifier value of the referenced entity instance, the Student.
    (what ??? In simpler words...this will generate an id that will also be assigned to the Student entity referenced
    by the student field in this class)
     */

    @Id
    @GeneratedValue(generator = "addressKeyGenerator")
    @org.hibernate.annotations.GenericGenerator(
            name = "addressKeyGenerator",
            strategy = "foreign",
            parameters =
            @org.hibernate.annotations.Parameter(
                    name = "property", value = "student"
            )
    )
    private int laptopId;
    private String laptopName;

    /*
    The student property is marked as a shared primary key entity association with the @PrimaryKeyJoinColumn
    annotation. It’s set to optional=false, so an Laptop must have a reference to a Student
     */
    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    private Student student;

    public Laptop() {}

    // The  public constructors of Laptop now require a Student instance.
    public Laptop(String laptopName, Student student) {
        this.laptopName = laptopName;
        this.student = student;
    }

    public String getLaptopName() {
        return laptopName;
    }

    public void setLaptopName(String laptopName) {
        this.laptopName = laptopName;
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "laptopId=" + laptopId +
                ", laptopName='" + laptopName + '\'' +
                '}';
    }

    public int getLaptopId() {
        return this.laptopId;
    }
}
