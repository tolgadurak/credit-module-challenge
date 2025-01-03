package io.github.tolgadurak.creditmodulechallenge.loanapi.service;

import io.github.tolgadurak.creditmodulechallenge.loanapi.config.LoanApiConfig;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanInstallmentEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.entity.CustomerLoanLimitEntity;
import io.github.tolgadurak.creditmodulechallenge.loanapi.exception.LoanApiBusinessException;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanFilterRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanInstallmentCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanLimitCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanPayRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanCreateResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanInstallmentQueryResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanPayResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanQueryResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.PagedResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.repository.CustomerJpaRepository;
import io.github.tolgadurak.creditmodulechallenge.loanapi.repository.CustomerLoanInstallmentJpaRepository;
import io.github.tolgadurak.creditmodulechallenge.loanapi.repository.CustomerLoanJpaRepository;
import io.github.tolgadurak.creditmodulechallenge.loanapi.service.mapper.CustomerLoanServiceMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CustomerLoanService {

    private final LoanApiConfig loanApiConfig;
    private final CustomerLoanServiceMapper customerLoanServiceMapper;
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerLoanJpaRepository customerLoanJpaRepository;
    private final CustomerLoanInstallmentJpaRepository customerLoanInstallmentJpaRepository;

    @Transactional
    public CustomerLoanCreateResponse createCustomerLoan(String customerId, CustomerLoanCreateRequest customerLoanCreateRequest) {
        CustomerEntity customerEntity = customerJpaRepository.findByReferenceId(customerId).orElseThrow(() -> new LoanApiBusinessException("errors.customerNotFound", HttpStatus.BAD_REQUEST));
        checkIfEligibleToGetLoan(customerLoanCreateRequest, customerEntity);
        BigDecimal totalAmount = calculateTotalAmount(customerLoanCreateRequest.getAmount(), customerLoanCreateRequest.getInterestRate());
        BigDecimal installmentAmount = calculateInstallmentAmount(totalAmount, customerLoanCreateRequest.getInstallmentCount());
        CustomerLoanEntity customerLoanEntity = createCustomerLoanEntity(customerLoanCreateRequest, customerEntity, totalAmount, installmentAmount);
        List<CustomerLoanInstallmentEntity> installments = createCustomerLoanInstallmentsEntity(customerLoanEntity, customerLoanCreateRequest, installmentAmount);
        updateCustomerLoanEntity(customerLoanEntity, installments);
        CustomerLoanLimitEntity customerLoanLimitEntity = createCustomerLoanLimitEntity(totalAmount, customerEntity, false);
        customerEntity.getLoanLimits().add(customerLoanLimitEntity);
        CustomerLoanEntity savedEntity = customerLoanJpaRepository.save(customerLoanEntity);
        return CustomerLoanCreateResponse.builder()
                .referenceId(savedEntity.getReferenceId())
                .build();
    }

    @Transactional
    public PagedResponse<CustomerLoanQueryResponse> queryCustomerLoan(String customerId, CustomerLoanFilterRequest filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        Page<CustomerLoanEntity> pages = customerLoanJpaRepository.findByCustomerIdAndFilter(customerId, filter, pageable);
        List<CustomerLoanQueryResponse> items = customerLoanServiceMapper.toCustomerLoanQueryResponseList(pages.getContent());
        return PagedResponse.<CustomerLoanQueryResponse>builder()
                .items(items)
                .page(pages.getNumber())
                .size(pages.getSize())
                .pageCount(pages.getTotalPages())
                .totalCount(pages.getTotalElements())
                .build();
    }

    @Transactional
    public List<CustomerLoanInstallmentQueryResponse> queryCustomerLoanInstallments(String customerId, String customerLoanId) {
        customerLoanJpaRepository.findByCustomerIdAndCustomerLoanId(customerId, customerLoanId)
                .orElseThrow(() -> new LoanApiBusinessException("errors.customerLoanNotFound", HttpStatus.BAD_REQUEST));
        List<CustomerLoanInstallmentEntity> installmentEntities = customerLoanInstallmentJpaRepository.findByCustomerIdAndCustomerLoanId(customerId, customerLoanId);
        return customerLoanServiceMapper.toCustomerLoanInstallmentQueryResponseList(installmentEntities);
    }

    @Transactional
    public CustomerLoanPayResponse payCustomerLoan(String customerId, String customerLoanId, CustomerLoanPayRequest customerLoanPayRequest) {
        CustomerLoanEntity customerLoanEntity = customerLoanJpaRepository.findByCustomerIdAndCustomerLoanId(customerId, customerLoanId)
                .orElseThrow(() -> new LoanApiBusinessException("errors.customerLoanNotFound", HttpStatus.BAD_REQUEST));

        if (customerLoanEntity.getPaid()) {
            throw new LoanApiBusinessException("errors.customerLoanIsAlreadyPaid", HttpStatus.BAD_REQUEST);
        }

        List<CustomerLoanInstallmentEntity> installmentsCanBePaid = customerLoanEntity.getInstallments().stream()
                .filter(Predicate.not(CustomerLoanInstallmentEntity::getPaid))
                .filter(installment -> installment.getDueDate()
                        .isBefore(LocalDateTime.now().plusMonths(loanApiConfig.getMaxNumberOfMonthsCanBePaid())))
                .sorted(Comparator.comparingInt(CustomerLoanInstallmentEntity::getInstallmentNumber))
                .toList();

        if (installmentsCanBePaid.isEmpty()) {
            throw new LoanApiBusinessException("errors.noAvailableInstallmentsToBePaid", HttpStatus.BAD_REQUEST);
        }

        if (customerLoanPayRequest.getAmount().compareTo(installmentsCanBePaid.getFirst().getInstallmentAmount()) < 0) {
            throw new LoanApiBusinessException("errors.insufficientAmountToPay", HttpStatus.BAD_REQUEST);
        }

        BigDecimal changeAmount = customerLoanPayRequest.getAmount();
        BigDecimal totalPaidAmount = BigDecimal.ZERO;
        List<Integer> installmentsPaid = new ArrayList<>();

        for (CustomerLoanInstallmentEntity installment : installmentsCanBePaid) {
            BigDecimal installmentAmount = installment.getInstallmentAmount();
            if (installmentAmount.compareTo(changeAmount) > 0) {
                break;
            }
            installment.setPaid(Boolean.TRUE);
            changeAmount = changeAmount.subtract(installmentAmount);
            totalPaidAmount = totalPaidAmount.add(installmentAmount);
            installmentsPaid.add(installment.getInstallmentNumber());
        }

        if (customerLoanEntity.getInstallments().stream().allMatch(CustomerLoanInstallmentEntity::getPaid)) {
            customerLoanEntity.setPaid(Boolean.TRUE);
            createCustomerLoanLimitEntity(customerLoanEntity.getTotalAmount(), customerLoanEntity.getCustomer(), true);
        }

        customerLoanEntity = customerLoanJpaRepository.save(customerLoanEntity);
        return CustomerLoanPayResponse.builder()
                .totalPaidAmount(totalPaidAmount)
                .changeAmount(changeAmount)
                .installmentsPaid(installmentsPaid)
                .allInstallmentsPaid(customerLoanEntity.getInstallments().stream()
                        .filter(CustomerLoanInstallmentEntity::getPaid)
                        .map(CustomerLoanInstallmentEntity::getInstallmentNumber)
                        .collect(Collectors.toList()))
                .completeLoanPaid(customerLoanEntity.getPaid())
                .build();
    }

    private void checkIfEligibleToGetLoan(CustomerLoanCreateRequest customerLoanCreateRequest, CustomerEntity customerEntity) {
        List<Integer> allowedInstallments = loanApiConfig.getAllowedInstallments();
        if (!allowedInstallments.contains(customerLoanCreateRequest.getInstallmentCount())) {
            throw new LoanApiBusinessException("errors.invalidInstallmentCount", HttpStatus.BAD_REQUEST);
        }
        if (customerLoanCreateRequest.getInterestRate().compareTo(loanApiConfig.getMinInterestRate()) < 0 ||
                customerLoanCreateRequest.getInterestRate().compareTo(loanApiConfig.getMaxInterestRate()) > 0) {
            throw new LoanApiBusinessException("errors.invalidInterestRate", HttpStatus.BAD_REQUEST);
        }
        int customerLoanLimitResult = customerEntity.getLoanLimits().stream()
                .map(CustomerLoanLimitEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .compareTo(customerLoanCreateRequest.getAmount());
        if (customerLoanLimitResult < 0) {
            throw new LoanApiBusinessException("errors.customerDoesNotHaveEnoughLimit", HttpStatus.BAD_REQUEST);
        }
    }

    private List<CustomerLoanInstallmentEntity> createCustomerLoanInstallmentsEntity(CustomerLoanEntity customerLoanEntity, CustomerLoanCreateRequest customerLoanCreateRequest, BigDecimal installmentAmount) {
        return IntStream.rangeClosed(1, customerLoanCreateRequest.getInstallmentCount())
                .mapToObj(count -> {
                    CustomerLoanInstallmentEntity customerLoanInstallmentEntity = customerLoanServiceMapper.toEntity(CustomerLoanInstallmentCreateRequest.builder()
                            .paid(Boolean.FALSE)
                            .installmentNumber(count)
                            .installmentAmount(installmentAmount)
                            .dueDate(LocalDateTime.now().plusMonths(count).with(TemporalAdjusters.firstDayOfMonth()))
                            .build());
                    customerLoanInstallmentEntity.setCustomerLoan(customerLoanEntity);
                    return customerLoanInstallmentEntity;
                }).toList();
    }

    private CustomerLoanEntity createCustomerLoanEntity(CustomerLoanCreateRequest customerLoanCreateRequest, CustomerEntity customerEntity, BigDecimal totalAmount, BigDecimal installmentAmount) {
        CustomerLoanEntity entity = customerLoanServiceMapper.toEntity(customerLoanCreateRequest);
        entity.setCustomer(customerEntity);
        entity.setTotalAmount(totalAmount);
        entity.setInstallmentAmount(installmentAmount);
        entity.setPaid(Boolean.FALSE);
        return entity;
    }

    private BigDecimal calculateTotalAmount(BigDecimal amount, BigDecimal interestRate) {
        return amount.multiply(BigDecimal.ONE.add(interestRate)).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateInstallmentAmount(BigDecimal totalAmount, Integer installmentCount) {
        return totalAmount.divide(new BigDecimal(installmentCount), 2, RoundingMode.HALF_UP);
    }

    private CustomerLoanLimitEntity createCustomerLoanLimitEntity(BigDecimal totalAmount, CustomerEntity customerEntity, boolean isPositive) {
        CustomerLoanLimitEntity customerLoanLimitEntity = customerLoanServiceMapper.toEntity(CustomerLoanLimitCreateRequest.builder()
                .amount(isPositive ? totalAmount : totalAmount.negate())
                .build());
        customerLoanLimitEntity.setCustomer(customerEntity);
        return customerLoanLimitEntity;
    }

    private void updateCustomerLoanEntity(CustomerLoanEntity customerLoanEntity, List<CustomerLoanInstallmentEntity> installments) {
        customerLoanEntity.setInstallments(installments);
        customerLoanEntity.setFirstDueDate(installments.getFirst().getDueDate());
        customerLoanEntity.setLastDueDate(installments.getLast().getDueDate());
    }
}
