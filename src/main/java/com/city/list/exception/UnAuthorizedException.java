package com.city.list.exception;

/**
 * Exception for unauthorized error
 * */
public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

}
