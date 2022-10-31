package com.testehan.hibernate.basics.mappings.embedded;

public class Weapon {

    private String name;

    private int dmg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "name='" + name + '\'' +
                ", dmg=" + dmg +
                '}';
    }
}
