package io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanPayResultRestResponse {
    private BigDecimal paidAmount;
    private BigDecimal totalPaidAmount;
    private BigDecimal changeAmount;
    private List<Integer> installmentsPaid;
    private List<Integer> allInstallmentsPaid;
    private Boolean completeLoanPaid;
}
