package com.desiseducare.controllers;

import com.desiseducare.models.Transactions;
import com.desiseducare.models.User;
import com.desiseducare.services.SecurityService;
import com.desiseducare.services.TransactionsService;
import com.desiseducare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

/**
 * Controller for all transaction related actions
 */
@Controller
public class TransactionController {
    @Autowired
    TransactionsService transactionsService;
    @Autowired
    SecurityService securityService;
    @Autowired
    UserService userService;

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

    @RequestMapping(path="/user/getTransaction/{UserId}",method = {RequestMethod.POST,RequestMethod.GET})
    public String getTransaction(Model model, @PathVariable("UserId") Integer id){

        if(!checkIfValidUser(id))
        {
            return "redirect:/403";
        }

        List<Transactions> userTransactions = transactionsService.getTransactionByUserId(id);
        model.addAttribute("transactions",userTransactions);
        return "transactionTable";
    }

}
