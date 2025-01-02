package io.github.tolgadurak.creditmodulechallenge.loanapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_loan_installment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanInstallmentEntity extends AbstractEntity {
    @JoinColumn(name = "customer_loan_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerLoanEntity customerLoan;
    @Column
    private Boolean paid;
    @Column
    private Integer installmentNumber;
    @Column
    private BigDecimal installmentAmount;
    @Column
    private LocalDateTime dueDate;
}
