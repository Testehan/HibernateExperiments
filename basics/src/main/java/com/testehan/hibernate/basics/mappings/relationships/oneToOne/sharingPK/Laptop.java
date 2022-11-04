package com.testehan.hibernate.basics.mappings.relationships.oneToOne.sharingPK;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Laptop {

    @Id
    @GeneratedValue
    private int laptopId;
    private String laptopName;

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
