package com.testehan.transactions.versioning.ex3Manual;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


 @Entity
public class Email {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String text;

    @OneToMany(mappedBy = "email", fetch=FetchType.EAGER,  cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList();

    public Email() {}

    public Email(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email = (Email) o;
        return Objects.equals(getId(), email.getId()) && Objects.equals(getText(), email.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getText());
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}