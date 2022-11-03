package com.testehan.hibernate.basics.mappings.collections.set;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alien_table")
public class Alien {

    @Id
    @GeneratedValue
    private long alienId;
    @Column(name = "alien_colour")
    private String colour;

    @ElementCollection
    @CollectionTable(
            name = "IMAGE",
            joinColumns = @JoinColumn(name = "alienId"))
    @Column(name = "FILENAME")
    protected Set<String> images = new HashSet();

    public Alien() {
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void addImage(String image){
        this.images.add(image);
    }

    @Override
    public String toString() {
        return "Alien{" +
                "alienId=" + alienId +
                ", colour='" + colour + '\'' +
                ", images=" + images +
                '}';
    }
}
