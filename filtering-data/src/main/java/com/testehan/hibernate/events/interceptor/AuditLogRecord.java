package com.testehan.hibernate.events.interceptor;

import javax.persistence.*;
import java.util.Date;


// You want to store an instance of AuditLogRecord whenever Hibernate inserts or
//updates a Book in the database

@Entity
public class AuditLogRecord {

    @Id
    @GeneratedValue
    private long auditLogId;

    protected String message;

    protected Long entityId;

    protected Class entityClass;

    protected String username;  // user that perfomed the action that the auditLog object refers to

    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdOn = new Date();

    public AuditLogRecord() {}

    public AuditLogRecord(String message, Auditable entity, String currentUsername) {
        this.message = message;
        this.entityId = entity.getId();
        this.entityClass = entity.getClass();
        this.username= currentUsername;
    }

    public long getAuditLogId() {
        return auditLogId;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public String getUserId() {
        return username;
    }

    public void setUserId(String username) {
        this.username = username;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "AuditLogRecord{" +
                "auditLogId=" + auditLogId +
                ", message='" + message + '\'' +
                ", entityId=" + entityId +
                ", entityClass=" + entityClass +
                ", username=" + username +
                ", createdOn=" + createdOn +
                '}';
    }
}
