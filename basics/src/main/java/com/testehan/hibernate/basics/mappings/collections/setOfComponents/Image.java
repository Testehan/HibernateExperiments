package com.testehan.hibernate.basics.mappings.collections.setOfComponents;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Image {

    @Column(nullable = false)
    protected String title;
    @Column(nullable = false)
    protected String filename;

    protected int width;

    protected int height;

    public Image(){}

    public Image(String title, String filename, int width, int height) {
        this.title = title;
        this.filename = filename;
        this.width = width;
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return width == image.width && height == image.height && title.equals(image.title) && filename.equals(image.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, filename, width, height);
    }

    @Override
    public String toString() {
        return "Image{" +
                "title='" + title + '\'' +
                ", filename='" + filename + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
