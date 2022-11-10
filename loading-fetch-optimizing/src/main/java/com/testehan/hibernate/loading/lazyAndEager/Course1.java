package com.testehan.hibernate.loading.lazyAndEager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course1 {

    @Id
    @GeneratedValue
    private int courseId;
    private String name;
    @ManyToMany
    private List<Student1> students = new ArrayList();

    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student1> getStudents() {
        return students;
    }

    public void setStudents(List<Student1> students) {
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
