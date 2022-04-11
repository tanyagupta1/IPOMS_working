package com.desiseducare.services;

import com.desiseducare.models.IpoAllocation;
import com.desiseducare.repository.IpoAllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * performs curd operations for IpoAllocation table in db
 */
@Service
public class IpoAllocationService
{
    @Autowired
    IpoAllocationRepository ipoAllocationRepository;

    /**
     * save the ipoAllocation info in db
     * @param ipoAllocation represents a ipoAllocation object
     */
    public void allocateIPO(IpoAllocation ipoAllocation)
    {
        ipoAllocationRepository.save(ipoAllocation);
    }

    /**
     * gets all ipo allocations
     * @return a list of all ipoAllocation objects form the db
     */
    public List<IpoAllocation> getAllIPOAllocations()
    {
        return ipoAllocationRepository.findAll();
    }

    /**
     * gets all ipo allocations for a particular companyID
     * @param companyId an integer representing companyID
     * @return a list of all ipoAllocation objects of the company
     */
    public List<IpoAllocation> getAllocationByCompany(Integer companyId)
    {
        return ipoAllocationRepository.findAllByCompanyIdIs(companyId);
    }

}
