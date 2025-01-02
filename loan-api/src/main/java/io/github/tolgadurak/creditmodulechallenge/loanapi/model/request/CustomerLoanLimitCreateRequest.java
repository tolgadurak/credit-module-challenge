package io.github.tolgadurak.creditmodulechallenge.loanapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLoanLimitCreateRequest {
    private BigDecimal amount;
}
