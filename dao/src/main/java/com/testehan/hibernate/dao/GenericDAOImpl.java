package com.testehan.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    protected EntityManager em;

    protected final Class<T> entityClass;

    protected GenericDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public T findById(ID id) {
        return executeInsideTransaction(em -> em.find(entityClass, id));
    }

    public T findReferenceById(ID id) {
        return executeInsideTransaction(em -> em.getReference(entityClass, id));
    }

    public List<T> findAll() {
        CriteriaQuery<T> c = em.getCriteriaBuilder().createQuery(entityClass);
        c.select(c.from(entityClass));
        return em.createQuery(c).getResultList();
    }
    public Long getCount() {
        CriteriaQuery<Long> c = em.getCriteriaBuilder().createQuery(Long.class);
        c.select(em.getCriteriaBuilder().count(c.from(entityClass)));
        return em.createQuery(c).getSingleResult();
    }

    public T makePersistent(T instance) {
        // merge() handles transient AND detached instances
        return executeInsideTransaction(em -> em.merge(instance));
    }
    public void makeTransient(T instance) {
        executeInsideTransactionVoid(em -> em.remove(instance));
    }

    protected T executeInsideTransaction(Function<EntityManager, T> action) {
        EntityTransaction tx = em.getTransaction();
        T result;
        try {
            tx.begin();
            result = action.apply(em);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
        return result ;
    }

    protected void executeInsideTransactionVoid(Consumer<EntityManager> action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
             action.accept(em);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
