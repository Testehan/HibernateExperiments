package com.testehan.hibernate.loading.nPlusOnePrb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
     @org.hibernate.annotations.BatchSize(size = 10)

    This setting tells Hibernate that it may load up to 10 IQ2 proxies if one has to be
    loaded, all with the same SELECT. Batch fetching is often called a blind-guess optimization, because you don’t
    know how many uninitialized User proxies may be in a particular persistence context. You can’t say for sure that
    10 is an ideal value—it’s a guess. You know that instead of n+1 SQL queries, you’ll now see n+1/10 queries, a
    significant reduction. Reasonable values are usually small, because you don’t want to load too
    much data into memory either, especially if you aren’t sure you’ll need it

    if you look at the query is will be something liwhere
        iq2x0_.id in ( ?, ? )where notebooks0_.studentId in (?, ?)
*/

@Entity
public class IQ2 {

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
