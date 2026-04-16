package com.banking.ms_loan.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequest {
    private Long customerId;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private Integer installmentsCount;
    private String frequency;
}
