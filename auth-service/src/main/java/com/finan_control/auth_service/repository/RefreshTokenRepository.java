package com.finan_control.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finan_control.auth_service.model.RefreshTokenModel;
import com.finan_control.auth_service.model.UserModel;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel,Long>{

    Optional<RefreshTokenModel> findByToken(String token);

    void deleteByUser(UserModel user);

}
