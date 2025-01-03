package io.github.tolgadurak.creditmodulechallenge.loanapi.repository;

import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerLoanJpaRepository extends JpaRepository<CustomerLoanEntity, Long> {
    @Query(value = "SELECT cl FROM CustomerLoanEntity cl LEFT JOIN FETCH cl.installments cli WHERE cl.customer.referenceId = :#{#customerId} AND (:#{#filter.installmentCount} IS NULL OR cl.installmentCount = :#{#filter.installmentCount}) AND (:#{#filter.paid} IS NULL OR cl.paid = :#{#filter.paid}) ORDER BY cli.installmentNumber ASC",
            countQuery = "SELECT COUNT(cl) FROM CustomerLoanEntity cl WHERE cl.customer.referenceId = :#{#customerId} AND (:#{#filter.installmentCount} IS NULL OR cl.installmentCount = :#{#filter.installmentCount}) AND (:#{#filter.paid} IS NULL OR cl.paid = :#{#filter.paid})")
    Page<CustomerLoanEntity> findByCustomerIdAndFilter(@Param("customerId") String customerId, @Param("filter") CustomerLoanFilterRequest filter, Pageable pageable);

    @Query(value = "SELECT cl FROM CustomerLoanEntity cl LEFT JOIN FETCH cl.installments cli LEFT JOIN FETCH cl.customer c WHERE c.referenceId = :#{#customerId} AND cl.referenceId = :#{#customerLoanId} ORDER BY cli.installmentNumber ASC")
    Optional<CustomerLoanEntity> findByCustomerIdAndCustomerLoanId(String customerId, String customerLoanId);
}
