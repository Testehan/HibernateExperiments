package com.testehan.hibernate.basics.first1;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "alien_table")
public class Alien {

    @Id
    @GeneratedValue
    private UUID alienId;
    @Transient
    private int notInDb;
    private AlienName name;
    @Column(name = "alien_colour")
    private String colour;

    public UUID getAlienId() {
        return alienId;
    }

    public AlienName getName() {
        return name;
    }

    public void setName(AlienName name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getNotInDb() {
        return notInDb;
    }

    public void setNotInDb(int notInDb) {
        this.notInDb = notInDb;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "alienId=" + alienId +
                ", notInDb=" + notInDb +
                ", name=" + name +
                ", colour='" + colour + '\'' +
                '}';
    }
}
