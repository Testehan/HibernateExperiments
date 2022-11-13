package com.testehan.hibernate.events.jpaListenerCallback;

import javax.persistence.*;

public class PersistEntityListener {

    @PostPersist
    public void calledAfterEntityWasPersisted(Object entityInstance) {
        System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("Entiy persisted " + entityInstance);
    }

    // Next 2 methods are "triggered only when the state of the entity requires synchronization
    // (for example, because it’s considered dirty).
    @PreUpdate
    public void calledBeforePersistenceContextIsSynchronizedWithDB(Object entityInstance) {
        System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("persistence context is synched with DB for " + entityInstance);
    }

    @PostUpdate
    public void calledAfterPersistenceContextIsFlushed(Object entityInstance) {
        System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("persistance context was flushed for " + entityInstance);
    }

    @PreRemove
    public void calledBeforeRemoval(Object entityInstance) {
        System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("before removal of " + entityInstance);
    }

    @PostRemove
    public void calledAfterRemoval(Object entityInstance) {
        System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("after removal of " + entityInstance);
    }
}
