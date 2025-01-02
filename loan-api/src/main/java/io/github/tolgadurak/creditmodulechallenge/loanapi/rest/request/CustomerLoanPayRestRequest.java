package io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanPayRestRequest {
    private String customerReferenceId;
    private String loanReferenceId;
    private BigDecimal amount;
}
