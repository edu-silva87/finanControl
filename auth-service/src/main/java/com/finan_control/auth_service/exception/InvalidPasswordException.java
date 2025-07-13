package com.finan_control.auth_service.exception;

public class InvalidPasswordException extends Exception{
    
    public InvalidPasswordException(String msg) {
        super(msg);
    }
}
