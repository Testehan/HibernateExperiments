package com.testehan.hibernate.basics.mappings.collections.setOfComponents;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
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
    @CollectionTable(name = "IMAGE")
    protected Set<Image> images = new HashSet();
    /*
    You’re mapping a set, so the primary key of the collection table is a composite of the foreign key column ITEM_ID
    and all “embedded” non-nullable columns from Image: TITLE, filename, WIDTH, and HEIGHT.
    (The size properties are nonnullable because their values are primitives)
     */


    public Alien() {
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void addImage(Image image){
        this.images.add(image);
    }

    public long getAlienId() {
        return alienId;
    }

    public Set<Image> getImages() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alien)) return false;
        Alien alien = (Alien) o;
        return getAlienId() == alien.getAlienId() && Objects.equals(getColour(), alien.getColour()) && Objects.equals(getImages(), alien.getImages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAlienId(), getColour(), getImages());
    }
}
