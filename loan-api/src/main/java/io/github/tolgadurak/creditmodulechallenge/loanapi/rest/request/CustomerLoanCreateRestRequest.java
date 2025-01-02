package io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CustomerLoanCreateRestRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private Integer installmentCount;
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 14, fraction = 2)
    private BigDecimal amount;
    @NotNull
    @Digits(integer = 1, fraction = 2)
    private BigDecimal interestRate;
}
