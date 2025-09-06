package com.finan_control.auth_service.util;

import java.util.regex.Pattern;

public final class PasswordValidation {

    private PasswordValidation() {
        throw new AssertionError("Utility class - must not be instantiated");
    }

    
    private static final String PASSWORD_PATTERN = 
        "^(?=.*[0-9])" +          
        "(?=.*[a-z])" +           
        "(?=.*[A-Z])" +           
        "(?=.*[@#$%^&+=!])" +     
        "(?=\\S+$).{8,}$";   

    private static final Pattern PATTERN = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        return PATTERN.matcher(password).matches();
    }
}
