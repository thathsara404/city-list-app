package com.city.list.exception;

/**
 * Exception for unexpected error
 * */
public class UnExpectedErrorOccurredException extends RuntimeException {

    public UnExpectedErrorOccurredException(String message, Throwable cause) {
        super(message, cause);
    }

}
