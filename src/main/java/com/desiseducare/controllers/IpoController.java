package com.desiseducare.controllers;
import com.desiseducare.models.*;
import com.desiseducare.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controller for all action related to IPOs
 */
@Controller
@Slf4j
public class IpoController
{
    @Autowired
    OpenIPOService openIPOService;
    @Autowired
    BidsService bidsService;
    @Autowired
    ClosedIPOService closedIPOService;
    @Autowired
    UserService userService;
    @Autowired
    IpoAllocationService ipoAllocationService;
    @Autowired
    SecurityService securityService;
    @Autowired
    CompanyService companyService;

    /**
     * Checks if the current logged-in user is a company with the given id.
     * @param id
     * @return true if logged-in user is company with given id else false
     */
    public boolean checkIfValidCompany(Integer id) {

        UserDetails userDetails=securityService.findLoggedInUser();
        if(userDetails==null)return false;
        Company company= companyService.getCompanyByName(userDetails.getUsername());

        if(company !=null && company.getCompanyId() == id)
        {
            return true;
        }
        return false;
    }

    /**
     * Checks if the current logged-in user is a bidder with the given id.
     * @param userId
     * @return true if logged-in user is bidder with given id else false
     */
    public boolean checkIfValidUser(Integer userId)
    {
        UserDetails loggedInUser=securityService.findLoggedInUser();
        User user = userService.getUserByEmail(loggedInUser.getUsername());
        if(user != null && user.getUserID() == userId)
        {
            return true;
        }
        return false;

    }


    /**
     * Endpoint that displays all open IPOs
     * @param model contains all attributes related to ipo
     * @return renders a table containing all IPOs
     */
    @RequestMapping(path="/getAllOpenIPOs",method= {RequestMethod.POST,RequestMethod.GET})
    public String getAllOpenIPOs(Model model)
    {
        List<OpenIPO> openIPOs= openIPOService.getAllOpenIPOs();
        model.addAttribute("openIPOs",openIPOs);
        return "openIPOTable";
    }

    /**
     * Endpoint to display a particular IPO
     * @param model contains all attributes related to ipo
     * @param id IPO id
     * @return renders a table with information of the particular IPO
     */
    @GetMapping("getOpenIPO/{OpenIPOId}")
    public String getUser(Model model,@PathVariable("OpenIPOId") Integer id)
    {
        Optional<OpenIPO> openIPOs= openIPOService.getOpenIPO(id);
        if(openIPOs.isPresent()) model.addAttribute("openIPOs",openIPOs.get());
        return "openIPOTable";
    }

    /**
     * Endpoint to add new IPO
     * @return renders an HTML form to add a new IPO
     */

    @RequestMapping(path="company/addOpenIPO/{companyId}",method= RequestMethod.POST)
    public String sendForm(@PathVariable("companyId") int companyId,Model model)
    {
        if(!checkIfValidCompany(companyId))
        {
            return "redirect:/403";
        }
        model.addAttribute("companyId",companyId);
        return "addOpenIPO" ;
    }
    @RequestMapping(path="company/addNewIPO/{companyId}",method= RequestMethod.POST)
    public String processForm(@PathVariable("companyId") int companyId,OpenIPO openIPO, Model model)
    {
        if(!checkIfValidCompany(companyId))
        {
            return "redirect:/403";
        }
        log.info("ipo before: "+openIPO);
        openIPO.setCompanyID(companyId);
        String result = openIPOService.addOpenIPO(openIPO);
        if (result !="")
        {
            model.addAttribute("errorMessage",result);
            return "error";
        }
        return "redirect:/getAllOpenIPOs";
    }



    /**
     * Endpoint to delete IPO
     * @param id ID of IPO to be deleted
     * @return redirects to /getAllOpenIPOs that renders a table containing all open IPOs
     */
    @RequestMapping(value = "deleteOpenIPO/{OpenIPOId}",method = {RequestMethod.GET,RequestMethod.DELETE})
    public String deleteOpenIPO(@PathVariable("OpenIPOId") Integer id)
    {
        openIPOService.deleteOpenIPO(id);
        return "redirect:/getAllOpenIPOs";
    }

    /**
     * Endpoint to retrieve all bids made by all users
     * @param model contains all attributes related to the bids
     * @return a table with information of all bids by all the users
     */
    @GetMapping("/getAllBids")
    public String getAllBids(Model model)
    {
        List<Bids> bids= bidsService.getAllBids();
        model.addAttribute("bids",bids);
        return "bidsTable";
    }

    /**
     * Endpoint to retrieve all bids made by a particular user
     * @param model contains all attributes related to the bids
     * @param userId userID of the user
     * @return a table with information of all bids made by the user
     */
    @RequestMapping(path = "/user/getBids/{userId}",method={RequestMethod.POST,RequestMethod.GET})
    public String getBidsByUserId(Model model,@PathVariable("userId") Integer userId)
    {
        if(!checkIfValidUser(userId))
        {
            return "redirect:/403";
        }
        List<Bids> bids= bidsService.getBidsByUserId(userId);
        model.addAttribute("bids",bids);
        return "bidsTable";
    }

    @RequestMapping(path="/user/getUserOpenIPOs/{userId}",method= {RequestMethod.POST,RequestMethod.GET})
    public String getUserOpenIPOs(Model model,@PathVariable("userId") Integer userId)
    {
        if(!checkIfValidUser(userId))
        {
            return "redirect:/403";
        }
        List<OpenIPO> openIPOs= openIPOService.getAllOpenIPOs();
        model.addAttribute("openIPOs",openIPOs);
        model.addAttribute("userId",userId);
        return "openIPOTable";
    }

    /**
     * Endpoint to make a new bid
     * @return renders a form that can be filled by a user to make a bid
     */
    @RequestMapping(path="/user/makeBid/{userId}/{companyId}",method = {RequestMethod.POST,RequestMethod.GET})
    public String sendBidForm(Model model,@PathVariable("userId") Integer userId,@PathVariable("companyId") Integer companyId)
    {
        if(!checkIfValidUser(userId))
        {
            return "redirect:/403";
        }
        model.addAttribute("userId",userId);
        model.addAttribute("companyId",companyId);
        return "makeBid" ;
    }

    @RequestMapping(path="/user/processBid/{userId}/{companyId}",method = {RequestMethod.POST,RequestMethod.GET})
    public String processBidForm(Bids bid, Model model,@PathVariable("userId") Integer userId,@PathVariable("companyId") Integer companyId)
    {
        if(!checkIfValidUser(userId))
        {
            return "redirect:/403";
        }
        User user = userService.getUser(bid.getUserId());
        bid.setUserType(user.getUserType());
        bid.setUserId(userId);
        bid.setCompanyId(companyId);
        bid.setBidDate(new Date());
        String result = bidsService.addBid(bid);
        if (result !="")
        {
            model.addAttribute("errorMessage",result);
            return "error";
        }
        return "redirect:/user/getBids/"+bid.getUserId();
    }

    /**
     * Endpoint to view all bids for a particular company's IPO
     * @param model contains all attributes related to bids
     * @param companyId companyID of the company
     * @return renders a table with all the bids for the company's IPO
     */
    @RequestMapping(path="/company/getBids/{companyId}",method= RequestMethod.POST)
    public String getBidsByCompanyId(Model model,@PathVariable("companyId") Integer companyId)
    {
        if(!checkIfValidCompany(companyId))
        {
            return "redirect:/403";
        }
        List<Bids> bids= bidsService.getBidsByCompanyId(companyId);
        model.addAttribute("bids",bids);
        return "bidsTable";
    }

    /**
     * Endpoint to delete a bid
     * @param id BidID of the bid to be deleted
     * @param model will be used to render an error message in case the bidID does not exist
     * @return redirects to /getAllBids
     */
    @RequestMapping(value = "deleteBid/{bidId}",method = {RequestMethod.GET,RequestMethod.DELETE})
    public String deleteBid(@PathVariable("bidId") Integer id,Model model)
    {
        String result = bidsService.deleteBid(id);
        if (result !="")
        {
            model.addAttribute("errorMessage",result);
            return "error";
        }
        return "redirect:/getAllBids";
    }

    /**
     * Endpoint that can be used by the company for issuing shares after closing date
     * @param model
     * @param companyId
     * @return returns a form where cutoff, different users' quota should be filled
     */

    @RequestMapping (path="/company/issue/{companyId}",method= RequestMethod.POST)
    public String issueTable(Model model,@PathVariable("companyId") Integer companyId)
    {
        if(!checkIfValidCompany(companyId))
        {
            return "redirect:/403";
        }
        Optional<OpenIPO> ipos = openIPOService.getOpenIPO(companyId);
        OpenIPO ipo=ipos.get();
        List<IssueTable> entries = new ArrayList<IssueTable>();
        int noOfShares=0;
        for(float p=ipo.getCapPrice();p>=ipo.getFloorPrice();p-=1)
        {
            List<Bids> bids = bidsService.getBidsByBidPrice(p);
            for (Bids b : bids)
            {
                noOfShares += b.getNumberOfLotsSubscribed() * ipo.getLotSize();
            }
            if (!bids.isEmpty()) entries.add(new IssueTable(p, noOfShares));

        }
        System.out.println(entries);
        model.addAttribute("bids",entries);
        model.addAttribute("companyId",companyId);
        return "issueTable";
    }

    /**
     * Adds the company to closed IPO that does further allocation of bids
     * @param closedIPO the IPO object of the company that needs to do allocation of bids
     * @return redirects to /getIPOAllocation where all the allocated shares information is displayed
     */
    @RequestMapping (path="/company/issuePrice/{companyId}",method= RequestMethod.POST)
    public String getIssuePrice(ClosedIPO closedIPO,@PathVariable("companyId")int companyId,  Model model)
    {
        if(!checkIfValidCompany(companyId))
        {
            return "redirect:/403";
        }
       log.info("closed IPO:"+closedIPO.toString());
        closedIPO.setCompanyID(companyId);
        Optional<OpenIPO> ipos = openIPOService.getOpenIPO(closedIPO.getCompanyID());
        OpenIPO ipo=ipos.get();
        closedIPO.copyToClosedIPO(ipo);
        closedIPOService.addClosedIPO(closedIPO);
        return "redirect:/getIPOAllocation";
    }

    /**
     * Endpoint that gets all IPO allocations
     * @param model contains all attributes for each bid that was allocated
     * @return renders a table containing all the allocated bids
     */
    @GetMapping("/getIPOAllocation")
    public String getIPOAllocation(Model model)
    {
        List<IpoAllocation> allocation= ipoAllocationService.getAllIPOAllocations();
        model.addAttribute("bids",allocation);
        return "AllocatedIPO";
    }

}
