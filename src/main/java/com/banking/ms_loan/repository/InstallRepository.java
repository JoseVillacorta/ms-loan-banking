package com.banking.ms_loan.repository;

import com.banking.ms_loan.entity.Installment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import java.util.UUID;

public interface InstallRepository extends ReactiveCrudRepository<Installment, UUID> {
    Flux<Installment> findByLoanId(UUID loanId);
}
