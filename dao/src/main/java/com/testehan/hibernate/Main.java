package com.testehan.hibernate;

import com.testehan.hibernate.dao.BidDAO;
import com.testehan.hibernate.dao.BidDAOImpl;
import com.testehan.hibernate.dao.ItemDAO;
import com.testehan.hibernate.dao.ItemDAOImpl;
import com.testehan.hibernate.model.Bid;
import com.testehan.hibernate.model.Item;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");

        long firstItemId = insertItemsAndBidstudent(entityManagerFactory);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        ItemDAO itemDao = new ItemDAOImpl();
        ((ItemDAOImpl) itemDao).setEntityManager(entityManager);

        BidDAO bidDAO = new BidDAOImpl();
        ((BidDAOImpl) bidDAO).setEntityManager(entityManager);

        System.out.println("Print all items");
        List<Item> items = itemDao.findAll();
        for (Item i : items) {
            System.out.println(i);
        }

        System.out.println("Print items with name 'ItemA nr 1'");
        List<Item> items2 = itemDao.findByName("ItemA nr 1", false);
        for (Item i : items2) {
            System.out.println(i);
        }

        System.out.println("Find item with id " + firstItemId);
        Item item = itemDao.findById(firstItemId);
        System.out.println(item);
        System.out.println("Find reference with id " + firstItemId);
        Item item1 = itemDao.findReferenceById(firstItemId);
        System.out.println(item1);
        item1.setName("ToBeDeleted");
        itemDao.makePersistent(item1);
        System.out.println("Number of elements in the DB is : " + itemDao.getCount());

        Item item2 = new Item();
        item2.setName("Maradona Tshirt");
        item2 = itemDao.makePersistent(item2);

        Bid bid = new Bid();
        bid.setPrice(10f);
        bid.setItem(item2);
        System.out.println("Persisting new bid : ");
        bid = bidDAO.makePersistent(bid);
        System.out.println(bid);

        System.out.println("Making transient the following item " + item) ;
        itemDao.makeTransient(item);
    }

    private static long insertItemsAndBidstudent(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Inside insertItemsAndBidstudent ------------------");
        EntityManager entityManager = null;
        long firstItemId = 0;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Item item = new Item();
            item.setName("ItemA nr 1");

            Item item2 = new Item();
            item2.setName("ItemB nr 2");

            Item item3 = new Item();
            item3.setName("ItemC nr 2");

            Bid bid = new Bid();
            bid.setPrice(101.53f);
            bid.setItem(item);

            Bid bid2 = new Bid();
            bid2.setPrice(105.00f);
            bid2.setItem(item);

            Bid bid3 = new Bid();
            bid3.setPrice(125.53f);
            bid3.setItem(item);
            item.getBids().add(bid);
            item.getBids().add(bid2);
            item.getBids().add(bid3);

            Bid bid4 = new Bid();
            bid4.setPrice(50f);
            bid4.setItem(item2);
            item2.getBids().add(bid4);


            entityManager.persist(item);
            firstItemId = item.getId();
            entityManager.persist(item2);
            entityManager.persist(item3);

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

        return firstItemId;
    }
}
