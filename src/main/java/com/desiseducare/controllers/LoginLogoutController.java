package com.desiseducare.controllers;


import com.desiseducare.models.Company;
import com.desiseducare.models.User;
import com.desiseducare.services.CompanyService;
import com.desiseducare.services.SecurityService;
import com.desiseducare.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collection;
import java.util.List;

/**
 * Controller for all action related to login and logout activities
 */
@Controller
@Slf4j
public class LoginLogoutController
{
    @Autowired
    SecurityService securityService;
    @Autowired
    UserService userService;
    @Autowired
    CompanyService companyService;

    @GetMapping("/")
    public String welcome(Model model)
    {
        model.addAttribute("name","user");
        model.addAttribute("allTypes", List.of("company","bidder"));
        model.addAttribute("entryOptions", List.of("login as existing user","create new account"));
        return "welcome";
    }

    /**
     * Endpoint for adding a new company
     * Renders a HTML form for adding a new company
     * @return html addCompany form
     */
    @GetMapping("/company-register")
    public String sendCompanyForm()
    {
        return "addCompany" ;
    }

    /**
     *
     * Processes form input from adding a new company
     * @param company
     * @return table with all company details
     */
    @RequestMapping(path="newCompany",method= RequestMethod.POST)
    public String processForm(Model model, Company company) {
        String result = companyService.addCompany(company);
        if (result != "") {
            model.addAttribute("errorMessage", result);
            return "error";
        }
        return "redirect:/company/Home/" + company.getCompanyId();
    }

    /**
     * Endpoint to add a new user - registration of a new user
     * @return renders a form requesting user details
     */
    @GetMapping("/user-register")
    public String sendUserForm()
    {
        return "addUser" ;
    }

    @RequestMapping(path="newUser",method= RequestMethod.POST)
    public String processForm(Model model, User user)
    {
        user.setWallet(0.0f);
        String result = userService.addUser(user);
        if (result !="")
        {
            model.addAttribute("errorMessage",result);
            return "error";
        }
        return "redirect:/user/Home/"+ user.getUserID();

    }

    @RequestMapping(path="/loginQuery",method= RequestMethod.POST)
    public String loginQuery(@RequestParam String userType,@RequestParam String entryMode, Model model)
    {
        model.addAttribute("name",userType+entryMode);
        if(entryMode.equalsIgnoreCase("create new account"))
        {
            if(userType.equalsIgnoreCase("company"))
                return "redirect:/company-register";
            else return "redirect:/user-register";

        }
        else
        {
            if(userType.equalsIgnoreCase("company"))
                return "redirect:/company/login";
            else return "redirect:/user/login";
        }

    }
    @GetMapping("/user/login")
    public String login(String error, Model model, Authentication authentication)
    {
        UserDetails loggedInUser = securityService.findLoggedInUser();
        if(loggedInUser!=null){
            Collection<? extends GrantedAuthority> authorities= loggedInUser.getAuthorities();
            if(authorities.contains(new SimpleGrantedAuthority("USER")))
            {
                return "redirect:/user/welcome";
            }
            else
            {
                return "redirect:/company/welcome";
            }
        }
        if(error!=null)
        {
            log.info(error);
            model.addAttribute("errormessage","Either the password username combination is invalid or user does not exist");
        }
        model.addAttribute("loginStatus",false);
        return "userlogin";
    }



    @GetMapping("/company/login")
    public String companylogin(String error, Model model)
    {

        UserDetails loggedInUser = securityService.findLoggedInUser();

        if(loggedInUser!=null){
            Collection<? extends GrantedAuthority> authorities= loggedInUser.getAuthorities();
            if(authorities.contains(new SimpleGrantedAuthority("USER")))
            {
                return "redirect:/user/welcome";
            }
            else
            {
                return "redirect:/company/welcome";
            }
        }

        if(error!=null)
        {
            log.info(error);
            model.addAttribute("errormessage","Either the password username combination is invalid or user does not exist");
        }
        model.addAttribute("loginStatus",false);
        return "companylogin";
    }




}
