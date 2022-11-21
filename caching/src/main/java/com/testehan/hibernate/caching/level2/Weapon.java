package com.testehan.hibernate.caching.level2;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class Weapon {

    @Id
    @GeneratedValue
    private int weaponId;

    private String weaponName;

    private int dmg;

    @ManyToOne
    @JoinColumn(name = "alienOwnerId")
    private Alien alienOwner;

    public Alien getAlienOwner() {
        return alienOwner;
    }

    public void setAlienOwner(Alien alienOwner) {
        this.alienOwner = alienOwner;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "weaponId=" + weaponId +
                ", weaponName='" + weaponName + '\'' +
                ", dmg=" + dmg +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weapon)) return false;
        Weapon weapon = (Weapon) o;
        return weaponId == weapon.weaponId && getDmg() == weapon.getDmg() && Objects.equals(getWeaponName(), weapon.getWeaponName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(weaponId, getWeaponName(), getDmg());
    }
}
