package com.desiseducare.services;

import com.desiseducare.models.Transactions;
import com.desiseducare.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * performs transaction related functions
 */
@Service
public class TransactionsService {
    @Autowired
    TransactionsRepository transactionsRepository;
    public void addTransaction (Transactions transaction)
    {
        transactionsRepository.save(transaction);
    }

    public List<Transactions> getAllTransactions(){

        return transactionsRepository.findAll();
    }

    public List<Transactions> getTransactionByUserId(Integer userID){
        return transactionsRepository.findByUserIDIs(userID);
    }


}