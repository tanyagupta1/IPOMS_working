package com.desiseducare.models;

import javax.persistence.*;
import java.util.Date;

/**
 * a class that represents a Transactions object stored in db
 */
@Entity
@Table
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer transactionID;

    Integer userID;
    Date transactionDate;
    Float transactionAmount;

    public Transactions() {
    }

    public Transactions(Integer userID, Date transactionDate, Float transactionAmount) {
        this.userID = userID;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Integer getTransactionID() {
        return transactionID;
    }


    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }


    @Override
    public String toString() {
        return "Transactions{" +
                "transactionID=" + transactionID +
                ", userID=" + userID +
                ", transactionDate=" + transactionDate +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}
