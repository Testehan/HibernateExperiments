package com.testehan.hibernate.loading.lazyAndEager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
    Hibernate will now no longer generate a proxy for the NoProxy entity. If you call
    entityManager.getReference(NoProxy.class, ID), a SELECT is executed, just as for find():
*/

@org.hibernate.annotations.Proxy(lazy = false)
@Entity
public class NoProxy {

    @Id
    @GeneratedValue
    private long noProxyId;
}
