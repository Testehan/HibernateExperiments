package com.testehan.hibernate.query.basics;

/*
    Let’s say you have a reporting screen in your application where you need to show
    some data in a list. You want to show all auction items and when each auction ends.
    You don’t want to load managed Item entity instances, because no data will be modified: you only read data.

    This is where DataTransferObjects or DTOS come in handy

    We sometimes call these kinds of classes data transfer objects (DTOs), because their main
    purpose is to shuttle data around in the application. The ItemSummary class isn’t
    mapped to the database, and you can add arbitrary methods (getter, setter, printing of
    values) as needed by your reporting user interface.
*/

public class StudentSummaryDTO {

    private String studentName;
    private int studentMark;

    public StudentSummaryDTO(String studentName, int studentMark) {
        this.studentName = studentName;
        this.studentMark = studentMark;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentMark() {
        return studentMark;
    }

    public void setStudentMark(int studentMark) {
        this.studentMark = studentMark;
    }

    @Override
    public String toString() {
        return "StudentSummaryDTO{" +
                "studentName='" + studentName + '\'' +
                ", studentMark='" + studentMark + '\'' +
                '}';
    }
}
