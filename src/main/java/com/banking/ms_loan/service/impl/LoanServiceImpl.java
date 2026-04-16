package com.banking.ms_loan.service.impl;

import com.banking.ms_loan.dto.request.LoanRequest;
import com.banking.ms_loan.dto.response.LoanResponse;
import com.banking.ms_loan.entity.Installment;
import com.banking.ms_loan.entity.Loan;
import com.banking.ms_loan.repository.InstallRepository;
import com.banking.ms_loan.repository.LoanRepository;
import com.banking.ms_loan.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LoanServiceImpl implements LoanService {
        private final LoanRepository loanRepository;
        private final InstallRepository installRepository;
        private final WebClient webClient;

        public LoanServiceImpl(LoanRepository loanRepository, InstallRepository installRepository,
                        WebClient.Builder webClientBuilder) {
                this.loanRepository = loanRepository;
                this.installRepository = installRepository;
                this.webClient = webClientBuilder.build();
        }

        @Override
        @Transactional
        public Mono<LoanResponse> createLoan(LoanRequest request) {
                // 1. Validar con customer si el cliente existe
                return webClient.get()
                                .uri("http://ms-customer/api/v1/customers/" + request.getCustomerId())
                                .retrieve()
                                .onStatus(status -> status.value() == 404,
                                                clientResponse -> Mono.error(new ResponseStatusException(
                                                                HttpStatus.NOT_FOUND,
                                                                "El cliente no existe, no se puede crear el préstamo")))
                                .bodyToMono(Object.class)
                                // 2. Si el cliente existe procedemos con la logica del prestamo
                                .flatMap(customerExists -> {
                                        BigDecimal interesMultiplier = request.getInterestRate()
                                                        .divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP)
                                                        .add(BigDecimal.ONE);
                                        BigDecimal totalToPay = request.getAmount().multiply(interesMultiplier);
                                        BigDecimal installmentAmount = totalToPay.divide(
                                                        new BigDecimal(request.getInstallmentsCount()), 2,
                                                        RoundingMode.HALF_UP);

                                        Loan loanEntity = Loan.builder()
                                                        .customerId(request.getCustomerId())
                                                        .amount(request.getAmount())
                                                        .interestRate(request.getInterestRate())
                                                        .totalToPay(totalToPay)
                                                        .installmentsCount(request.getInstallmentsCount())
                                                        .frequency(request.getFrequency())
                                                        .status("ACTIVE")
                                                        .createdAt(LocalDateTime.now())
                                                        .build();

                                        return loanRepository.save(loanEntity)
                                                        .flatMap(savedLoan -> {
                                                                List<Installment> installments = new ArrayList<>();
                                                                LocalDate firstDueDate = LocalDate.now().plusDays(
                                                                                request.getFrequency().equalsIgnoreCase(
                                                                                                "WEEKLY") ? 7 : 30);
                                                                for (int i = 1; i <= request
                                                                                .getInstallmentsCount(); i++) {
                                                                        LocalDate dueDate = request.getFrequency()
                                                                                        .equalsIgnoreCase("WEEKLY")
                                                                                                        ? firstDueDate.plusWeeks(
                                                                                                                        i - 1)
                                                                                                        : firstDueDate.plusMonths(
                                                                                                                        i - 1);
                                                                        installments.add(Installment.builder()
                                                                                        .loanId(savedLoan.getId())
                                                                                        .installmentNumber(i)
                                                                                        .amount(installmentAmount)
                                                                                        .dueDate(dueDate)
                                                                                        .status("PENDING")
                                                                                        .build());
                                                                }
                                                                return installRepository.saveAll(installments)
                                                                                .then(Mono.just(mapToResponse(
                                                                                                savedLoan)));
                                                        });
                                });
        }

        @Override
        public Flux<LoanResponse> getLoansByCustomer(Long customerId) {
                return loanRepository.findByCustomerId(customerId).map(this::mapToResponse);
        }

        @Override
        public Flux<Installment> getInstallmentsByLoan(UUID loanId) {
                return installRepository.findByLoanId(loanId);
        }

        private LoanResponse mapToResponse(Loan entity) {
                return LoanResponse.builder()
                                .id(entity.getId())
                                .customerId(entity.getCustomerId())
                                .amount(entity.getAmount())
                                .interestRate(entity.getInterestRate())
                                .totalToPay(entity.getTotalToPay())
                                .installmentsCount(entity.getInstallmentsCount())
                                .frequency(entity.getFrequency())
                                .status(entity.getStatus())
                                .createdAt(entity.getCreatedAt())
                                .build();
        }

}
