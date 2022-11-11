package com.testehan.hibernate.loading.nPlusOnePrb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@org.hibernate.annotations.BatchSize(size = 10)
public class Laptop2 {

    @Id
    @GeneratedValue
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
