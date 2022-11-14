package com.testehan.hibernate.audit;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Laptop1 {

    @Id
//    @GeneratedValue
    private int laptopId;
    private String laptopName;

    public int getLaptopId() {
        return laptopId;
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
}
