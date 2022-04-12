package com.desiseducare.services;

import com.desiseducare.models.Bids;
import com.desiseducare.models.IpoAllocation;
import com.desiseducare.repository.BidsRepository;
import com.desiseducare.repository.ClosedIPORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * refunds for all bidders will be initiated through this class
 */
@Service
public class RefundService {
    @Autowired
    IpoAllocationService ipoAllocationService;
    @Autowired
    BidsService bidsService;
    @Autowired
    ClosedIPORepository closedIPORepository;
    @Autowired
    BidsRepository bidsRepository;
    @Autowired
    WalletServices walletService;

    /**
     * refunds all bidders of an IPO the appropriate amount. If the user is allocated some IPO, then refund amount is calculated
     * accordingly. If the user is not allocated any ipo, the entire bid amount is refunded
     * @param companyID
     */
    public void refundAfterClosingIPO(Integer companyID){
        Float cutoffPrice = closedIPORepository.getById(companyID).getCutoff();
        List<IpoAllocation> allocations = ipoAllocationService.getAllocationByCompany(companyID);
        List<Bids> unallocatedBids = bidsService.getBidsByCompanyId(companyID);
        for(IpoAllocation allocation : allocations){
            Bids bid = bidsRepository.getById(allocation.getBidID());
            unallocatedBids.remove(bid);
            Float paidAmount = bid.getTotalPrice();
            Float allocationPrice = allocation.getNoOfSharesAllocated()*cutoffPrice ;
            walletService.refundBid(bid, paidAmount - allocationPrice);
        }
        for (Bids unallotedBid: unallocatedBids){
            walletService.refundBid(unallotedBid, unallotedBid.getTotalPrice());
        }
    }

}
