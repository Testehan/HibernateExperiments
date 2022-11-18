package com.testehan.hibernate.query.join;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

public class Main {

    public static void main(String[] args)
    {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();


            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }


        insertItemsAndBidstudent(entityManagerFactory);

        thetaStyleJoin(entityManagerFactory);                              // 4
//        dynamicFetchingJoins(entityManagerFactory);                         //3
//        explicitAssociationJoin(entityManagerFactory);                    // 2
//        implicitAssociationJoin(entityManagerFactory);                    // 1

        entityManagerFactory.close();
    }

    /*
        theta-style syntax is useful when your join condition isn’t a foreign key relationship mapped to a class association.
        For example, the classes Artifact and Item don’t know anything about each other, because they aren’t associated.

        You probably won’t need to use the theta-style joins often. Note that it’s currently not
        possible in JPA to outer join two tables that don’t have a mapped association—thetastyle joins are inner joins.
     */
    private static void thetaStyleJoin(EntityManagerFactory entityManagerFactory) {
        System.out.println("Inside thetaStyleJoin ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();


            // Method 1
            Query select = entityManager.createQuery("select i, a from Item i, Artifact a where i.name = a.name");
            List<Object[]> rows = select.getResultList();
            for (Object[] row : rows){
                System.out.println(row[0]);
                System.out.println(row[1]);
            }

            // Method 2
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> criteria = criteriaBuilder.createQuery();
            Root<Item> item = criteria.from(Item.class);
            Root<Artifact> artifact = criteria.from(Artifact.class);
            criteria.where(criteriaBuilder.equal(item.get("name"),artifact.get("name")));
            criteria.multiselect(item,artifact);

            Query query = entityManager.createQuery(criteria);
            rows = query.getResultList();
            for (Object[] row : rows){
                System.out.println(row[0]);
                System.out.println(row[1]);
            }

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    /*
        All the queries you saw in the previous sections have one thing in common: the
        returned Item instances have a collection named bids. This @OneToMany collection, if
        mapped as FetchType.LAZY (the default for collections), isn’t initialized, and an additional SQL statement is
        triggered as soon as you access it. The same is true for all single-valued associations, like the @ManyToOne
        association seller of each Item. By default, Hibernate generates a proxy and loads the associated User instance lazily and
        only on demand.

         Eager fetching of associated data is possible with the FETCH keyword in JPQL and the fetch() method in the
         criteria query API:
     */
    private static void dynamicFetchingJoins(EntityManagerFactory entityManagerFactory) {
        System.out.println("Inside dynamicFetchingJoins ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            // This query returns a List<Item>; each Item instance has its bids collection fully
            //initialized. This is different than the ordered pairs returned by the queries in the previous section! (methods)

            // Method 1
            TypedQuery select = entityManager.createQuery("select i from Item i left join fetch i.bids",Item.class);
            List<Item> items = select.getResultList();
            for (Item item : items){
                System.out.println(item);
            }

            // Method 2
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Item> criteria = criteriaBuilder.createQuery(Item.class);
            Root<Item> item = criteria.from(Item.class);
            item.fetch("bids", JoinType.LEFT);
            criteria.select(item);

            TypedQuery<Item> query = entityManager.createQuery(criteria);
            items = query.getResultList();
            for (Item i : items){
                System.out.println(i);
            }

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    private static void explicitAssociationJoin(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Inside explicitAssociationJoin ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

// LEFT OUTER JOIN !!

            // the following query and retrieves items that have no bids, and items with bids of a minimum bid
            //amount of 100

            // Method 1
            Query select = entityManager.createQuery("select i, b from Item i left join i.bids b on b.price > 100");
            List<Object> results = select.getResultList();
            printItemBidResults(results,true);

            // Method 2
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> criteria = criteriaBuilder.createQuery(Object.class);

            Root<Item> i = criteria.from(Item.class);
            Join<Item, Bid> b = i.join("bids", JoinType.LEFT);
            b.on(criteriaBuilder.gt(b.get("price"), 100f));
            criteria.multiselect(i, b);

            TypedQuery<Object> query = entityManager.createQuery(criteria);
            results = query.getResultList();
            printItemBidResults(results,true);

// RIGHT OUTER JOIN !!

            // Method 1
            Query select2 = entityManager.createQuery("select b, i from Bid b right outer join b.item i where b is null or b.price > 100");
            results = select2.getResultList();
            printItemBidResults(results,false);

            // Method 2
            CriteriaBuilder criteriaBuilder2 = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> criteria2 = criteriaBuilder2.createQuery(Object.class);

            Root<Bid> bi = criteria2.from(Bid.class);
            // does not work...it throws UnsupportedOperationException : right join not supported
            // found in the hibernate documentation this : "Relation joins can be applied to many-to-one and
            // one-to-one mappings only when using JoinType.LEFT or JoinType.INNER."
            Join<Bid,Item> it = bi.join("item", JoinType.RIGHT);
            criteria2.multiselect(bi, it).where(
                    criteriaBuilder2.or(
                            criteriaBuilder2.isNull(bi),
                            criteriaBuilder2.gt(bi.get("price"),100f)
                    )
            );


            TypedQuery<Object> query2 = entityManager.createQuery(criteria2);
            results = query2.getResultList();
            printItemBidResults(results,false);


            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }

    // don't judge me on my pretty text styling from below ;) ; if leftOuterJoin is false I assume right outer join is used
    private static void printItemBidResults(List<Object> results, boolean leftOuterJoin) {
        if (leftOuterJoin) {
            System.out.println("Item id  Item name     Bid id     Bid price");
        } else {
            System.out.println("Bid id     Bid price     Item id  Item name ");
        }

        for (Object result : results) {
            Object[] tuple = (Object[]) result;
            Item item;
            Bid bid;
            if (leftOuterJoin) {
                item = tuple[0] != null ? (Item) tuple[0] : null;
                bid = tuple[1] != null ? (Bid) tuple[1] : null;
            } else {
                item = tuple[1] != null ? (Item) tuple[1] : null;
                bid = tuple[0] != null ? (Bid) tuple[0] : null;
            }

            if (leftOuterJoin) {
                System.out.print(item != null ? item.getId() + "        " : null + "        ");
                System.out.print(item != null ? item.getName() + "        " : null + "        ");
                System.out.print(bid != null ? bid.getBidId() + "        " : null + "        ");
                System.out.println(bid != null ? bid.getPrice() + "        " : null + "        ");
            } else {
                System.out.print(bid != null ? bid.getBidId() + "        " : null + "        ");
                System.out.print(bid != null ? bid.getPrice() + "        " : null + "        ");
                System.out.print(item != null ? item.getId() + "        " : null + "        ");
                System.out.println(item != null ? item.getName() + "        " : null + "        ");
            }
        }
    }


    /*
        In JPA queries, you don’t have to specify a join condition explicitly. Rather, you specify
        the name of a mapped Java class association.

        For example, the Bid entity class has a mapped many-to-one association named item, with the Item entity
        class. If you refer to this association in a query, Hibernate has enough information to deduce the join
        expression with a key column comparison. This helps make queries less verbose and more readable.

        The path b.item.name creates an implicit join on the many-to-one associations from Bid
        to Item—the name of this association is item. Hibernate knows that you mapped this
        association with the ITEM_ID foreign key in the BID table and generates the SQL join
        condition accordingly. Implicit joins are always directed along many-to-one or one-to-one
        associations, never through a collection-valued association (you can’t write item.bids.amount).
     */
    private static void implicitAssociationJoin(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Inside implicitAssociationJoin ------------------");
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            // Method 1
            TypedQuery select = entityManager.createQuery("select b from Bid b where b.item.name like 'ItemA%'",Bid.class);
            List<Bid> bids = select.getResultList();
            for (Bid b : bids){
                System.out.println(b);
            }

            // Method 2
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Bid> criteria = criteriaBuilder.createQuery(Bid.class);
            Root<Bid> b = criteria.from(Bid.class);
            criteria.select(b).where(criteriaBuilder.like(b.get("item").get("name"), "ItemA%"));
            TypedQuery<Bid> query = entityManager.createQuery(criteria);
            bids = query.getResultList();
            for (Bid bid : bids){
                System.out.println(bid);
            }

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }
    }


    private static int insertItemsAndBidstudent(final EntityManagerFactory entityManagerFactory)
    {
        System.out.println("Inside insertItemsAndBidstudent ------------------");
        EntityManager entityManager = null;
        int studentId = 0;
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

            Artifact a1 = new Artifact();
            a1.setName("ItemB nr 2");
            Artifact a2 = new Artifact();
            a2.setName("Tutankamon jewls");

            entityManager.persist(item);
            entityManager.persist(item2);
            entityManager.persist(item3);
            entityManager.persist(a1);
            entityManager.persist(a2);

            entityManager.getTransaction().commit();

        } catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("!!!! " + e);
        } finally {
            if (entityManager != null && entityManager.isOpen())
                entityManager.close();
        }

        return studentId;
    }
}
