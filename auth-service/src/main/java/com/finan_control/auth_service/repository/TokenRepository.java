package com.finan_control.auth_service.repository;

import com.finan_control.auth_service.model.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenModel, UUID> {

    Optional<TokenModel> findByTokenValue(String tokenValue);
} 