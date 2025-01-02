package io.github.tolgadurak.creditmodulechallenge.loanapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanPayResult {
    private BigDecimal totalAmount;
}
