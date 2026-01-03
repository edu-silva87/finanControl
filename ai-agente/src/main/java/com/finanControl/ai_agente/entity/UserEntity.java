package com.finanControl.ai_agente.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "tb_users")
public class UserEntity {

    @Id
    private UUID id;
}
