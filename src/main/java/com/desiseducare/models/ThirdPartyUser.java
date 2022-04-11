package com.desiseducare.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * a class that represents a ThirdPartyUser object stored in db
 */
@Entity
@Table


public class ThirdPartyUser {
    @Id
    Integer userID;
    Integer platformID;

    @Column(unique = true)
    Integer dematAccountNumber;

    public ThirdPartyUser() {
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getPlatformID() {
        return platformID;
    }

    public void setPlatformID(Integer platformID) {
        this.platformID = platformID;
    }

    public Integer getDematAccountNumber() {
        return dematAccountNumber;
    }

    public void setDematAccountNumber(Integer dematAccountNumber) {
        this.dematAccountNumber = dematAccountNumber;
    }

    @Override
    public String toString() {
        return "ThirdPartyUser{" +
                "userID=" + userID +
                ", platformID=" + platformID +
                ", dematAccountNumber=" + dematAccountNumber +
                '}';
    }
}
