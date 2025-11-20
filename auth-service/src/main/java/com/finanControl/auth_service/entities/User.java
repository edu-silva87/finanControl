package com.finanControl.auth_service.entities;

import com.finanControl.auth_service.dto.LoginRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Getter
@Builder
@Entity
@Table(name = "tb_users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    public boolean isCorrectPassword(LoginRequestDto loginRequestDto, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(loginRequestDto.password(), this.password);
    }
}
