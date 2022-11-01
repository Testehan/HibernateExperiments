package com.testehan.hibernate.basics.converter1;

import java.io.Serializable;
import java.util.Currency;
import java.util.Objects;

public class MonetaryAmount implements Serializable {

    protected final Integer value;
    protected final Currency currency;
    public MonetaryAmount(Integer value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }
    public Integer getValue() {
        return value;
    }
    public Currency getCurrency() {
        return currency;
    }

    public String toString() {
        return getValue() + " " + getCurrency();
    }
    public static MonetaryAmount fromString(String s) {
        String[] split = s.split(" ");
        return new MonetaryAmount(
                new Integer(split[0]),
                Currency.getInstance(split[1])
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonetaryAmount that = (MonetaryAmount) o;
        return Objects.equals(value, that.value) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }
}
