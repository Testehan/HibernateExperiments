package com.testehan.hibernate.loading.lazyAndEager;

import javax.persistence.*;

@Entity
public class Notebook1 {

    @Id
    @GeneratedValue
    private long notebookId;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "NOTEBOOK_STUDENT",
            joinColumns = @JoinColumn(name = "NOTEBOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "studentId", nullable = false)
    )
    private Student1 student;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student1 getStudent() {
        return student;
    }

    public void setStudent(Student1 student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Notebook{" +
                "notebookId=" + notebookId +
                ", name='" + name + '\'' +
                '}';
    }
}
