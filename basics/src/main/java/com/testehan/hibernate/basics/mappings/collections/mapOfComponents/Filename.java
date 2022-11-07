package com.testehan.hibernate.basics.mappings.collections.mapOfComponents;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;


/*
If you want to use this class for the keys of a map, the mapped database columns can’t be nullable, because they’re all
part of a composite primary key. You also have to override the equals() and hashCode() methods, because the keys of a
map are a set, and each Filename must be unique within a given key set.
 */

@Embeddable
public class Filename {

    @Column(nullable = false)       // these 2 must be non null because they are part of the primary key
    protected String name;
    @Column(nullable = false)
    protected String extension;

    public Filename(){}

    public Filename(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filename)) return false;
        Filename filename = (Filename) o;
        return Objects.equals(getName(), filename.getName()) && Objects.equals(getExtension(), filename.getExtension());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getExtension());
    }

    @Override
    public String toString() {
        return "Filename{" +
                "name='" + name + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
