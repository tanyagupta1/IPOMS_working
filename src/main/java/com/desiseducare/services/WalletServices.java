package com.desiseducare.services;

import com.desiseducare.exception.InsufficientAmountException;
import com.desiseducare.exception.InvalidUserException;
import com.desiseducare.models.Bids;
import com.desiseducare.models.Transactions;
import com.desiseducare.models.User;
import com.desiseducare.repository.TransactionsRepository;
import com.desiseducare.repository.UserRepository;
import com.desiseducare.utility.transactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * performs all wallet related services for a user
 */
@Service
public class WalletServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionsRepository transactionsRepository;

    /**
     * Function that transfers money from wallet of a given user
     * @param userID integer representing userID of a particular user
     * @param amount float number representing money to be transferred from the wallet
     * @return returns a string representing the status of transaction
     * @throws InsufficientAmountException in case of insufficient fund in account
     * @throws InvalidUserException in case the userID given is invalid
     */
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false)
    public String transferAmountFromWallet(Integer userID, Float amount){
        if(userRepository.existsById(userID)){
            Optional<User> findUser = userRepository.findById(userID);
            User user = findUser.get();
            transactionUtils transactionUtils = new transactionUtils();
            boolean valid = transactionUtils.checkAmount(user, amount);
            if(valid == false){
                throw new InsufficientAmountException("Insufficient fund in wallet");
            }
            // update amount
            user.setWallet(user.getWallet() - amount);
            userRepository.save(user);
            Date date = new Date();
            Transactions transaction = new Transactions(user.getUserID(),date, -1.00f*amount);

            transactionsRepository.save(transaction);
            return "Transaction Successful";
        }
        else{
            throw new InvalidUserException("User ID invalid");
        }
    }
    /**
     * Function that transfers money to wallet of a given user
     * @param userID integer representing userID of a particular user
     * @param amount float number representing money to be transferred to the wallet
     * @return returns a string representing the status of transaction
     * @throws InvalidUserException in case the userID given is invalid
     */
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false)
    public String transferAmountToWallet(Integer userID, Float amount){
        if(userRepository.existsById(userID)){
            System.out.println(amount);
            Optional<User> findUser = userRepository.findById((userID));
            User user = findUser.get();
            System.out.println("here"+user.toString());
            Float prevAmount = user.getWallet();
            user.setWallet(prevAmount + amount);
            userRepository.save(user);
            Date date = new Date();

            Transactions transaction = new Transactions(user.getUserID(),date, amount);

            transactionsRepository.save(transaction);
            return "Transaction Successful";
        }
        else{
            throw new InvalidUserException("User ID invalid");
        }
    }

    /**
     * Refund service that refunds the bidder in case his bid gets rejected or only partial amount of share are allotted
     * @param b a bid object that represents a bid that needs to be refunded
     * @param amountToRefund float number representing amount to be refunded to the bidder
     */
    public String refundBid(Bids b, Float amountToRefund){
        if(b.getPlatformID() == 1){ // if bid user is registered on the same platform
            Integer userID = b.getUserId();
            transferAmountToWallet(userID, amountToRefund);
        }
        else{
            // functionality to refund for 3rd party users
        }
        return "";
    }
}
