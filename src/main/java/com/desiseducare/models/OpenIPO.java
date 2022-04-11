package com.desiseducare.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * a class that represents a OpenIPO object stored in db
 */
@Entity
@Table
public class OpenIPO
{

    @Id
    Integer companyID;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;

    public OpenIPO(Integer companyID, Date startDate, Date endDate, Integer lotSize, Float floorPrice, Float capPrice, Integer totalNumberOfSharesOffered) {
        this.companyID = companyID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lotSize = lotSize;
        this.floorPrice = floorPrice;
        this.capPrice = capPrice;
        this.totalNumberOfSharesOffered = totalNumberOfSharesOffered;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date endDate;
    Integer lotSize;
    Float floorPrice;
    Float capPrice;
    Integer totalNumberOfSharesOffered;

    public OpenIPO()
    {
    }


    public Integer getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getLotSize() {
        return lotSize;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

    public Float getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(Float floorPrice) {
        this.floorPrice = floorPrice;
    }

    public Float getCapPrice() {
        return capPrice;
    }

    public void setCapPrice(Float capPrice) {
        this.capPrice = capPrice;
    }

    public Integer getTotalNumberOfSharesOffered() {
        return totalNumberOfSharesOffered;
    }

    public void setTotalNumberOfSharesOffered(Integer totalNumberOfSharesOffered) {
        this.totalNumberOfSharesOffered = totalNumberOfSharesOffered;
    }

    @Override
    public String toString() {
        return "openIPO{" +
                "companyID=" + companyID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", lotSize=" + lotSize +
                ", floorPrice=" + floorPrice +
                ", capPrice=" + capPrice +
                ", totalNumberOfSharesOffered=" + totalNumberOfSharesOffered +
                '}';
    }
}
