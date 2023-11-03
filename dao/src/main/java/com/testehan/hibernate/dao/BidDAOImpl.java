package com.testehan.hibernate.dao;

import com.testehan.hibernate.model.Bid;

public class BidDAOImpl extends GenericDAOImpl<Bid, Long> implements BidDAO{

    public BidDAOImpl() {
        super(Bid.class);
    }
}
