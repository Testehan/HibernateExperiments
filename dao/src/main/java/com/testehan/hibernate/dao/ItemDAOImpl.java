package com.testehan.hibernate.dao;

import com.testehan.hibernate.model.Item;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ItemDAOImpl extends GenericDAOImpl<Item, Long> implements ItemDAO{

    public ItemDAOImpl() {
        super(Item.class);
    }

    @Override
    public List<Item> findAll(boolean withBids) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> criteria = cb.createQuery(Item.class);
        criteria.select(criteria.from(Item.class));
        TypedQuery<Item> query = em.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Item> findByName(String name, boolean substring) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> criteria = cb.createQuery(Item.class);
        Root<Item> item = criteria.from(Item.class);

        if (substring){
            criteria.where(cb.like(item.get("name"),name+"%"));
        } else {
            criteria.where(cb.equal(item.get("name"), name));
        }
        TypedQuery<Item> query = em.createQuery(criteria);
        return query.getResultList();
    }
}
