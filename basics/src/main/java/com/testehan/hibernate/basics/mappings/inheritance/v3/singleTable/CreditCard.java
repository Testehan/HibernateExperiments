package com.testehan.hibernate.basics.mappings.inheritance.v3.singleTable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/*
Without an explicit discriminator value, Hibernate defaults to the fully qualified class
name if you use Hibernate XML files and the simple entity name if you use annotations or JPA XML files
 */
@Entity
@DiscriminatorValue("AStringThatWillBeRepresentativeForCreditCardRows")
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
