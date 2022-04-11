package com.desiseducare.models;

import javax.persistence.*;

/**
 * a class that represents a company object stored in db
 */
@Entity
@Table
public class Company {

    @Column(unique = true)
    String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer companyId;

    String password;
    Boolean satisfiesRequirements;

    public Company(String name)
    {
        this.name = name;
    }

    public Company(String name, Integer companyId, String password, Boolean satisfiesRequirements) {
        this.name = name;
        this.companyId = companyId;
        this.password = password;
        this.satisfiesRequirements = satisfiesRequirements;
    }

    public Company() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSatisfiesRequirements(Boolean satisfiesRequirements) {
        this.satisfiesRequirements = satisfiesRequirements;
    }

    public String getName() {
        return name;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getSatisfiesRequirements() {
        return satisfiesRequirements;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", id=" + companyId +
                ", password='" + password + '\'' +
                ", satisfiesRequirements=" + satisfiesRequirements +
                '}';
    }
}
