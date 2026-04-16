package com.banking.ms_loan.handler;

import com.banking.ms_loan.common.response.ApiResponse;
import com.banking.ms_loan.dto.request.LoanRequest;
import com.banking.ms_loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoanHandler {
    private final LoanService loanService;

    public Mono<ServerResponse> createLoan(ServerRequest request){
        return request.bodyToMono(LoanRequest.class)
                .flatMap(loanService::createLoan)
                .flatMap(loan -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.success(loan, "Préstamo y cuotas generadas con éxito", 201)));
    }

    public Mono<ServerResponse> getByCustomer(ServerRequest request){
        Long customerId = Long.parseLong(request.pathVariable("customerId"));
        return loanService.getLoansByCustomer(customerId)
                .collectList()
                .flatMap(loans -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.success(loans, "Préstamos del cliente obtenidos", 200)));
    }

    public Mono<ServerResponse> getInstallments(ServerRequest request){
        UUID loanId = UUID.fromString(request.pathVariable("loanId"));
        return loanService.getInstallmentsByLoan(loanId)
                .collectList()
                .flatMap(installments -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.success(installments, "Detalle de cuotas obtenido", 200)));
    }
}
