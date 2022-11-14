package com.testehan.hibernate.entityManagerMethods;

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

    public int getAttchmentId() {
        return attchmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attachment)) return false;
        Attachment that = (Attachment) o;
        return getAttchmentId() == that.getAttchmentId() && Objects.equals(getAttachmentFile(), that.getAttachmentFile()) && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttchmentId(), getAttachmentFile(), getMessage());
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attchmentId=" + attchmentId +
                ", attachmentFile='" + attachmentFile + '\'' +
                '}';
    }
}
