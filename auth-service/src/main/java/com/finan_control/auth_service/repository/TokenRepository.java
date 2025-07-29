package com.finan_control.auth_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finan_control.auth_service.model.TokenModel;

public interface TokenRepository extends JpaRepository<TokenModel, UUID> {

    Optional<TokenModel> findByTokenValue(String tokenValue);
} 