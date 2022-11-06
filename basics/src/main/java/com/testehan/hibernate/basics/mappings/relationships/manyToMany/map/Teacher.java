package com.testehan.hibernate.basics.mappings.relationships.manyToMany.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// The Teacher is the one that is responsible for putting a certain student in a certain course
@Entity
public class Teacher {
    @Id
    @GeneratedValue
    private int teacherId;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", name='" + name + '\'' +
                '}';
    }
}
