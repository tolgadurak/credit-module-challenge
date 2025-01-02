package io.github.tolgadurak.creditmodulechallenge.loanapi.model.response;

import io.github.tolgadurak.creditmodulechallenge.loanapi.enums.CustomerLoanInstallmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanInstallmentQueryResponse extends BaseResponse {
    private CustomerLoanInstallmentStatus status;
    private Integer installmentNumber;
    private BigDecimal installmentAmount;
    private LocalDateTime dueDate;
}
