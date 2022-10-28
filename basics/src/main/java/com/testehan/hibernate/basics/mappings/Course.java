package com.testehan.hibernate.basics.mappings;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    private int courseId;
    private String name;
    @ManyToMany
    private List<Student> students = new ArrayList();

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                '}';
    }
}
