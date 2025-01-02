package io.github.tolgadurak.creditmodulechallenge.loanapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity extends AbstractEntity {
    @Column
    private String firstName;
    @Column
    private String lastName;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private List<CustomerLoanLimitEntity> loanLimits;
}
