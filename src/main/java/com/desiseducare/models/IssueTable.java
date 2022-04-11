package com.desiseducare.models;

public class IssueTable
{
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getValue() {
        return noOfShares;
    }

    public void setValue(long noOfShares) {
        this.noOfShares = noOfShares;
    }

    float price;
    long noOfShares;

    @Override
    public String toString()
    {
        return "issueTable{" +
                "price=" + price +
                ", noOfShares=" + noOfShares +
                '}';
    }

    public IssueTable(float price, long noOfShares)
    {
        this.price = price;
        this.noOfShares = noOfShares;
    }
}
