package com.testehan.hibernate.basics.mappings.inheritance.v4.joinedTables;

import javax.persistence.Entity;

/*
In subclasses, you don’t need to specify the join column if the primary key column
of the subclass table has (or is supposed to have) the same name as the primary key
column of the superclass table
 */
@Entity
public class CreditCard extends BillingDetails {

    protected String cardNumber;

    protected String expMonth;

    protected int expYear;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", expMonth='" + expMonth + '\'' +
                ", expYear='" + expYear + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
