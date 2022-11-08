package com.testehan.transactions.versioning.ex1;

import org.hibernate.annotations.OptimisticLock;

import javax.persistence.*;
import java.util.Objects;

/*
You could add a getVersion() method to the class, but you shouldn’t have a setter method and the application shouldn’t
modify the value. Hibernate automatically changes the version value: it increments the version number whenever a Message
instance has been found dirty during flushing of the persistence context. The version
is a simple counter without any useful semantic value beyond concurrency control.
You can use an int, an Integer, a short, a Short, or a Long instead of a long; Hibernate wraps and starts from zero
again if the version number reaches the limit of the data type.
 */

 @Entity
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String text;

    //  If you don’t want to increment the version of the entity instance when a particular property’s value has changed
    @Column
    @OptimisticLock(excluded = true)
    private long length;

     @Version
     private long version;

    public Message() {}

    public Message(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

     public void setText(String text) {
         this.text = text;
     }

     public void setLength(long length){
        this.length = length;
     }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(getId(), message.getId()) && Objects.equals(getText(), message.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getText());
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

     public long getVersion() {
        return this.version;
     }
 }