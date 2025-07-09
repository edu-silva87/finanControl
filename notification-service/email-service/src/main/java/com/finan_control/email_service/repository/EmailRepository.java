package com.finan_control.email_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finan_control.email_service.models.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel, UUID>{

}
