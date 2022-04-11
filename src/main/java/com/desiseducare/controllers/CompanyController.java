package com.desiseducare.controllers;


import com.desiseducare.models.Company;
import com.desiseducare.services.CompanyService;
import com.desiseducare.services.OpenIPOService;
import com.desiseducare.services.SecurityService;
import com.desiseducare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
/**
 * Controller for all actions that would be performed by a company
 */
@RequestMapping("/company/")
public class CompanyController
{
    /**
     * Company service class to perform CRUD company operations
     */
    @Autowired
    CompanyService companyService;
    @Autowired
    OpenIPOService openIPOService;
    @Autowired
    SecurityService securityService;
    @Autowired
    UserService userService;


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
     * Welcome page that redirects to another page as below:
     * 1. If logged-in user is a company -> /company/Home/{companyId}
     * 2. If logged-in user is a bidder -> /403 (Access denied)
     * 3. If no user in logged in -> /company/login
     * @return
     */
    @GetMapping("/welcome")
    public String welcome()
    {
        UserDetails loggedInUser=securityService.findLoggedInUser();
        if(loggedInUser==null)
        {
            return "redirect:/company/login";
        }
        Company company=companyService.getCompanyByName(loggedInUser.getUsername());
        if(company == null)
        {
            return "redirect:/403";
        }
        return "redirect:/company/Home/"+company.getCompanyId();

    }

    /**
     * Endpoint to retrieve all companies that are registered
     * @param model stores all company attributes
     * @return  table with all companies
     */
    @GetMapping("/getAllCompanies")
    public String getAllCompanies(Model model)
    {
        log.info("Inside getAllCompanies in companyController");
        List<Company> allCompanies= companyService.getAllCompanies();
        model.addAttribute("companies",allCompanies);
        return "companyTable";
    }

    /**
     * Endpoint to retrieve information of a particular registered company
     * @param model stores all company attributes
     * @param id Company id
     * @return table with details of the particular company
     */
    @GetMapping("/{CompanyId}")
    public String getCompany(Model model, @PathVariable("CompanyId") Integer id, Principal principal)
    {

        if(!checkIfValidCompany(id))
        {
            return "redirect:/403";
        }
        Company company= companyService.getCompany(id);
         model.addAttribute("companies",company);
        return "companyTable";
    }





    @PostMapping("/getAllCompanies")
    public String processForm(Company company) {
        companyService.addCompany(company);
        return "redirect:/getAllCompanies";

    }

    /**
     * company home page
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/Home/{CompanyId}")
    public String companyHomePage(Model model,@PathVariable("CompanyId") Integer id)
    {

        if(!checkIfValidCompany(id))
        {
            return "redirect:/403";
        }
        Company company= companyService.getCompany(id);
        model.addAttribute("company",company.getName());
        model.addAttribute("companyId",company.getCompanyId());

        return "companyHomePage";
    }



    /**
     * Endpoint for deleting a particular company
     * Deletes a company by ID
     * @param id ID of comapany to be deleted
     * @return redirects to /getAllCompanies
     */
    @RequestMapping(value = "deleteCompany/{CompanyId}",method = {RequestMethod.GET,RequestMethod.DELETE})
    public String deleteCompany(@PathVariable("CompanyId") Integer id)
    {
        if(!checkIfValidCompany(id))
        {
            return "redirect:/403";
        }
        companyService.deleteCompany(id);
        return "redirect:/company/getAllCompanies";
    }


}
