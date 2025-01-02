package io.github.tolgadurak.creditmodulechallenge.loanapi.model;

import io.github.tolgadurak.creditmodulechallenge.loanapi.enums.CustomerLoanInstallmentStatus;
import lombok.*;

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
