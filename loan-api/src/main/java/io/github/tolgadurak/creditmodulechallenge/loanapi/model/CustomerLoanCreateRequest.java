package io.github.tolgadurak.creditmodulechallenge.loanapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanCreateRequest {
    private String name;
    private String description;
    private Integer installmentCount;
    private BigDecimal amount;
    private BigDecimal interestRate;
}
