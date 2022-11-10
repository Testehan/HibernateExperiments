package com.testehan.hibernate.loading.lazyAndEager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
    Purpose of this class is to have a one-to-one relationship with Student that is not optionable to see the check
    the points from below

    Lazy loading for one-to-one entity associations is sometimes confusing for new Hibernate users. If you consider
    one-to-one associations based on shared primary keys, an association can be proxied only if it’s optional=false. For
    example, an Address always has a reference to a User. If this association is nullable
    and optional, Hibernate must first hit the database to find out whether it should apply
    a proxy or a null—and the purpose of lazy loading is to not hit the database at all.
    You can enable lazy loading of optional one-to-one associations through bytecode
    instrumentation and interception, which we discuss later in this chapter.


*/

@Entity
public class IQ1 {

    @Id
    @GeneratedValue
    private long id;

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IQ{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
