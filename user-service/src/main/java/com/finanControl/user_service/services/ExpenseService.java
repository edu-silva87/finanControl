package com.finanControl.user_service.services;

import com.finanControl.user_service.dtos.NewExpenseDto;
import com.finanControl.user_service.dtos.ReplaceExpenseDto;
import com.finanControl.user_service.entity.ExpenseEntity;
import com.finanControl.user_service.repository.ExpenseRepository;
import com.finanControl.user_service.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public void newExpense(NewExpenseDto newExpenseDto, String id) {
        var user = userRepository.findById(UUID.fromString(id));

        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        var newExpense = ExpenseEntity.builder()
                .user(user.get())
                .name(newExpenseDto.name())
                .price(newExpenseDto.price())
                .typeExpense(newExpenseDto.typeExpense())
                .date(newExpenseDto.date())
                .build();

        expenseRepository.save(newExpense);

    }

    public Page<ExpenseEntity> showExpense(String userId, Pageable pageable) {
        return expenseRepository.findAllByUserId(UUID.fromString(userId), pageable);
    }

    public ExpenseEntity replaceExpense(ReplaceExpenseDto replaceExpenseDto, String userId) {
        var expense = expenseRepository.findById(replaceExpenseDto.expenseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));

        if (!Objects.equals(expense.getUser().getId(), UUID.fromString(userId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not own this expense");
        }

        var newExpense = ExpenseEntity.builder()
                .id(replaceExpenseDto.expenseId())
                .user(expense.getUser())
                .name(replaceExpenseDto.name())
                .typeExpense(replaceExpenseDto.typeExpense())
                .price(replaceExpenseDto.price())
                .date(replaceExpenseDto.date())
                .build();

        return expenseRepository.save(newExpense);
    }
}
