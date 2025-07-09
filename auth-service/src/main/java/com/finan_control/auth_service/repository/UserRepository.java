package com.finan_control.auth_service.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.finan_control.auth_service.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID>{

    Optional<UserModel> findByEmailAndPassword(String email, String password);
    boolean existsByemail(String email);
}
