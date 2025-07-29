package com.finan_control.auth_service.exceptions;

public class InvalidTokenException extends Exception{
    
    public InvalidTokenException(String msg) {
        super(msg);
    }
}