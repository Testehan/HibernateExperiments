package com.testehan.hibernate.basics.mappings.collections.set;

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

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public long getAlienId() {
        return alienId;
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
