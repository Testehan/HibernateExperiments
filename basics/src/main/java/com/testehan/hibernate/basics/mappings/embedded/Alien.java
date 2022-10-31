package com.testehan.hibernate.basics.mappings.embedded;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "alien_table")
public class Alien {

    @Id
    @GeneratedValue
    private UUID alienId;
    private Address shippingAdress;

    // because our aliens needs 2 addresses, we can't use the same column names, so we need to override them with new names
    @AttributeOverrides({
            @AttributeOverride(name = "street",
                    column = @Column(name = "BILLING_STREET")),
            @AttributeOverride(name = "city",
                    column = @Column(name = "BILLING_CITY"))
    })
    protected Address billingAddress;

    // The @Embedded annotation is useful if you want to map a third-party component class without source and no annotations,
    //	but using the right getter/setter methods (like regular JavaBeans). Weapon class has no annotations whatsoever,
    // but its fields will be used to create columns in the Aliens table
    @Embedded
    private Weapon weapon;

    public UUID getAlienId() {
        return alienId;
    }

    public Address getShippingAdress() {
        return shippingAdress;
    }

    public void setShippingAdress(Address shippingAdress) {
        this.shippingAdress = shippingAdress;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "alienId=" + alienId +
                ", shippingAdress=" + shippingAdress +
                ", billingAddress=" + billingAddress +
                ", weapon=" + weapon +
                '}';
    }
}
