package io.github.tolgadurak.creditmodulechallenge.loanapi.repository;

import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerLoanJpaRepository extends JpaRepository<CustomerLoanEntity, Long> {
    @Query(value = "SELECT cl FROM CustomerLoanEntity cl LEFT JOIN FETCH cl.installments WHERE cl.customer.referenceId = :#{#customerId} AND cl.installmentCount = :#{#filter.installmentCount} AND cl.paid = :#{#filter.paid}",
            countQuery = "SELECT COUNT(cl) FROM CustomerLoanEntity cl WHERE cl.customer.referenceId = :#{#customerId} AND cl.installmentCount = :#{#filter.installmentCount} AND cl.paid = :#{#filter.paid}")
    Page<CustomerLoanEntity> findByCustomerIdAndFilter(@Param("customerId") String customerId, @Param("filter") CustomerLoanFilterRequest filter, Pageable pageable);
}
