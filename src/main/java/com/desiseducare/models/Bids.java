package com.desiseducare.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * a class that represents a bid object stored in db
 */
@Entity
@Table
public class Bids {

    Integer userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer bidId;
    String userType;
    Integer companyId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date bidDate;
    Integer NumberOfLotsSubscribed;
    Float bidPrice;
    Float totalPrice;
    Integer PlatformID=1;

    public Bids() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getBidDate() {
        return bidDate;
    }

    public void setBidDate(Date bidDate) {
        this.bidDate = bidDate;
    }

    public Integer getNumberOfLotsSubscribed() {
        return NumberOfLotsSubscribed;
    }

    public void setNumberOfLotsSubscribed(Integer numberOfLotsSubscribed) {
        NumberOfLotsSubscribed = numberOfLotsSubscribed;
    }

    public Float getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Float bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Integer getPlatformID() {
        return PlatformID;
    }

    public void setPlatformID(Integer platformID) {
        PlatformID = platformID;
    }


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Bids{" +
                "userId=" + userId +
                ", bidId=" + bidId +
                ", userType='" + userType + '\'' +
                ", companyId=" + companyId +
                ", bidDate=" + bidDate +
                ", NumberOfLotsSubscribed=" + NumberOfLotsSubscribed +
                ", bidPrice=" + bidPrice +
                ", PlatformID=" + PlatformID +
                '}';
    }

}
