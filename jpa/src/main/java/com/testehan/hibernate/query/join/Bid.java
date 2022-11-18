package com.testehan.hibernate.query.join;

import javax.persistence.*;

@Entity
public class Bid {

    @Id
    @GeneratedValue
    private int bidId;

    private float price;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public int getBidId() {
        return bidId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "bidId=" + bidId +
                ", price=" + price +
                '}';
    }
}
