package io.github.tolgadurak.creditmodulechallenge.loanapi.repository;

import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanInstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerLoanInstallmentJpaRepository extends JpaRepository<CustomerLoanInstallmentEntity, Long> {
    List<CustomerLoanInstallmentEntity> findByCustomerLoanCustomerReferenceIdAndCustomerLoanReferenceId(String customerId, String customerLoanId);
}
