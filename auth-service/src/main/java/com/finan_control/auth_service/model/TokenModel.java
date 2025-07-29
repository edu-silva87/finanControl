package com.finan_control.auth_service.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_tokens", schema = "auth")
public class TokenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String tokenValue;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expireAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false, foreignKey = @ForeignKey(name = "fk_token_user"))
    private UserModel user;
}
