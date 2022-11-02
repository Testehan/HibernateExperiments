package com.testehan.hibernate.basics.mappings.inheritance.v2.union;


import javax.persistence.*;

/*
The database identifier and its mapping have to be present in the superclass, to share
it in all subclasses and their tables. This is no longer optional, as it was for the previous
mapping strategy
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BillingDetails {

    @Id
    @GeneratedValue
    protected Long id;

    protected String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
