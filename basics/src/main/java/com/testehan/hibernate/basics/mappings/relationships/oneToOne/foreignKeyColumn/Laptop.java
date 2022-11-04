package com.testehan.hibernate.basics.mappings.relationships.oneToOne.foreignKeyColumn;

import javax.persistence.*;

@Entity
public class Laptop {

    @Id
    @GeneratedValue
    private int laptopId;
    private String laptopName;

    public Laptop() {}

    public Laptop(String laptopName) {
        this.laptopName = laptopName;
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
