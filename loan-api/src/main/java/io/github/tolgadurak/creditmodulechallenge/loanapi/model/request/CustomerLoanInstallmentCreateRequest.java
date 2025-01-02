package io.github.tolgadurak.creditmodulechallenge.loanapi.model.request;

import io.github.tolgadurak.creditmodulechallenge.loanapi.enums.CustomerLoanInstallmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLoanInstallmentCreateRequest {
    private CustomerLoanInstallmentStatus status;
    private Integer installmentNumber;
    private BigDecimal installmentAmount;
    private LocalDateTime dueDate;
}
