package com.desiseducare.controllers;


import com.desiseducare.models.User;
import com.desiseducare.services.SecurityService;
import com.desiseducare.services.UserService;
import com.desiseducare.services.WalletServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller for all actions that would be performed by a user
 */
@Controller
@Slf4j
@RequestMapping("/user/")
public class UserController
{
    @Autowired
    UserService userService;

    @Autowired
    WalletServices walletService;

    @Autowired
    SecurityService securityService;

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
     * Welcome page that redirects to another page as below:
     * 1. If logged-in user is a bidder -> /bidder/Home/{userId}
     * 2. If logged-in user is a company -> /403 (Access denied)
     * 3. If no user in logged in -> /user/login
     * @return
     */
    @GetMapping("/welcome")
    public String welcome()
    {
        log.info("inside welcome user");
        UserDetails loggedInUser=securityService.findLoggedInUser();
        if(loggedInUser==null)
        {
            return "redirect:/user/login";
        }
        User user=userService.getUserByEmail(loggedInUser.getUsername());
        if(user == null)
        {
            return "redirect:/403";
        }
        return "redirect:/user/Home/"+user.getUserID();

    }

    /**
     * Endpoint that displays all users registered on the platform
     * @param model contains attributes of all users
     * @return renders a table containing details of all users
     */
    @GetMapping("/getAllUsers")
    public String getAllUsers(Model model)
    {
        List<User> allUsers= userService.getAllUsers();
        model.addAttribute("users",allUsers);
        return "userTable";
    }

    /**
     * Endpoint that displays details of a particular user
     * @param model contains attributes of the user
     * @param id userID of user
     * @return renders a table containing details of the user
     */
    @GetMapping("/{UserId}")
    public String getUser(Model model,@PathVariable("UserId") Integer id)
    {
        if(!checkIfValidUser(id))
        {
            return "redirect:/403";
        }
        User user= userService.getUser(id);
        if(user != null) model.addAttribute("users",user);
        return "userTable";
    }

    /**
     * User Home Page
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/Home/{UserId}")
    public String companyHomePage(Model model,@PathVariable("UserId") Integer id)
    {
        if(!checkIfValidUser(id))
        {
            return "redirect:/403";
        }
        User user= userService.getUser(id);

        model.addAttribute("userId",user.getUserID());
        return "userHomePage";
    }

    /**
     * Endpoint to delete a user from the system
     * @param id userID of user to be deleted
     * @return redirects to /getAllUsers
     */
    @RequestMapping(value = "deleteUser/{UserId}",method = {RequestMethod.GET,RequestMethod.DELETE})
    public String deleteUser(@PathVariable("UserId") Integer id)
    {
        if(!checkIfValidUser(id))
        {
            return "redirect:/403";
        }
        userService.deleteUser(id);
        return "redirect:/getAllUsers";
    }

    @RequestMapping(path="walletForm/{UserId}",method = {RequestMethod.POST,RequestMethod.GET})
    public String sendWalletForm(@PathVariable("UserId") Integer userId,Model model)
    {
        User user = userService.getUser(userId);
        model.addAttribute("userId",userId);
        model.addAttribute("amount",user.getWallet());
        return "addWallet";
    }
    /**
     * Endpoint to add money to the wallet of a particular user
     * @param userId userID of the user
     * @param amount amount to be added
     * @return redirects to /getAllUsers
     */
    @RequestMapping(path="/addAmountToWallet/{UserId}",method = {RequestMethod.POST,RequestMethod.GET})
    public String addAmountToWallet(@PathVariable("UserId") Integer userId, @RequestParam Float amount)
    {
        System.out.println("amount is "+amount);
        walletService.transferAmountToWallet(userId, amount);
        return "redirect:/user/walletForm/"+userId;
    }

    /**
     * Endpoint to withdraw money from the wallet of a particular user
     * @param userId userID of the user
     * @param amount amount to be withdrawn from the wallet
     * @return redirects to /getAllUsers
     */
    @GetMapping("/withdrawAmountFromWallet/{UserId}/{Amount}")
    public String withdrawAmountFromWallet(@PathVariable("UserId") Integer userId, @PathVariable("Amount") Float amount)
    {
        walletService.transferAmountFromWallet(userId, amount);
        return "redirect:/getAllUsers";
    }
}
