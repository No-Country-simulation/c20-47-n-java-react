package com.FlowBanck.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class TransactionDto {

    private String accountIdentifier;
    private BigDecimal amount;
    private String method;
}
