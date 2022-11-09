package com.testehan.transactions.versioning.ex4ForcingVersionIncrement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

/*
What happens if two users place a bid for the same auction item at the same time?
When a user makes a new bid, the application must do several things:
1 Retrieve the currently highest Bid for the Item from the database.
2 Compare the new Bid with the highest Bid; if the new Bid is higher, it must be
stored in the database.
There is the potential for a race condition in between these two steps. If, in between
reading the highest Bid and placing the new Bid, another Bid is made, you won’t see
it. This conflict isn’t visible; even enabling versioning of the Item doesn’t help. The
Item is never modified during the procedure. Forcing a version increment of the Item
makes the conflict detectable.

*/


public class Main
{
    public static void main( String[] args )
    {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");

        EntityManager entityManager = null;
        Item item = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            item = new Item("Old Egiptian Statue");
            Bid bid = new Bid();
            bid.setItem(item);
            bid.setPrice(100l);
            item.getAttachments().add(bid);
            Bid bid2 = new Bid();
            bid2.setItem(item);
            bid2.setPrice(150l);
            item.getAttachments().add(bid2);

            entityManager.persist(item);
            System.out.println(item);

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

        calculateSizeOfAllAttachments(entityManagerFactory, item);

        entityManagerFactory.close();
    }

    private static void calculateSizeOfAllAttachments(EntityManagerFactory entityManagerFactory, Item item) {
        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            Item itemFromDB = entityManager.find(Item.class, item.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

            System.out.println(itemFromDB);
            Bid highestBid = getHighestBid(entityManagerFactory, item);

            // lets assume that this is a new bid...if it is not bigger than the already maximum one, we will throw an
            // exception and not persist this new bid, as we want our Item to only be linked to the valid Bids, and a
            // lowe price from a Bid makes that Bid not valid for the current Item
            Bid newBid = new Bid(400, item, highestBid);

            //A new row is inserted into the BID table. Hibernate wouldn’t detect concurrently made bids without a
            // forced version increment of the Item.
            entityManager.persist(newBid);

            /*
            When flushing the persistence context, Hibernate executes an INSERT for the new Bid
            and forces an UPDATE of the Item with a version check. If someone modified the Item
            concurrently or placed a Bid concurrently with this procedure, Hibernate throws an exception

            !!!
             Note that if instead of a Bid#item entity association with @ManyToOne, you have an
                @ElementCollection of Item#bids, adding a Bid to the collection will increment the
                Item version. The forced increment then isn’t necessary.
             */
            entityManager.getTransaction().commit();


            entityManager.close();

        } catch (InvalidBidException ex){
            System.out.println("!!!! Offered bid is smaller than the current highest Bid !!!!");
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }


    }

    private static Bid getHighestBid(EntityManagerFactory entityManagerFactory, Item item) {
        EntityManager entityManager = null;
        Bid highestBid = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

             highestBid =
                    (Bid) entityManager.createQuery("select b from Bid b where b.item.id = :itemId and b.price= (select max(b2.price) from Bid b2 where b2.item.id= :itemId)")
                            .setParameter("itemId", item.getId())
                            .getSingleResult();

            entityManager.getTransaction().commit();
            entityManager.close();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

       return highestBid;
    }

}
