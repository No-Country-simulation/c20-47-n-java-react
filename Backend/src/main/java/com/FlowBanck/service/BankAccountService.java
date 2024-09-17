package com.FlowBanck.service;

import com.FlowBanck.FlowBanckApplication;
import com.FlowBanck.entity.*;
import com.FlowBanck.payload.BankAccountUtils;
import com.FlowBanck.repository.BankAccountRepository;
import com.FlowBanck.repository.StateRepository;
import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import javax.management.StandardEmitterMBean;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private StateRepository stateRepository;
    public void saveBankAccount(UserEntity userEntity, String accountType) {
        Set<State> states = new HashSet<>();
        Optional<State> optionalState = this.stateRepository.findByState(EnumState.ACTIVA);

        if (optionalState.isPresent()){
            states.add(optionalState.get());
        }else {
            State newState = State.builder()
                    .state(EnumState.ACTIVA)
                    .build();
            this.stateRepository.save(newState);
            states.add(newState);
        }



        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(BankAccountUtils.generateAccountNumber())
                .cbu(BankAccountUtils.generateCbu())
                .alias(BankAccountUtils.generateAlias())
                .amount(BigDecimal.valueOf(0))
                .accountType(accountType)
                .state(states)
                .userEntity(userEntity)
                .build();

        this.bankAccountRepository.save(bankAccount);
    }


}




