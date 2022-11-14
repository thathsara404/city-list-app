package com.city.list.exception;

/**
 * Exception for transaction not found
 * */
public class CanNotProceedTransactionException extends RuntimeException {

    public CanNotProceedTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

}
