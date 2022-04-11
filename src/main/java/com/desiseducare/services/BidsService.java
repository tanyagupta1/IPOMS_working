package com.desiseducare.services;

import com.desiseducare.models.Bids;
import com.desiseducare.models.OpenIPO;
import com.desiseducare.models.User;
import com.desiseducare.repository.BidsRepository;
import com.desiseducare.repository.OpenIpoRepository;
import com.desiseducare.repository.UserRepository;

import com.desiseducare.utility.transactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * performs CRUD operations on bid along with checks when adding a bid
 */
@Service
public class BidsService
{
    @Autowired
    BidsRepository bidsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OpenIpoRepository openIPORepository;
    @Autowired
    WalletServices walletService;

    /**
     * Function for making a bid
     * @param bid bids object containing all bid attributes
     * @return returns error message in case of error while placing the bid else returns empty string
     */

    public String addBid(Bids bid)
    {
        Integer userId = bid.getUserId();
        User user = userRepository.getById(userId);
        OpenIPO ipo= openIPORepository.getById(bid.getCompanyId());
        if(bid.getBidPrice()<ipo.getFloorPrice() || bid.getBidPrice()>ipo.getCapPrice())
        {
            return "BID price out of range";
        }

        if((Math.round(bid.getBidPrice())==Math.round(ipo.getCapPrice())) &&(!user.getUserType().equalsIgnoreCase("RII")))
        {
            return user.getUserType()+" can't bid at Cap price";
        }
        Float totalBidAmount = bid.getBidPrice()*bid.getNumberOfLotsSubscribed()*ipo.getLotSize();
        switch (user.getUserType())
        {
            case "RII":
                if(totalBidAmount>200000.0)
                {
                    return "RII amount can't be greater than 2 lacs";
                }
                break;
            case "NII":
                if(totalBidAmount<200000.0)
                {
                    return "NII amount can't be less than 2 lacs";
                }
                break;
            case "Angel":
                if(totalBidAmount<100000000.0)
                {
                    return "Angel must bid for a total value >10 Cr";
                }
                break;
        }
        transactionUtils transactionUtils = new transactionUtils();
        System.out.println("wallet: "+user.getWallet()+"bid"+totalBidAmount);
        boolean valid = transactionUtils.checkAmount(user, totalBidAmount);
        if(valid == false){
            return "Insufficient funds to make the bid";
        }
        else{
            walletService.transferAmountFromWallet(user.getUserID(), totalBidAmount);
            bid.setTotalPrice(totalBidAmount);
            bidsRepository.save(bid);
        }

        return "";
    }

    /**
     * @return returns a list of all bids from the bids repository
     */
    public List<Bids> getAllBids()
    {
        return bidsRepository.findAll();
    }

    /**
     * Function for retrieving all bids of a particular user
     * @param userId an integer representing userID of user
     * @return returns a list of all bids placed by a particular user
     */

    public List<Bids> getBidsByUserId(Integer userId)
    {
        return bidsRepository.findByUserIdIs(userId);
    }

    /**
     * Function for retrieving all bids of a particular company
     * @param companyId an integer representing companyID of the company
     * @return returns a list of all bids for a particular company
     */
    public List<Bids> getBidsByCompanyId(Integer companyId)
    {
        return bidsRepository.findByCompanyIdIs(companyId);
    }

    /**
     * Function to delete a bid
     * @param id an integer representing the bidID of the bid to be deleted
     * @return returns error message in case or error or else returns an empty string
     */
    public String deleteBid(Integer id)
    {

        if(bidsRepository.existsById(id))
        {
            Bids bid = bidsRepository.getById(id);
            User user= userRepository.getById(bid.getUserId());
            if(!user.getUserType().equalsIgnoreCase("RII"))
            {
                return "non- RIIs can't delete their Bids";
            }
            else{
                bidsRepository.deleteById(id);
                walletService.transferAmountToWallet(bid.getUserId(), bid.getTotalPrice());
            }

            return "";
        }
        else return "Bid doesn't exist";
    }

    /**
     * Function for retrieving all bids with a particular price
     * @param bidPrice a float number representing the price of the bid
     * @return returns a list of bids with the given bidPrice
     */

    public List<Bids> getBidsByBidPrice(float bidPrice)
    {
        return bidsRepository.findAllByBidPriceIs(bidPrice);
    }
}
