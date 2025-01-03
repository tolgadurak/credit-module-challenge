package io.github.tolgadurak.creditmodulechallenge.loanapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLoanPayResponse {
    private BigDecimal paidAmount;
    private BigDecimal totalPaidAmount;
    private BigDecimal changeAmount;
    private List<Integer> installmentsPaid;
    private List<Integer> allInstallmentsPaid;
    private Boolean completeLoanPaid;
}
