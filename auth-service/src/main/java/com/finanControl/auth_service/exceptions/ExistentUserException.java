package com.finanControl.auth_service.exceptions;

public class ExistentUserException extends RuntimeException {
    public ExistentUserException(String message) {
        super(message);
    }
}
