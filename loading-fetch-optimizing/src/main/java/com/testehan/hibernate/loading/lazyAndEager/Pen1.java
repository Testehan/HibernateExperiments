package com.testehan.hibernate.loading.lazyAndEager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pen1 {

    @Id
    @GeneratedValue
    private long penId;

    private String colour;

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "Pen{" +
                "penId=" + penId +
                ", colour='" + colour + '\'' +
                '}';
    }
}
