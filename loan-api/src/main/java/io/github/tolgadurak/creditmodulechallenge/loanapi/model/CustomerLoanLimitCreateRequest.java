package io.github.tolgadurak.creditmodulechallenge.loanapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLoanLimitCreateRequest {
    private BigDecimal amount;
}
