package com.finan_control.auth_service.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data 
@AllArgsConstructor 
@Builder
@Entity
@Table(name = "tb_users", schema = "auth")
public class UserModel {

    private UUID id;
    private String email;
    @Builder.Default
    private boolean enabled = false;
}
