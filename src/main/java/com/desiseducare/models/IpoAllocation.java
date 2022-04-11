package com.desiseducare.models;

import javax.persistence.*;

/**
 * a class that represents a IPOAllocation stored in db
 */
@Entity
@Table
public class IpoAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long allocationId;
    Integer bidID;
    Integer companyId;
    Integer userId;
    String userType;
    Integer noOfSharesAllocated;
    Integer platformID;
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getNoOfSharesAllocated() {
        return noOfSharesAllocated;
    }

    public void setNoOfSharesAllocated(Integer noOfSharesAllocated) {
        this.noOfSharesAllocated = noOfSharesAllocated;
    }

    public Long getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Long allocationId) {
        this.allocationId = allocationId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public IpoAllocation(Integer companyId, Integer userId, String userType, Integer noOfSharesAllocated, Integer platformID, Integer bidID) {
        this.companyId = companyId;
        this.userId = userId;
        this.userType = userType;
        this.noOfSharesAllocated = noOfSharesAllocated;
        this.platformID = platformID;
        this.bidID = bidID;
    }

    public Integer getPlatformID() {
        return platformID;
    }

    public void setPlatformID(Integer platformID) {
        this.platformID = platformID;
    }

    public Integer getBidID() {
        return bidID;
    }

    public void setBidID(Integer bidID) {
        this.bidID = bidID;
    }

    public IpoAllocation() {
    }
}