package com.finan_control.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finan_control.auth_service.model.PasswordResetToken;
import com.finan_control.auth_service.model.UserModel;

public interface PasswodResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUser(UserModel user);
}
