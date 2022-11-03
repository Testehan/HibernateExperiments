package com.testehan.hibernate.basics.mappings.collections.mapOfComponents;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "alien_table")
public class Alien {

    @Id
    @GeneratedValue
    private long alienId;
    @Column(name = "alien_colour")
    private String colour;

    @ElementCollection
    @CollectionTable(name = "IMAGE")
    protected Map<Filename, Image> images = new HashMap();

    public Alien() {
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void addImage(Filename filename, Image image){
        this.images.put(filename,image);
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
