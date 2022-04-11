package com.desiseducare.repository;


import com.desiseducare.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  This class provides the mechanism for storage, retrieval, search, update and delete operation on Company objects from the database.
 *  This extends JpaRepository that contains the full API of CrudRepository and PagingAndSortingRepository .
 */
public interface CompanyRepository extends JpaRepository<Company,Integer>
{
    List<Company> findByNameIs(String name);

}
