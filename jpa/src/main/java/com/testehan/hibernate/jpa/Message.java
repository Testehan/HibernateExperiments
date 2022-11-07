package com.testehan.hibernate.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
Hibernate never needs to execute UPDATE statements on the Message table. Hibernate can also make a few other
optimizations, such as avoiding dirty checking, if you map an immutable class like below
*/
 @Entity
// @Immutable
public class Message {
    @Id
    @GeneratedValue
    private Long id;
//    @Basic(optional = false)    or
    @Column(nullable = false)
    private String text;

    @OneToMany(mappedBy = "message", fetch=FetchType.EAGER,  cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList();

    public Message() {}

    public Message(String text) {
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
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(getId(), message.getId()) && Objects.equals(getText(), message.getText());
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