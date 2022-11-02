package com.testehan.hibernate.basics.mappings.inheritance.v4.joinedTables;


import javax.persistence.*;

/*

 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BillingDetails {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false)
    protected String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
