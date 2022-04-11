package com.desiseducare.services;

import com.desiseducare.models.Bids;
import com.desiseducare.models.ClosedIPO;
import com.desiseducare.models.IpoAllocation;
import com.desiseducare.repository.BidsRepository;
import com.desiseducare.repository.ClosedIPORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * performs allocation of bids after closing the ipo
 */
@Service
public class ClosedIPOService
{
    @Autowired
    ClosedIPORepository closedIPORepository;
    @Autowired
    BidsRepository bidsRepository;
    @Autowired
    IpoAllocationService ipoAllocationService;
    @Autowired
    RefundService refundService;
    /**
     * Function that performs all allocation of bids after end date of an IPO.
     * It calculates the subscription level and allots shares based on it.
     * For NII and QBI, number of shares allotted is scaled on the basis of subscription level.
     * In case of an over-subscription for RII and if number of requests is lesser than number of lots available, then
     * everyone gets at least 1 lot and a few lucky draw winners get the remaining lots.
     * If the number of requests is greater than number of lots available, then only some users get 1 lot based on the lucky draw.
     * Lucky draw is implemented with the help of random integer generator.
     * Appropriate refund is made to all the users who were allotted less shares than what they bid and for those who did
     * not receive any shares at all
     * @param closedIPO closedIPO object that contains information about percentage of shares for NII, RII and QBI
     */
    public void addClosedIPO(ClosedIPO closedIPO)
    {
        Integer noOfLotsNII = Math.round(closedIPO.getTotalNumberOfSharesOffered()/closedIPO.getLotSize()*closedIPO.getNIIPercentage()/100f);
        Integer noOfLotsRII = Math.round(closedIPO.getTotalNumberOfSharesOffered()/closedIPO.getLotSize()*closedIPO.getRIIPercentage()/100f);
        Integer noOfLotsQBI = Math.round(closedIPO.getTotalNumberOfSharesOffered()/closedIPO.getLotSize()*closedIPO.getQIBPercentage()/100f);
        //allocate to NII
        System.out.println(noOfLotsNII+" "+noOfLotsRII+" "+noOfLotsQBI);
        List<Bids> NII = bidsRepository.findAllByUserTypeIsAndCompanyIdIsAndBidPriceGreaterThanEqual( "NII",closedIPO.getCompanyID(),closedIPO.getCutoff());
        System.out.println(NII);
        Integer noOfBidLotsNII = bidsRepository.findSumOfValidBids(closedIPO.getCompanyID(), "NII",closedIPO.getCutoff());
        System.out.println(noOfBidLotsNII);
        if(noOfBidLotsNII!=null)
        {
            float subscriptionLevelNII = Math.min(((float) noOfLotsNII) / ((float) noOfBidLotsNII), 1f);
            System.out.println("MY subscription level " + subscriptionLevelNII);
            for (Bids b : NII) {
                ipoAllocationService.allocateIPO(new IpoAllocation(closedIPO.getCompanyID(), b.getUserId(), b.getUserType(), Math.round(b.getNumberOfLotsSubscribed() * subscriptionLevelNII), b.getPlatformID(), b.getBidId()));
            }
        }
        //allocate QBI
        List<Bids> QBI = bidsRepository.findAllByUserTypeIsAndCompanyIdIsAndBidPriceGreaterThanEqual("QBI",closedIPO.getCompanyID(),closedIPO.getCutoff());
        System.out.println(QBI);
        Integer noOfBidLotsQBI = bidsRepository.findSumOfValidBids(closedIPO.getCompanyID(),"QBI",closedIPO.getCutoff());
        System.out.println(noOfBidLotsQBI);
        if(noOfBidLotsQBI!=null)
        {
            float subscriptionLevelQBI = Math.min(((float) noOfLotsQBI) / ((float) noOfBidLotsQBI), 1f);
            System.out.println("QBI subscription level " + subscriptionLevelQBI);
            for (Bids b : QBI) {
                ipoAllocationService.allocateIPO(new IpoAllocation(closedIPO.getCompanyID(), b.getUserId(), b.getUserType(), Math.round(b.getNumberOfLotsSubscribed() * subscriptionLevelQBI), b.getPlatformID(), b.getBidId()));
            }
        }
        //RII
            List<Bids> RII = bidsRepository.findAllByUserTypeIsAndCompanyIdIsAndBidPriceGreaterThanEqual("RII",closedIPO.getCompanyID(),closedIPO.getCutoff());
        System.out.println(RII);
        Integer noOfBidLotsRII = bidsRepository.findSumOfValidBids(closedIPO.getCompanyID(),"RII",closedIPO.getCutoff());
        System.out.println(noOfBidLotsRII);
        if(noOfBidLotsRII!=null)
        {
            float subscriptionLevelRII = Math.min(((float) noOfLotsRII) / ((float) noOfBidLotsRII), 1f);
            System.out.println("RII subscription level " + subscriptionLevelRII);
            if (subscriptionLevelRII < 1) //over-subscribed
            {
                Integer noOfRequests = RII.size();
                Random rand = new Random();
                if (noOfRequests <= noOfLotsRII) //everyone gets 1 lot at least
                {
                    HashMap<Integer,Integer> luckyWinners=new HashMap<Integer,Integer>();
                    Integer noOfLeftOverShares = noOfLotsRII - noOfRequests;
                    for (int i = 0; i < noOfLeftOverShares; i++) {
                        int luckyBidder = rand.nextInt(noOfRequests);
                       luckyWinners.put(luckyBidder,luckyWinners.getOrDefault(luckyBidder,0)+1);
                    }
                    System.out.println("LUCKY WINNERS: " + luckyWinners);
                    for (int i = 0; i < noOfRequests; i++) {
                        int extra = luckyWinners.getOrDefault(i,0);
                        System.out.println("EXTRA"+extra);
                        ipoAllocationService.allocateIPO(new IpoAllocation(closedIPO.getCompanyID(), RII.get(i).getUserId(), RII.get(i).getUserType(), 1 + extra, RII.get(i).getPlatformID(), RII.get(i).getBidId()));
                    }
                } else //some will get 0 lots
                {
                    Set<Integer> luckyWinners = new TreeSet<>();
                    for (int i = 0; i < noOfLotsRII; i++) {
                        int luckyBidder = rand.nextInt(noOfRequests);
                        while (luckyWinners.contains(luckyBidder)) luckyBidder = rand.nextInt(noOfRequests);
                        luckyWinners.add(luckyBidder);
                    }
                    System.out.println("LUCKY WINNERS: " + luckyWinners);
                    for (int i = 0; i < noOfRequests; i++) {
                        if (luckyWinners.contains(i))
                            ipoAllocationService.allocateIPO(new IpoAllocation(closedIPO.getCompanyID(), RII.get(i).getUserId(), RII.get(i).getUserType(), 1, RII.get(i).getPlatformID(), RII.get(i).getBidId()));

                    }
                }
            } else //everyone gets their exact demand
            {
                for (Bids b : RII) {
                    ipoAllocationService.allocateIPO(new IpoAllocation(closedIPO.getCompanyID(), b.getUserId(), b.getUserType(), Math.round(b.getNumberOfLotsSubscribed()), b.getPlatformID(), b.getBidId()));
                }

            }
        }
        closedIPORepository.save(closedIPO);
        refundService.refundAfterClosingIPO(closedIPO.getCompanyID());
    }
}