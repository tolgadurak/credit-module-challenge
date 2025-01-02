package io.github.tolgadurak.creditmodulechallenge.loanapi.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLoanLimitCreateRequest {
    private BigDecimal amount;
}
