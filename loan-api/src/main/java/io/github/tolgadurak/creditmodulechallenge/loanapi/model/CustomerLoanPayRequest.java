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
public class CustomerLoanPayRequest {
    private BigDecimal amount;
}
