package com.testehan.transactions.versioning.ex4ForcingVersionIncrement;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Bid {

    @Id
    @GeneratedValue
    private int bidId;

    private long price;

    @Version
    private long version;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Bid() {}

    public Bid(long price, Item item, Bid highestBid) {
        this.price = price;
        this.item = item;

        if (highestBid.getPrice() > price){
            throw new InvalidBidException();
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getBidId() {
        return bidId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bid)) return false;
        Bid that = (Bid) o;
        return getBidId() == that.getBidId() && getPrice() == that.getPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBidId(), getPrice());
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attchmentId=" + bidId +
                ", price='" + price + '\'' +
                '}';
    }
}
