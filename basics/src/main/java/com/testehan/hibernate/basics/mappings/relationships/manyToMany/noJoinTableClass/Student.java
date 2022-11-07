package com.testehan.hibernate.basics.mappings.relationships.manyToMany.noJoinTableClass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private int studentId;
    private String name;

    @ManyToMany(mappedBy = "students")     //(you don’t have to make it bidirectional, if you don’t need it)
    private Set<Course> course = new HashSet();

    public Set<Course> getCourse() {
        return course;
    }

    public void setCourse(Set<Course> course) {
        this.course = course;
    }

    public int getStudentId() {
        return studentId;
    }

    // Primary key values never change, so you shouldn’t allow modification of the identifier property value.
    // this makes the examples easier but in productive code you shouldn't need a setter
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", course=" + course +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getStudentId() == student.getStudentId() && Objects.equals(getName(), student.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId(), getName());
    }
}
