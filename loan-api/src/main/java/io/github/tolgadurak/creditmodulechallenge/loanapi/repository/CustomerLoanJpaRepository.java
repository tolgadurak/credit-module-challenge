package io.github.tolgadurak.creditmodulechallenge.loanapi.repository;

import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLoanJpaRepository extends JpaRepository<CustomerLoanEntity, Long> {
}
