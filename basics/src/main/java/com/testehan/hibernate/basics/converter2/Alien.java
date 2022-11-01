package com.testehan.hibernate.basics.converter2;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "alien_table")
public class Alien {

    @Id
    @GeneratedValue
    private UUID alienId;

    /*
    The attributeName declares the zipcode attribute of the embeddable Address class.
    This setting supports a dot syntax for the attribute path; if zipcode isn’t a property of
    the Address class but is a property of a nested embeddable City class reference it with city.zipcode, its nested
     path.

      If several @Convert annotations are required on a single embedded property, to
        convert several attributes of the Address, for example, you can group them within an
        @Converts annotation
     */
    @Convert(
            converter = ZipcodeConverter.class,
            attributeName = "zipcode"
    )
    @Column(name = "zipcode", length = 10)
    private Address address;

    public UUID getAlienId() {
        return alienId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "alienId=" + alienId +
                ", Address='" + address + '\'' +
                '}';
    }
}
