package io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public final class CustomerLoanPayRestRequest {
    private final String customerReferenceId;
    private final String loanReferenceId;
    private final BigDecimal amount;
}
