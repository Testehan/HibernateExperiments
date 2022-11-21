package com.testehan.hibernate.caching.level2;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alien_table")
@Cacheable
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class Alien {

    @Id
    @GeneratedValue
    private int alienId;
    @Transient
    private int notInDb;
    private AlienName name;
    @Column(name = "alien_colour")
    private String colour;

    @OneToMany(mappedBy = "alienOwner")
    @org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
    private Set<Weapon> weapons = new HashSet<>();

    public Set<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(Set<Weapon> weapons) {
        this.weapons = weapons;
    }

    public int getAlienId() {
        return alienId;
    }

    public AlienName getName() {
        return name;
    }

    public void setName(AlienName name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getNotInDb() {
        return notInDb;
    }

    public void setNotInDb(int notInDb) {
        this.notInDb = notInDb;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "alienId=" + alienId +
                ", notInDb=" + notInDb +
                ", name=" + name +
                ", colour='" + colour + '\'' +
                '}';
    }
}
