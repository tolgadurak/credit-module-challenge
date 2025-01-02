package io.github.tolgadurak.creditmodulechallenge.loanapi.service;

import io.github.tolgadurak.creditmodulechallenge.loanapi.config.LoanApiConfig;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanInstallmentEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanLimitEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.enums.CustomerLoanInstallmentStatus;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.*;
import io.github.tolgadurak.creditmodulechallenge.loanapi.repository.CustomerJpaRepository;
import io.github.tolgadurak.creditmodulechallenge.loanapi.repository.CustomerLoanJpaRepository;
import io.github.tolgadurak.creditmodulechallenge.loanapi.repository.CustomerLoanLimitJpaRepository;
import io.github.tolgadurak.creditmodulechallenge.loanapi.service.mapper.CustomerLoanServiceMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CustomerLoanService {

    private final LoanApiConfig loanApiConfig;
    private final CustomerLoanServiceMapper customerLoanServiceMapper;
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerLoanJpaRepository customerLoanJpaRepository;
    private final CustomerLoanLimitJpaRepository customerLoanLimitJpaRepository;

    @Transactional
    public void createCustomerLoan(String customerId, CustomerLoanCreateRequest customerLoanCreateRequest) {
        CustomerEntity customerEntity = customerJpaRepository.findByReferenceId(customerId).orElseThrow(RuntimeException::new);
        checkIfEligible(customerLoanCreateRequest, customerEntity);
        BigDecimal totalAmount = calculateTotalAmount(customerLoanCreateRequest.getAmount(), customerLoanCreateRequest.getInterestRate());
        BigDecimal installmentAmount = calculateInstallmentAmount(totalAmount, customerLoanCreateRequest.getInstallmentCount());
        List<CustomerLoanInstallmentEntity> installments = createCustomerLoanInstallmentsEntity(customerLoanCreateRequest, installmentAmount);
        CustomerLoanEntity entity = createCustomerLoanEntity(customerLoanCreateRequest, customerEntity, installments, totalAmount, installmentAmount);
        CustomerLoanLimitEntity customerLoanLimitEntity = createCustomerLoanLimitEntity(totalAmount, customerEntity);
        customerEntity.getLoanLimits().add(customerLoanLimitEntity);
        customerLoanJpaRepository.save(entity);
    }

    @Transactional
    public CustomerLoanPayResult payCustomerLoan(String customerId, String loanId, CustomerLoanPayRequest customerLoanPayRequest) {
        return CustomerLoanPayResult.builder().build();
    }

    private void checkIfEligible(CustomerLoanCreateRequest customerLoanCreateRequest, CustomerEntity customerEntity) {
        int customerLoanLimitResult = customerEntity.getLoanLimits().stream()
                .map(CustomerLoanLimitEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .compareTo(customerLoanCreateRequest.getAmount());
        if (customerLoanLimitResult < 0) {
            throw new RuntimeException("Not enough loan limit"); // TODO: Throw managed exception
        }
        List<Integer> allowedInstallments = loanApiConfig.getAllowedInstallments();
        if (!allowedInstallments.contains(customerLoanCreateRequest.getInstallmentCount())) {
            throw new RuntimeException("Invalid installment count"); // TODO: Throw managed exception
        }
        if (customerLoanCreateRequest.getInterestRate().compareTo(loanApiConfig.getMinimumInterestRate()) < 0 ||
                customerLoanCreateRequest.getInterestRate().compareTo(loanApiConfig.getMaximumInterestRate()) > 0) {
            throw new RuntimeException("Invalid interest rate"); // TODO: Throw managed exception
        }
    }

    private List<CustomerLoanInstallmentEntity> createCustomerLoanInstallmentsEntity(CustomerLoanCreateRequest customerLoanCreateRequest, BigDecimal installmentAmount) {
        return IntStream.of(customerLoanCreateRequest.getInstallmentCount())
                .mapToObj(count ->
                        customerLoanServiceMapper.toEntity(CustomerLoanInstallmentCreateRequest.builder()
                                .status(CustomerLoanInstallmentStatus.ACTIVE)
                                .installmentNumber(count)
                                .installmentAmount(installmentAmount)
                                .dueDate(LocalDateTime.now().plusMonths(1 + count).with(TemporalAdjusters.firstDayOfMonth()))
                                .build())
                ).toList();
    }

    private CustomerLoanEntity createCustomerLoanEntity(CustomerLoanCreateRequest customerLoanCreateRequest, CustomerEntity customerEntity, List<CustomerLoanInstallmentEntity> installments, BigDecimal totalAmount, BigDecimal installmentAmount) {
        CustomerLoanEntity entity = customerLoanServiceMapper.toEntity(customerLoanCreateRequest);
        entity.setCustomer(customerEntity);
        entity.setInstallments(installments);
        entity.setTotalAmount(totalAmount);
        entity.setInstallmentAmount(installmentAmount);
        entity.setFirstDueDate(LocalDateTime.now().plusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));
        entity.setLastDueDate(LocalDateTime.now().plusMonths(1 + installments.size()).with(TemporalAdjusters.firstDayOfMonth()));
        return entity;
    }

    private BigDecimal calculateTotalAmount(BigDecimal amount, BigDecimal interestRate) {
        return amount.multiply(BigDecimal.ONE.add(interestRate)).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateInstallmentAmount(BigDecimal totalAmount, Integer installmentCount) {
        return totalAmount.divide(new BigDecimal(installmentCount), 2, RoundingMode.HALF_UP);
    }

    private CustomerLoanLimitEntity createCustomerLoanLimitEntity(BigDecimal totalAmount, CustomerEntity customerEntity) {
        CustomerLoanLimitEntity customerLoanLimitEntity = customerLoanServiceMapper.toEntity(CustomerLoanLimitCreateRequest.builder()
                .amount(totalAmount)
                .build());
        customerLoanLimitEntity.setCustomer(customerEntity);
        return customerLoanLimitEntity;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }
}
