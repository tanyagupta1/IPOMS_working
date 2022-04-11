package com.desiseducare.models;

import javax.persistence.*;

/**
 * a class that represents a ThirdPartyPlatform object stored in db
 */
@Entity
@Table
public class ThirdPartyPlatform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer platformID;
    String platformName;

    public ThirdPartyPlatform() {
    }

    public Integer getPlatformID() {
        return platformID;
    }

    public void setPlatformID(Integer platformID) {
        this.platformID = platformID;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    @Override
    public String toString() {
        return "ThirdPartyPlatform{" +
                "platformID=" + platformID +
                ", platformName='" + platformName + '\'' +
                '}';
    }
}
