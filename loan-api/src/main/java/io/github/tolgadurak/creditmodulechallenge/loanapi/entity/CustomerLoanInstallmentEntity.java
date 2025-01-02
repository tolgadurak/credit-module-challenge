package io.github.tolgadurak.creditmodulechallenge.loanapi.entity;

import io.github.tolgadurak.creditmodulechallenge.loanapi.enums.CustomerLoanInstallmentStatus;
import jakarta.persistence.*;
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
    private CustomerLoanInstallmentStatus status;
    @Column
    private Integer installmentNumber;
    @Column
    private BigDecimal installmentAmount;
    @Column
    private LocalDateTime dueDate;
}
