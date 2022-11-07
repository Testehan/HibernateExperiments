package com.testehan.hibernate.basics.mappings.relationships.manyToMany.intermediaryEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private int courseId;
    private String name;

    @OneToMany(mappedBy = "course")
    protected Set<CourseStudent> courseStudents = new HashSet<>();

    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CourseStudent> getCourseStudents() {
        return courseStudents;
    }

    public void setCourseStudents(Set<CourseStudent> courseStudents) {
        this.courseStudents = courseStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getCourseId() == course.getCourseId() && Objects.equals(getName(), course.getName()) && Objects.equals(getCourseStudents(), course.getCourseStudents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseId(), getName(), getCourseStudents());
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                '}';
    }


}
