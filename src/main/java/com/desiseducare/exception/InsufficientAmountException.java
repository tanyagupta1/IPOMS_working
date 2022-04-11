package com.desiseducare.exception;

/**
 *  Exception for insufficient balance in account
 */
public class InsufficientAmountException extends RuntimeException{
    /**
     * Exception for insufficient balance in account
     * @param message error message to be displayed
     */
    public InsufficientAmountException(String message){
        super(message);
    }
}
