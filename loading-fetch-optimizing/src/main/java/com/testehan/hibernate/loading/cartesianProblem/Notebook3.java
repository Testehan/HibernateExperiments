package com.testehan.hibernate.loading.cartesianProblem;

import javax.persistence.*;

@Entity
public class Notebook3 {

    @Id
    @GeneratedValue
    private long notebookId;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "NOTEBOOK_STUDENT_3",
            joinColumns = @JoinColumn(name = "NOTEBOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "studentId", nullable = false)
    )
    private Student3 student;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student3 getStudent() {
        return student;
    }

    public void setStudent(Student3 student) {
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
