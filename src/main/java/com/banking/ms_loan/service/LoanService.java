package com.banking.ms_loan.service;

import com.banking.ms_loan.dto.request.LoanRequest;
import com.banking.ms_loan.dto.response.LoanResponse;
import com.banking.ms_loan.entity.Installment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanService {
    Mono<LoanResponse> createLoan(LoanRequest request);
    Flux<LoanResponse> getLoansByCustomer(Long customerId);
    Flux<Installment> getInstallmentsByLoan(UUID loanId);
}
