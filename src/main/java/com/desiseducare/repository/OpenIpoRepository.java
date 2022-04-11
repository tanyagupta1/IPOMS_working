package com.desiseducare.repository;
import com.desiseducare.models.OpenIPO;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *  This class provides the mechanism for storage, retrieval, search, update and delete operation on OpenIPO objects from the database.
 *  This extends JpaRepository that contains the full API of CrudRepository and PagingAndSortingRepository .
 */
public interface OpenIpoRepository extends JpaRepository<OpenIPO,Integer>
{

}
