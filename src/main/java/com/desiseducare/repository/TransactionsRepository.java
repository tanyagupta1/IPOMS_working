package com.desiseducare.repository;

import com.desiseducare.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  This class provides the mechanism for storage, retrieval, search, update and delete operation on Transactions objects from the database.
 *  This extends JpaRepository that contains the full API of CrudRepository and PagingAndSortingRepository .
 */
@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer>
{
    List<Transactions> findByUserIDIs(Integer userID);
}
