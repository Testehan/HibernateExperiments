package com.testehan.transactions.versioning.ex3Manual;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Attachment {

    @Id
    @GeneratedValue
    private int attchmentId;

    private String attachmentFile;

    private long size;

    @Version
    private long version;

    @ManyToOne
    @JoinColumn(name = "email_id", nullable = false)
    private Email email;

    public String getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(String attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public int getAttchmentId() {
        return attchmentId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attachment)) return false;
        Attachment that = (Attachment) o;
        return getAttchmentId() == that.getAttchmentId() && getSize() == that.getSize() && Objects.equals(getAttachmentFile(), that.getAttachmentFile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttchmentId(), getAttachmentFile(), getSize());
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attchmentId=" + attchmentId +
                ", attachmentFile='" + attachmentFile + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
