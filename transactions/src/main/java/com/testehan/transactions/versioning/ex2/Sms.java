package com.testehan.transactions.versioning.ex2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/*
Hibernate lists all columns and their last known values in the WHERE clause. If any concurrent transaction
has modified any of these values or even deleted the row, this statement returns with zero updated rows.
Hibernate then throws an exception at flush time.
 */

@Entity
@org.hibernate.annotations.OptimisticLocking(
        type = org.hibernate.annotations.OptimisticLockType.ALL)
@org.hibernate.annotations.DynamicUpdate
public class Sms {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column
    private long length;

    public Sms() {}

    public Sms(String text) {
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
        if (!(o instanceof Sms)) return false;
         Sms message = (Sms) o;
        return Objects.equals(getId(), message.getId()) && Objects.equals(getText(), message.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getText());
    }

    @Override
    public String toString() {
        return "Sms{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

 }