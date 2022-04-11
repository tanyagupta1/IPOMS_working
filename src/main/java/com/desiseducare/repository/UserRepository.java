package com.desiseducare.repository;

import com.desiseducare.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 *  This class provides the mechanism for storage, retrieval, search, update and delete operation on User objects from the database.
 *  This extends JpaRepository that contains the full API of CrudRepository and PagingAndSortingRepository .
 */
public interface UserRepository extends JpaRepository<User, Integer>
{
    @Query(value = "SELECT * FROM user WHERE email= :email", nativeQuery = true)
    User getByEmail(@Param("email") String email);
}