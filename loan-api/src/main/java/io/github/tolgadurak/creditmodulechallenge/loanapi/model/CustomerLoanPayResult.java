package io.github.tolgadurak.creditmodulechallenge.loanapi.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanPayResult {
    private BigDecimal totalAmount;
}
