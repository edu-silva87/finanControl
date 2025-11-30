package com.finanControl.user_service.repository;

import com.finanControl.user_service.entity.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, UUID> {

    Page<ExpenseEntity> findAllByUserId(UUID id, Pageable pageable);
}