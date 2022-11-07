package com.testehan.hibernate.basics.mappings.collections.mapOfComponents;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/*
A composite embeddable class like Image isn’t limited to simple properties of basic
type. You’ve already seen how you can nest other components, such as City in
Address. You could extract and encapsulate the width and height properties of Image
in a new Dimensions class.
 */
@Embeddable
public class Image {

    @Column(nullable = false)
    protected String title;

    protected int width;

    protected int height;

    public Image(){}

    public Image(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        Image image = (Image) o;
        return getWidth() == image.getWidth() && getHeight() == image.getHeight() && Objects.equals(getTitle(), image.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getWidth(), getHeight());
    }

    @Override
    public String toString() {
        return "Image{" +
                "title='" + title + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
