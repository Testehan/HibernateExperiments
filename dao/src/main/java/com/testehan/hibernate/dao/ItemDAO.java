package com.testehan.hibernate.dao;

import com.testehan.hibernate.model.Item;

import java.util.List;

public interface ItemDAO extends GenericDAO<Item, Long>{

    List<Item> findAll(boolean withBids);

    List<Item> findByName(String name, boolean substring);
}
