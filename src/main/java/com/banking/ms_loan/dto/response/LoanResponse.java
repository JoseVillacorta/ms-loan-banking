package com.banking.ms_loan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {
    private UUID id;
    private Long customerId;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private BigDecimal totalToPay;
    private Integer installmentsCount;
    private String frequency;
    private String status;
    private LocalDateTime createdAt;
}
