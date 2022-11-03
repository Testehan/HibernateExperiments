package com.testehan.hibernate.basics.mappings.collections.list2;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "FILENAME")
    /*
    While the JPA @OrderBy annotation allows you to specify the entity attributes used for sorting when fetching the
    current annotated collection, the Hibernate specific @OrderBy annotation is used to specify a SQL clause instead.
     */
//    @OrderBy(value = "FILENAME asc")
    @org.hibernate.annotations.OrderBy(clause = "FILENAME asc")
    protected List<String> images = new ArrayList();

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
