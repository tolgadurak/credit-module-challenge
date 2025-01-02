package io.github.tolgadurak.creditmodulechallenge.loanapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanQueryResponse extends BaseResponse {
    private List<CustomerLoanInstallmentQueryResponse> installments;
    private String name;
    private String description;
    private Integer installmentCount;
    private BigDecimal installmentAmount;
    private BigDecimal amount;
    private BigDecimal totalAmount;
    private BigDecimal interestRate;
    private LocalDateTime firstDueDate;
    private LocalDateTime lastDueDate;
    private Boolean paid;
}
