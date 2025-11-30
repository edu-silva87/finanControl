package com.finanControl.user_service.controller;

import com.finanControl.user_service.dtos.NewExpenseDto;
import com.finanControl.user_service.dtos.ReplaceExpenseDto;
import com.finanControl.user_service.entity.ExpenseEntity;
import com.finanControl.user_service.services.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final ExpenseService expenseService;

    public UserController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/newExpense")
    public ResponseEntity<Void> newExpense(@RequestBody NewExpenseDto newExpense,
                                           JwtAuthenticationToken token) {
        expenseService.newExpense(newExpense, token.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/showExpenses")
    public ResponseEntity<Page<ExpenseEntity>> showExpense(Pageable pageable,
                                                           JwtAuthenticationToken token) {
        var response = expenseService.showExpense(token.getName(), pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/replaceExpense")
    public ResponseEntity<ExpenseEntity> replaceExpense(@RequestBody ReplaceExpenseDto replaceExpenseDto,
                                                        JwtAuthenticationToken token) {
        var newExpense = expenseService.replaceExpense(replaceExpenseDto, token.getName());
        return ResponseEntity.ok(newExpense);
    }
}
