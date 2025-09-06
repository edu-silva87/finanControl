package com.finan_control.auth_service.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PasswordValidationTest {

    @Test
    @DisplayName("Should return true when password is strong")
    void isValid_ShouldReturnTrue_WhenPasswordIsStrong() {
        String password = "@#63128fDRTYRQ@#$";
        assertTrue(PasswordValidation.isValid(password));
    }

    @Test
    @DisplayName("Should return false when password is not strong")
    void isValid_ShouldReturnFalse_WhenPasswordIsNotStrong() {
        String password = "123";
        assertFalse(PasswordValidation.isValid(password));
    }
}
