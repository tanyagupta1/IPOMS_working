package com.desiseducare.utility;

import com.desiseducare.models.Company;
import com.desiseducare.models.User;
import com.desiseducare.services.CompanyService;
import com.desiseducare.services.SecurityService;
import com.desiseducare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Autowired
     SecurityService securityService;
    @Autowired
     CompanyService companyService;
    @Autowired
     UserService userService;

    public boolean checkIfValidCompany(Integer companyId) {

        UserDetails loggedInUser = securityService.findLoggedInUser();
        Company company= companyService.getCompanyByName(loggedInUser.getUsername());
        if(company !=null && company.getCompanyId() == companyId)
        {
            return true;
        }
        return false;
    }

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
}
