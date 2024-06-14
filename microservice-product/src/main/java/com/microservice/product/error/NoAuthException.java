package com.microservice.product.error;

public class NoAuthException extends Exception{

    public NoAuthException(String message) {
        super(message);
    }
}
