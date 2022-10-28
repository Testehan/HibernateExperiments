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

    // this transformer is usefull when your DB holds data representing something, and in the java app you want something else
    @Column(name = "alien_weight_pounds")
    @org.hibernate.annotations.ColumnTransformer(
            read = "? / 2.20",
            write = "? * 2.20"
    )
    private int weightKg;

    public Alien() {
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "alienId=" + alienId +
                ", colour='" + colour + '\'' +
                ", weightKg='" + weightKg + '\'' +
                '}';
    }
}
