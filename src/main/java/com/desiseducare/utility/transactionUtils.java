package com.desiseducare.utility;

import com.desiseducare.models.User;


import com.desiseducare.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;

public class transactionUtils {
    @Autowired
    UserRepository userRepository;


    /**
     * A utility function that returns true if amount in wallet of a given user is greater than given amount else returns false
     * @param user user object whose wallet needs to be checked
     * @param amount float number representing the amount in wallet
     * @return returns a boolean variable
     */
    public boolean checkAmount(User user, Float amount){
        if(amount > user.getWallet()){
            return false;
        }
        else{
            return true;
        }

    }


    public Boolean companyCheck(Integer id)
    {

        return false;
    }
}
