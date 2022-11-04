package com.testehan.hibernate.basics.mappings.relationships.oneToOne.joinTable;

import javax.persistence.*;

@Entity
public class Laptop {

    @Id
    @GeneratedValue
    private int laptopId;
    private String laptopName;

    /*
        @JoinTable annotation is new; you always have to specify the name of the intermediate table. This
        mapping effectively hides the join table; there is no corresponding Java class
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "STUDENT_LAPTOP",
            joinColumns =
            @JoinColumn(name = "LAPTOP_ID"),
            inverseJoinColumns =
            @JoinColumn(name = "STUDENT_ID",
                    nullable = false,
                    unique = true)
    )
    protected Student student;

    public Laptop() {}

    public Laptop(String laptopName, Student student) {
        this.laptopName = laptopName;
        this.student=student;
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
                ", laptopName='" + laptopName + '\'' +
                '}';
    }

}
