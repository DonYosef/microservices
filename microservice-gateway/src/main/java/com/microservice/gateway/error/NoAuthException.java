package com.microservice.gateway.error;

public class NoAuthException extends Exception{

    public NoAuthException(String message) {
        super(message);
    }
}
