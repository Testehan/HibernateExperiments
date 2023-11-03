package com.testehan.hibernate.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {

    T findById(ID id);

    T findReferenceById(ID id);

    List<T> findAll();

    Long getCount();

    T makePersistent(T entity);

    void makeTransient(T entity);

}
