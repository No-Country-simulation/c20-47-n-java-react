package com.FlowBanck.controller;

import com.FlowBanck.dto.TransactionDto;
import com.FlowBanck.entity.BankAccount;
import com.FlowBanck.repository.TransactionRepository;
import com.FlowBanck.security.filter.JwtAuthorizationFilter;
import com.FlowBanck.service.TransactionService;
import com.FlowBanck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @PostMapping
    public ResponseEntity<?> saveTransaction(@RequestBody TransactionDto transactionDto, Authentication authentication){
        BankAccount bankAccount = this.userService.getBankAccountByUser(authentication.getName());

        transactionService.createTransaction(transactionDto,bankAccount);
        return ResponseEntity.ok("Transaccion realizada");
    }
}
