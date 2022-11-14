package com.testehan.hibernate.events.interceptor;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AuditLogInterceptor extends EmptyInterceptor {

    private Session currentSession;
    private String currentUsername;
    private Set<Auditable> inserts = new HashSet();
    private Set<Auditable> updates = new HashSet();


    public void setCurrentSession(Session session) {
        this.currentSession = session;
    }
    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    // This method is called when an entity instance is made persistent.
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, org.hibernate.type.Type[] types) {
        if (entity instanceof Auditable)
            inserts.add((Auditable)entity);
        return false;
    }

    //This method is called when an entity instance is detected as dirty during flushing of
    //the persistence context
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, org.hibernate.type.Type[] types) {
        if (entity instanceof Auditable)
            updates.add((Auditable)entity);

        return false;
    }


    //This method is called after flushing of the persistence context is complete. Here, you
    //write the audit log records for all insertions and updates you collected earlier
    @Override
    public void postFlush(Iterator entities) {
        // we need to create a temp session, because otherwise we will get stackoverflow..This happens because
        // when we do the persists in this method, the interceptor onSave method will be triggered and then again
        // this method. This is why we need a fresh session here, one that does not have the interceptor set on it
        // Only currentSession has the interceptor set on it..
        try (Session tempSession = currentSession.sessionWithOptions().openSession())
        {
            tempSession.getTransaction().begin();
            for (Auditable entity : inserts) {
                tempSession.persist(new AuditLogRecord("insert", entity, currentUsername));
            }
            for (Auditable entity : updates) {
                tempSession.persist(new AuditLogRecord("update", entity, currentUsername));
            }

            tempSession.getTransaction().commit();

        } finally {
            inserts.clear();
            updates.clear();
        }
    }
}
