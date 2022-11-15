package com.city.list.exception;

/**
 * Exception for bad request error
 * */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
