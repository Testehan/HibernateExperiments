package com.testehan.hibernate.basics.mappings.relationships.manyToMany.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return teacherId == teacher.teacherId && Objects.equals(getName(), teacher.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, getName());
    }
}
