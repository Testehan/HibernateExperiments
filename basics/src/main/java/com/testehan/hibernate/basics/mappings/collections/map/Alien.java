package com.testehan.hibernate.basics.mappings.collections.map;

import javax.persistence.*;
import java.util.SortedMap;
import java.util.TreeMap;

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
    @MapKeyColumn(name = "FILE_NAME")			// map keys
    @Column(name = "IMAGE_NAME")			    // map values
    protected SortedMap<String, String> images = new TreeMap();

    public Alien() {
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void addImage(String filename, String image){
        this.images.put(filename, image);
    }

    public SortedMap<String, String> getImages() {
        return images;
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
