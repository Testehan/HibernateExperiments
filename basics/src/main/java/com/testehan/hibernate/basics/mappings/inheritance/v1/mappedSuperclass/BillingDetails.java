package com.testehan.hibernate.basics.mappings.inheritance.v1.mappedSuperclass;


import javax.persistence.MappedSuperclass;

/*
 By default, properties of the superclass are ignored and not persistent! You have to
 annotate the superclass with @MappedSuperclass to enable embedding of its properties
 in the concrete subclass tables;
 */

@MappedSuperclass
public abstract class BillingDetails {

    protected String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
