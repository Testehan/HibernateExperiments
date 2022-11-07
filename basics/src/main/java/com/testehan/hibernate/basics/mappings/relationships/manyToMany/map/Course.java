package com.testehan.hibernate.basics.mappings.relationships.manyToMany.map;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private int courseId;
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @MapKeyJoinColumn(name = "STUDENT_ID")
    @JoinTable(
            name = "COURSE_STUDENT",
            joinColumns = @JoinColumn(name = "COURSE_ID"),
            inverseJoinColumns = @JoinColumn(name = "TEACHER_ID")
    )
    protected Map<Student , Teacher> studentsAddedBy = new HashMap<>();

    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Student, Teacher> getStudentsAddedBy() {
        return studentsAddedBy;
    }

    public void setStudentsAddedBy(Map<Student, Teacher> studentsAddedBy) {
        this.studentsAddedBy = studentsAddedBy;
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
