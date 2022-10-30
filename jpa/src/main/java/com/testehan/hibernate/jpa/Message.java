package com.testehan.hibernate.jpa;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
Hibernate never needs to execute UPDATE statements on the Message table. Hibernate can also make a few other
optimizations, such as avoiding dirty checking, if you map an immutable class like below
*/
 @Entity
 @Immutable
public class Message {
    @Id
    @GeneratedValue
    private Long id;
//    @Basic(optional = false)    or
    @Column(nullable = false)
    private String text;

    // hibernate needs a default constructor; since we have a parameterized one, we need to explicitly
    // declare de default one
    public Message() {}

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}