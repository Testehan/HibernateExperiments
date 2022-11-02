package com.testehan.hibernate.basics.mappings.inheritance.v3.singleTable;


import javax.persistence.*;

/*
The root class BillingDetails of the inheritance hierarchy is mapped to the table BILLINGDETAILS automatically

Shared properties of the superclass can be NOT NULL in the schema; every subclass instance must have a value.
An implementation quirk of Hibernate requires that you declare nullability with @Column because

You have to add a special discriminator column to distinguish what each row represents. This isn’t a property of the
entity; it’s used internally by Hibernate. The column name is BD_TYPE, and the values are strings—in this case,
"CC" or "BA". Hibernate automatically sets and retrieves the discriminator values.

If you don’t specify a discriminator column in the superclass, its name defaults to DTYPE and the value are strings
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BillingDetails_TYPE", length=255)
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
