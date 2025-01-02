package io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response;

import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanInstallmentQueryResponse;
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
public class CustomerLoanQueryRestResponse extends BaseRestResponse {
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
    private List<CustomerLoanInstallmentQueryResponse> installments;
}
