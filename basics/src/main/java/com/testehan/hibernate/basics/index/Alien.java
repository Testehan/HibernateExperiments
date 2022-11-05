package com.testehan.hibernate.basics.index;

import javax.persistence.*;

@Entity
@Table(name = "alien_table",
        indexes = {
                @Index(
                        name = "IDX_name",
                        columnList = "name"
                ),
                @Index(
                        name = "IDX_name_email",
                        columnList = "name, email"
                )
        }
 )
public class Alien {

    @Id
    @GeneratedValue()
    private long alienId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    public Alien() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "alienId=" + alienId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
