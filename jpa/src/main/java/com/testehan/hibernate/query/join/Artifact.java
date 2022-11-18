package com.testehan.hibernate.query.join;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Artifact {

    @Id
    @GeneratedValue
    private long id;

    private String name;        // purposes is to have an unrelated entity to Item to have a theta-style join

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Artifact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
