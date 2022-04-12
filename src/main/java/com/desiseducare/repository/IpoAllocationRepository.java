package com.desiseducare.repository;

import com.desiseducare.models.IpoAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  This class provides the mechanism for storage, retrieval, search, update and delete operation on IpoAllocation objects from the database.
 *  This extends JpaRepository that contains the full API of CrudRepository and PagingAndSortingRepository .
 */
public interface IpoAllocationRepository extends JpaRepository<IpoAllocation,Integer>
{
    List<IpoAllocation> findAllByCompanyIdIs(Integer companyId);
    List<IpoAllocation> findAllByUserIdIs(Integer userId);

}
