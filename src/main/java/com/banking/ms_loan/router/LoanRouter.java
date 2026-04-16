package com.banking.ms_loan.router;

import com.banking.ms_loan.handler.LoanHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LoanRouter {
    @Bean
    public RouterFunction<ServerResponse> loanRoutes(LoanHandler handler){
        return route(POST("/api/v1/loans"), handler::createLoan)
                .andRoute(GET("/api/v1/loans/customer/{customerId}"), handler::getByCustomer)
                .andRoute(GET("/api/v1/loans/{loanId}/installments"), handler::getInstallments);
    }
}
