package com.banking.ms_loan.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("loans")
public class Loan {
    @Id
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









