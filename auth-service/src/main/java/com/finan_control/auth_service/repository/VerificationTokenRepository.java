package com.finan_control.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finan_control.auth_service.model.UserModel;
import com.finan_control.auth_service.model.VerificationTokenModel;

public interface VerificationTokenRepository extends JpaRepository<VerificationTokenModel, Long>{
    
    Optional<VerificationTokenModel> findByToken(String token);
    
    void deleteByUser(UserModel user);
}
