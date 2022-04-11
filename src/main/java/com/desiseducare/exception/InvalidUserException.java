package com.desiseducare.exception;

/**
 * Exception for invalid user
 */
public class InvalidUserException extends RuntimeException{

    /**
     * Exception for invalid user
     * @param message error message to vbe displayed
     */

    public InvalidUserException(String message){
        super(message);
    }
}
