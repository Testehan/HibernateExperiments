package com.testehan.hibernate.basics.mappings.enumm;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "alien_table")
public class Alien {

    @Id
    @GeneratedValue
    private long alienId;
    @NotNull
    @Enumerated(EnumType.STRING) // without this in the DB the ordinal number of the enum value will be stored as int;
                                   // with this, the string value of the enum will be stored
    private Colour colour = Colour.WHITE;

    public Alien() {
    }


    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "alienId=" + alienId +
                ", colour='" + colour + '\'' +
                '}';
    }
}
