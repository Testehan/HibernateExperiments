package com.testehan.hibernate.caching.level2;

import javax.persistence.Embeddable;

@Embeddable
public class AlienName {

    private String firstName;
    private String lastName;
    private String middleName;

    public AlienName(){}
    public AlienName(String firstName, String lastName, String middleName){
        this.firstName = firstName;
        this.lastName= lastName;
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String toString() {
        return "AlienName{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                '}';
    }
}
