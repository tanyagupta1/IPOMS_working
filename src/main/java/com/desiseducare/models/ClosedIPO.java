package com.desiseducare.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * a class that represents a closedIPO object stored in db
 */
@Entity
@Table
public class ClosedIPO {

    @Id
    Integer companyID;
    Date startDate;

    @Override
    public String toString() {
        return "ClosedIPO{" +
                "companyID=" + companyID +
                ", startDate=" + startDate +
                ", closedDate=" + closedDate +
                ", lotSize=" + lotSize +
                ", floorPrice=" + floorPrice +
                ", capPrice=" + capPrice +
                ", totalNumberOfSharesOffered=" + totalNumberOfSharesOffered +
                ", cutoff=" + cutoff +
                ", numberOfInvestorsAllottedIPO=" + numberOfInvestorsAllottedIPO +
                ", RIIPercentage=" + RIIPercentage +
                ", NIIPercentage=" + NIIPercentage +
                ", QBIPercentage=" + QIBPercentage +
                '}';
    }

    Date closedDate;
    Integer lotSize;
    Float floorPrice;
    Float capPrice;
    Integer totalNumberOfSharesOffered;
    Float cutoff;
    Integer numberOfInvestorsAllottedIPO;
    //for IPO allocation
    Float RIIPercentage;
    Float NIIPercentage;
    Float QIBPercentage;
    String allocationInfo="";
    public String getAllocationInfo() {
        return allocationInfo;
    }

    public void setAllocationInfo(String allocationInfo)
    {
        if(allocationInfo.isBlank())
        this.allocationInfo += allocationInfo;
    }
    public Float getRIIPercentage() {
        return RIIPercentage;
    }

    public void setRIIPercentage(Float RIIPercentage) {
        this.RIIPercentage = RIIPercentage;
    }

    public Float getNIIPercentage() {
        return NIIPercentage;
    }

    public void setNIIPercentage(Float NIIPercentage) {
        this.NIIPercentage = NIIPercentage;
    }

    public Float getQIBPercentage() {
        return QIBPercentage;
    }

    public void setQIBPercentage(Float QIBPercentage) {
        this.QIBPercentage = QIBPercentage;
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

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
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

    public Float getCutoff() {
        return cutoff;
    }

    public void setCutoff(Float cutoff) {
        this.cutoff = cutoff;
    }

    public Integer getNumberOfInvestorsAllottedIPO() {
        return numberOfInvestorsAllottedIPO;
    }

    public void setNumberOfInvestorsAllottedIPO(Integer numberOfInvestorsAllottedIPO) {
        this.numberOfInvestorsAllottedIPO = numberOfInvestorsAllottedIPO;
    }

    public void copyToClosedIPO(OpenIPO openIPO)
    {
        this.companyID=openIPO.getCompanyID();
        this.startDate=openIPO.getStartDate();
        this.closedDate=openIPO.getEndDate();
        this.floorPrice=openIPO.getFloorPrice();
        this.capPrice=openIPO.getCapPrice();
        this.lotSize=openIPO.getLotSize();
        this.totalNumberOfSharesOffered=openIPO.getTotalNumberOfSharesOffered();

    }
}
