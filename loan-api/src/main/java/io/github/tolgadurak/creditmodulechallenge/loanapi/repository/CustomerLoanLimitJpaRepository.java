package io.github.tolgadurak.creditmodulechallenge.loanapi.repository;

import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanLimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLoanLimitJpaRepository extends JpaRepository<CustomerLoanLimitEntity, Long> {
}
