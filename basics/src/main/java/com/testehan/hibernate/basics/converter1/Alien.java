package com.testehan.hibernate.basics.converter1;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "alien_table")
public class Alien {

    @Id
    @GeneratedValue
    private UUID alienId;

    // converter from class MonetaryAmountConverter will be applied automatically to MonetaryAmount when reading and
    // writing data to the DB; make sure you add the Converter class to the configuration file or use it with
    // addAnnotatedClass; If not you will need to add @Convert annotation like below

//    @Convert(converter = MonetaryAmountConverter.class)
    @Column(name = "SALARY", length = 63)
    protected MonetaryAmount alienSalary;        // yeah..they want salary in bitcoin ;)

    public UUID getAlienId() {
        return alienId;
    }

    public MonetaryAmount getAlienSalary() {
        return alienSalary;
    }

    public void setAlienSalary(MonetaryAmount alienSalary) {
        this.alienSalary = alienSalary;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "alienId=" + alienId +
                ", alienSalary='" + alienSalary + '\'' +
                '}';
    }
}
