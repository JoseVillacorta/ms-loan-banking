package com.banking.ms_loan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("installments")
public class Installment {
    @Id
    private UUID id;
    private UUID loanId;
    private Integer installmentNumber;
    private BigDecimal amount;
    private LocalDate dueDate;
    private String status;
}
