package com.testehan.hibernate.basics.converter2;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private Zipcode zipcode;
    // other address related fields

    public Zipcode getZipcode() {
        return zipcode;
    }

    public void setZipcode(Zipcode zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "zipcode=" + zipcode +
                '}';
    }
}
