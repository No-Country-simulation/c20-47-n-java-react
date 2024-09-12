package com.FlowBanck.dto;

import com.FlowBanck.entity.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class BankAccountDto {

    private String accountNumber;
    private String cbu;
    private String alias;
    private BigDecimal amount;
    private String accountType;
    private String state;

    public static BankAccountDto fromBankAccount(BankAccount bankAccount){
        return BankAccountDto.builder()
                .accountNumber(bankAccount.getAccountNumber())
                .cbu(bankAccount.getCbu())
                .alias(bankAccount.getAlias())
                .amount(bankAccount.getAmount())
                .accountType(bankAccount.getAccountType())
                .state(bankAccount.getState())
                .build();
    }
}
