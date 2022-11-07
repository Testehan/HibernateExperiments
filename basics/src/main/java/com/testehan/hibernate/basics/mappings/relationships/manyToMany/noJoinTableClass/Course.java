package com.testehan.hibernate.basics.mappings.relationships.manyToMany.noJoinTableClass;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private int courseId;
    private String name;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "COURSE_STUDENT",
            joinColumns = @JoinColumn(name = "courseId"),
            inverseJoinColumns = @JoinColumn(name = "studentId")
    )
    private Set<Student> students = new HashSet();

    public int getCourseId() {
        return courseId;
    }

    // Primary key values never change, so you shouldn’t allow modification of the identifier property value.
    // this makes the examples easier but in productive code you shouldn't need a setter
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getCourseId() == course.getCourseId() && Objects.equals(getName(), course.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(), getName());
    }
}
