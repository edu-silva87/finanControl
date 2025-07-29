package com.finan_control.auth_service.exceptions;

public class InvalidPasswordException extends Exception{
    
    public InvalidPasswordException(String msg) {
        super(msg);
    }
}
