package io.github.tolgadurak.creditmodulechallenge.loanapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "customer_loan_limit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanLimitEntity extends AbstractEntity {
    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerEntity customer;
    @Column
    private BigDecimal amount;
}
