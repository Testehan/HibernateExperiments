package com.testehan.hibernate.basics.first3;

import javax.persistence.*;

@Entity
@Table(name = "alien_table")
public class Alien {

    // SEQUENCE is the generation type recommended by the Hibernate documentation.
    @Id
    @GeneratedValue(generator = "ALIEN_ID_GENERATOR")
    @org.hibernate.annotations.GenericGenerator(
            name = "ALIEN_ID_GENERATOR",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "sequence_name",
                            value = "ALIEN_SEQUENCE_FIRST3"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = "initial_value",
                            value = "1000"
                    )
            })
    private long alienId;
    @Column(name = "alien_colour")
    private String colour;

    public Alien() {
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
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
