package io.github.tolgadurak.creditmodulechallenge.loanapi.repository;

import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanInstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerLoanInstallmentJpaRepository extends JpaRepository<CustomerLoanInstallmentEntity, Long> {

    @Query("SELECT cli FROM CustomerLoanInstallmentEntity cli LEFT JOIN FETCH cli.customerLoan cl LEFT JOIN FETCH cl.customer c WHERE c.referenceId = :customerId AND cl.referenceId = :customerLoanId ORDER BY cli.installmentNumber ASC ")
    List<CustomerLoanInstallmentEntity> findByCustomerIdAndCustomerLoanId(@Param("customerId") String customerId, @Param("customerLoanId") String customerLoanId);
}
