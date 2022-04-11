package com.desiseducare.repository;

import com.desiseducare.models.Bids;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  This class provides the mechanism for storage, retrieval, search, update and delete operation on Bid objects from the database.
 *  This extends JpaRepository that contains the full API of CrudRepository and PagingAndSortingRepository .
 */
@Repository
public interface BidsRepository extends JpaRepository<Bids,Integer>
{
    List<Bids> findByUserIdIs(Integer userId);
    List<Bids> findByCompanyIdIs(Integer companyId);
    List<Bids> findAllByBidPriceIs(Float bidPrice);
    List<Bids> findAllByUserTypeIsAndBidPriceGreaterThanEqual(String userType, Float bidPrice);
    List<Bids> findAllByUserTypeIsAndCompanyIdIsAndBidPriceGreaterThanEqual
            ( String userType,Integer companyId, Float bidPrice);
    @Query(value = "SELECT SUM(number_of_lots_subscribed) " +
            "FROM bids WHERE" +
            " (user_type= :userType AND bid_price>= :bidPrice AND company_id= :companyID)", nativeQuery = true)
    Integer findSumOfValidBids( @Param("companyID") Integer companyID, @Param("userType") String userType,
                                @Param("bidPrice") Float bidPrice);
    List<Bids> findAllByUserTypeIs(String userType);
}
