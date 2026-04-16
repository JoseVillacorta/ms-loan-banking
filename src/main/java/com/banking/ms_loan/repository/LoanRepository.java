package com.banking.ms_loan.repository;

import com.banking.ms_loan.entity.Loan;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface LoanRepository extends ReactiveCrudRepository<Loan, UUID> {
    Flux<Loan> findByCustomerId(Long customerId);
}
