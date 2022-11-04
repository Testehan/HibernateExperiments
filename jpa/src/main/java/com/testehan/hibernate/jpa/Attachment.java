package com.testehan.hibernate.jpa;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Attachment {

    @Id
    @GeneratedValue
    private int attchmentId;

    private String attachmentFile;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Message message;

    public String getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(String attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return attchmentId == that.attchmentId && Objects.equals(attachmentFile, that.attachmentFile) && Objects.equals(message, that.message);
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attchmentId=" + attchmentId +
                ", attachmentFile='" + attachmentFile + '\'' +
                '}';
    }
}
