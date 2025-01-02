package io.github.tolgadurak.creditmodulechallenge.loanapi.model.response;

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
    private Boolean paid;
    private Integer installmentNumber;
    private BigDecimal installmentAmount;
    private LocalDateTime dueDate;
}
