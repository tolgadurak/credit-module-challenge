package io.github.tolgadurak.creditmodulechallenge.loanapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer_loan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoanEntity extends AbstractEntity {
    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private CustomerEntity customer;
    @OneToMany(mappedBy = "customerLoan", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private List<CustomerLoanInstallmentEntity> installments;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Integer installmentCount;
    @Column
    private BigDecimal installmentAmount;
    @Column
    private BigDecimal amount;
    @Column
    private BigDecimal totalAmount;
    @Column
    private BigDecimal interestRate;
    @Column
    private LocalDateTime firstDueDate;
    @Column
    private LocalDateTime lastDueDate;

}
