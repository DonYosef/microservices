package com.microservice.user.error;

public class NoAuthException extends Exception{

    public NoAuthException(String message) {
        super(message);
    }
}
