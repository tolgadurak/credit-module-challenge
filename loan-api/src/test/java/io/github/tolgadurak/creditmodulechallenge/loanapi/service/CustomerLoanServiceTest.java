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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomerLoanServiceTest {

    @Mock
    private LoanApiConfig loanApiConfig;
    @Mock
    private CustomerLoanServiceMapper customerLoanServiceMapper;
    @Mock
    private CustomerJpaRepository customerJpaRepository;
    @Mock
    private CustomerLoanJpaRepository customerLoanJpaRepository;
    @Mock
    private CustomerLoanInstallmentJpaRepository customerLoanInstallmentJpaRepository;
    @Mock
    private Page<CustomerLoanEntity> customerLoanEntityPage;
    @InjectMocks
    private CustomerLoanService customerLoanService;

    private CustomerLoanEntity customerLoanEntity;
    private CustomerLoanCreateRequest customerLoanCreateRequest;
    private CustomerEntity customerEntity;
    private CustomerLoanInstallmentEntity customerLoanInstallmentEntity;
    private CustomerLoanQueryResponse customerLoanQueryResponse;
    private CustomerLoanInstallmentQueryResponse customerLoanInstallmentQueryResponse;
    private List<CustomerLoanEntity> customerLoanEntities;

    private static final String CUSTOMER_ID = "CUSTOMER_ID";
    private static final String CUSTOMER_LOAN_ID = "CUSTOMER_LOAN_ID";

    @BeforeEach
    public void setUp() {
        LocalDateTime now = LocalDateTime.now();

        customerEntity = new CustomerEntity();
        customerEntity.setFirstName("FirstName");
        customerEntity.setLastName("LastName");
        customerEntity.setLoanLimits(new ArrayList<>());

        CustomerLoanLimitEntity customerLoanLimitEntity = new CustomerLoanLimitEntity();
        customerLoanLimitEntity.setAmount(new BigDecimal("10000.00"));
        customerLoanLimitEntity.setCustomer(customerEntity);

        List<CustomerLoanLimitEntity> customerLoanLimitEntities = new ArrayList<>();
        customerLoanLimitEntities.add(customerLoanLimitEntity);
        customerEntity.setLoanLimits(customerLoanLimitEntities);

        customerLoanCreateRequest = new CustomerLoanCreateRequest();
        customerLoanCreateRequest.setName("Loan Name");
        customerLoanCreateRequest.setDescription("Loan Description");
        customerLoanCreateRequest.setInstallmentCount(12);
        customerLoanCreateRequest.setAmount(new BigDecimal("1000.00"));
        customerLoanCreateRequest.setInterestRate(new BigDecimal("0.2"));

        customerLoanEntity = new CustomerLoanEntity();
        customerLoanEntity.setReferenceId(CUSTOMER_LOAN_ID);
        customerLoanEntity.setName(customerLoanCreateRequest.getName());
        customerLoanEntity.setDescription(customerLoanCreateRequest.getDescription());
        customerLoanEntity.setInstallmentCount(customerLoanCreateRequest.getInstallmentCount());
        customerLoanEntity.setInstallmentAmount(new BigDecimal("100.00"));
        customerLoanEntity.setAmount(customerLoanCreateRequest.getAmount());
        customerLoanEntity.setInterestRate(customerLoanCreateRequest.getInterestRate());
        customerLoanEntity.setPaid(Boolean.FALSE);
        customerLoanEntity.setFirstDueDate(now.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));
        customerLoanEntity.setFirstDueDate(now.plusMonths(customerLoanCreateRequest.getInstallmentCount()).with(TemporalAdjusters.firstDayOfMonth()));

        customerLoanInstallmentEntity = new CustomerLoanInstallmentEntity();
        customerLoanInstallmentEntity.setPaid(Boolean.FALSE);
        customerLoanInstallmentEntity.setInstallmentNumber(1);
        customerLoanInstallmentEntity.setInstallmentAmount(new BigDecimal("100.00"));
        customerLoanInstallmentEntity.setDueDate(LocalDateTime.now().plusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));

        List<CustomerLoanInstallmentEntity> customerLoanInstallmentEntities = new ArrayList<>();
        customerLoanInstallmentEntities.add(customerLoanInstallmentEntity);
        customerLoanEntity.setInstallments(customerLoanInstallmentEntities);

        customerLoanEntities = new ArrayList<>();
        customerLoanEntities.add(customerLoanEntity);

        customerLoanQueryResponse = new CustomerLoanQueryResponse();
        customerLoanQueryResponse.setReferenceId(CUSTOMER_LOAN_ID);
        customerLoanQueryResponse.setName(customerLoanEntity.getName());
        customerLoanQueryResponse.setDescription(customerLoanEntity.getDescription());
        customerLoanQueryResponse.setInstallmentCount(customerLoanEntity.getInstallmentCount());
        customerLoanQueryResponse.setInstallmentAmount(customerLoanEntity.getInstallmentAmount());
        customerLoanQueryResponse.setAmount(customerLoanEntity.getAmount());
        customerLoanQueryResponse.setInterestRate(customerLoanEntity.getInterestRate());
        customerLoanQueryResponse.setPaid(customerLoanEntity.getPaid());
        customerLoanQueryResponse.setFirstDueDate(customerLoanEntity.getFirstDueDate());
        customerLoanQueryResponse.setLastDueDate(customerLoanEntity.getLastDueDate());

        customerLoanInstallmentQueryResponse = new CustomerLoanInstallmentQueryResponse();
        customerLoanInstallmentQueryResponse.setPaid(Boolean.FALSE);
        customerLoanInstallmentQueryResponse.setInstallmentNumber(1);
        customerLoanInstallmentQueryResponse.setInstallmentAmount(new BigDecimal("100.00"));
        customerLoanInstallmentQueryResponse.setDueDate(now.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));


        when(loanApiConfig.getMinInterestRate()).thenReturn(new BigDecimal("0.2"));
        when(loanApiConfig.getMaxInterestRate()).thenReturn(new BigDecimal("0.5"));
        when(loanApiConfig.getMaxNumberOfMonthsCanBePaid()).thenReturn(3);
        when(loanApiConfig.getAllowedInstallments()).thenReturn(List.of(6, 9, 12, 24));
    }

    @Test
    void shouldCreateCustomerLoan() {
        CustomerLoanLimitEntity customerLoanLimitEntity = new CustomerLoanLimitEntity();
        customerLoanLimitEntity.setAmount(new BigDecimal("1000.00").negate());
        customerLoanLimitEntity.setCustomer(customerEntity);

        when(customerJpaRepository.findByReferenceId(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));
        when(customerLoanServiceMapper.toEntity(any(CustomerLoanCreateRequest.class))).thenReturn(customerLoanEntity);
        when(customerLoanServiceMapper.toEntity(any(CustomerLoanInstallmentCreateRequest.class))).thenReturn(customerLoanInstallmentEntity);
        when(customerLoanServiceMapper.toEntity(any(CustomerLoanLimitCreateRequest.class))).thenReturn(customerLoanLimitEntity);
        when(customerLoanJpaRepository.save(any())).thenReturn(customerLoanEntity);

        CustomerLoanCreateResponse customerLoanCreateResponse = customerLoanService.createCustomerLoan(CUSTOMER_ID, customerLoanCreateRequest);

        assertEquals(CUSTOMER_LOAN_ID, customerLoanCreateResponse.getReferenceId());
    }

    @Test
    void shouldNotCreateCustomerLoan_InstallmentCountNotAllowed() {
        customerLoanCreateRequest.setInstallmentCount(1);

        when(customerJpaRepository.findByReferenceId(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));

        LoanApiBusinessException loanApiBusinessException = assertThrows(LoanApiBusinessException.class, () -> customerLoanService.createCustomerLoan(CUSTOMER_ID, customerLoanCreateRequest));

        assertEquals("errors.invalidInstallmentCount", loanApiBusinessException.getCode());
    }

    @Test
    void shouldNotCreateCustomerLoan_InterestRateGreaterThanAllowed() {
        customerLoanCreateRequest.setInterestRate(new BigDecimal("1.9"));

        when(customerJpaRepository.findByReferenceId(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));

        LoanApiBusinessException loanApiBusinessException = assertThrows(LoanApiBusinessException.class, () -> customerLoanService.createCustomerLoan(CUSTOMER_ID, customerLoanCreateRequest));

        assertEquals("errors.invalidInterestRate", loanApiBusinessException.getCode());
    }

    @Test
    void shouldNotCreateCustomerLoan_InterestRateLessThanAllowed() {
        customerLoanCreateRequest.setInterestRate(new BigDecimal("0.1"));

        when(customerJpaRepository.findByReferenceId(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));

        LoanApiBusinessException loanApiBusinessException = assertThrows(LoanApiBusinessException.class, () -> customerLoanService.createCustomerLoan(CUSTOMER_ID, customerLoanCreateRequest));

        assertEquals("errors.invalidInterestRate", loanApiBusinessException.getCode());
    }

    @Test
    void shouldNotCreateCustomerLoan_CustomerNotHasEnoughLimit() {
        customerEntity.getLoanLimits().getFirst().setAmount(BigDecimal.ZERO);

        when(customerJpaRepository.findByReferenceId(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));

        LoanApiBusinessException loanApiBusinessException = assertThrows(LoanApiBusinessException.class, () -> customerLoanService.createCustomerLoan(CUSTOMER_ID, customerLoanCreateRequest));

        assertEquals("errors.customerDoesNotHaveEnoughLimit", loanApiBusinessException.getCode());
    }

    @Test
    void shouldQueryCustomerLoan() {
        CustomerLoanFilterRequest customerLoanFilterRequest = new CustomerLoanFilterRequest();
        customerLoanFilterRequest.setPage(1);
        customerLoanFilterRequest.setSize(10);

        when(customerLoanJpaRepository.findByCustomerIdAndFilter(anyString(), any(CustomerLoanFilterRequest.class), any(Pageable.class))).thenReturn(customerLoanEntityPage);
        when(customerLoanEntityPage.getContent()).thenReturn(customerLoanEntities);
        when(customerLoanServiceMapper.toCustomerLoanQueryResponseList(anyList())).thenReturn(List.of(customerLoanQueryResponse));

        PagedResponse<CustomerLoanQueryResponse> pagedResponse = customerLoanService.queryCustomerLoan(CUSTOMER_ID, customerLoanFilterRequest);

        assertNotNull(pagedResponse);
        assertNotNull(pagedResponse.getItems());
        assertFalse(pagedResponse.getItems().isEmpty());
        assertEquals(CUSTOMER_LOAN_ID, pagedResponse.getItems().getFirst().getReferenceId());
        assertEquals(customerLoanEntity.getName(), pagedResponse.getItems().getFirst().getName());
        assertEquals(customerLoanEntity.getDescription(), pagedResponse.getItems().getFirst().getDescription());
        assertEquals(customerLoanEntity.getInstallmentCount(), pagedResponse.getItems().getFirst().getInstallmentCount());
        assertEquals(customerLoanEntity.getInstallmentAmount(), pagedResponse.getItems().getFirst().getInstallmentAmount());
        assertEquals(customerLoanEntity.getInterestRate(), pagedResponse.getItems().getFirst().getInterestRate());
        assertEquals(customerLoanEntity.getPaid(), pagedResponse.getItems().getFirst().getPaid());
        assertEquals(customerLoanEntity.getFirstDueDate(), pagedResponse.getItems().getFirst().getFirstDueDate());
        assertEquals(customerLoanEntity.getLastDueDate(), pagedResponse.getItems().getFirst().getLastDueDate());
    }

    @Test
    void shouldQueryCustomerLoanInstallments() {
        when(customerLoanJpaRepository.findByCustomerIdAndCustomerLoanId(CUSTOMER_ID, CUSTOMER_LOAN_ID)).thenReturn(Optional.of(customerLoanEntity));
        when(customerLoanInstallmentJpaRepository.findByCustomerIdAndCustomerLoanId(CUSTOMER_ID, CUSTOMER_LOAN_ID)).thenReturn(List.of(customerLoanInstallmentEntity));
        when(customerLoanServiceMapper.toCustomerLoanInstallmentQueryResponseList(anyList())).thenReturn(List.of(customerLoanInstallmentQueryResponse));

        List<CustomerLoanInstallmentQueryResponse> customerLoanInstallmentQueryResponses = customerLoanService.queryCustomerLoanInstallments(CUSTOMER_ID, CUSTOMER_LOAN_ID);

        assertNotNull(customerLoanInstallmentQueryResponses);
        assertFalse(customerLoanInstallmentQueryResponses.isEmpty());
        assertEquals(customerLoanInstallmentQueryResponse.getInstallmentNumber(), customerLoanInstallmentQueryResponses.getFirst().getInstallmentNumber());
        assertEquals(customerLoanInstallmentQueryResponse.getInstallmentAmount(), customerLoanInstallmentQueryResponses.getFirst().getInstallmentAmount());
        assertEquals(customerLoanInstallmentQueryResponse.getDueDate(), customerLoanInstallmentQueryResponses.getFirst().getDueDate());
        assertEquals(customerLoanInstallmentQueryResponse.getPaid(), customerLoanInstallmentQueryResponses.getFirst().getPaid());
    }

    @Test
    void shouldPayCustomerLoan() {
        CustomerLoanLimitEntity customerLoanLimitEntity = new CustomerLoanLimitEntity();
        customerLoanLimitEntity.setAmount(new BigDecimal("100.00"));
        customerLoanLimitEntity.setCustomer(customerEntity);

        BigDecimal amount = new BigDecimal("100.00");
        CustomerLoanPayRequest customerLoanPayRequest = new CustomerLoanPayRequest();
        customerLoanPayRequest.setAmount(amount);
        when(customerLoanJpaRepository.findByCustomerIdAndCustomerLoanId(CUSTOMER_ID, CUSTOMER_LOAN_ID)).thenReturn(Optional.of(customerLoanEntity));
        when(customerLoanServiceMapper.toEntity(any(CustomerLoanLimitCreateRequest.class))).thenReturn(customerLoanLimitEntity);
        when(customerLoanJpaRepository.save(any())).thenReturn(customerLoanEntity);

        CustomerLoanPayResponse customerLoanPayResponse = customerLoanService.payCustomerLoan(CUSTOMER_ID, CUSTOMER_LOAN_ID, customerLoanPayRequest);

        assertNotNull(customerLoanPayResponse);
        assertEquals(amount, customerLoanPayResponse.getPaidAmount());
        assertEquals(amount, customerLoanPayResponse.getTotalPaidAmount());
        assertEquals(new BigDecimal("0.00"), customerLoanPayResponse.getChangeAmount());
        assertEquals(List.of(1), customerLoanPayResponse.getInstallmentsPaid());
        assertEquals(List.of(1), customerLoanPayResponse.getAllInstallmentsPaid());
        assertEquals(Boolean.TRUE, customerLoanPayResponse.getCompleteLoanPaid());
    }

    @Test
    void shouldPayCustomerLoan_PartialPayment() {
        CustomerLoanInstallmentEntity secondInstallmentEntity = new CustomerLoanInstallmentEntity();
        secondInstallmentEntity.setPaid(Boolean.FALSE);
        secondInstallmentEntity.setInstallmentNumber(2);
        secondInstallmentEntity.setInstallmentAmount(new BigDecimal("100.00"));
        secondInstallmentEntity.setDueDate(LocalDateTime.now().plusMonths(2).with(TemporalAdjusters.firstDayOfMonth()));
        customerLoanEntity.getInstallments().add(secondInstallmentEntity);

        CustomerLoanLimitEntity customerLoanLimitEntity = new CustomerLoanLimitEntity();
        customerLoanLimitEntity.setAmount(new BigDecimal("100.00"));
        customerLoanLimitEntity.setCustomer(customerEntity);

        BigDecimal amount = new BigDecimal("100.00");
        CustomerLoanPayRequest customerLoanPayRequest = new CustomerLoanPayRequest();
        customerLoanPayRequest.setAmount(amount);
        when(customerLoanJpaRepository.findByCustomerIdAndCustomerLoanId(CUSTOMER_ID, CUSTOMER_LOAN_ID)).thenReturn(Optional.of(customerLoanEntity));
        when(customerLoanServiceMapper.toEntity(any(CustomerLoanLimitCreateRequest.class))).thenReturn(customerLoanLimitEntity);
        when(customerLoanJpaRepository.save(any())).thenReturn(customerLoanEntity);

        CustomerLoanPayResponse customerLoanPayResponse = customerLoanService.payCustomerLoan(CUSTOMER_ID, CUSTOMER_LOAN_ID, customerLoanPayRequest);

        assertNotNull(customerLoanPayResponse);
        assertEquals(amount, customerLoanPayResponse.getPaidAmount());
        assertEquals(amount, customerLoanPayResponse.getTotalPaidAmount());
        assertEquals(new BigDecimal("0.00"), customerLoanPayResponse.getChangeAmount());
        assertEquals(List.of(1), customerLoanPayResponse.getInstallmentsPaid());
        assertEquals(List.of(1), customerLoanPayResponse.getAllInstallmentsPaid());
        assertEquals(Boolean.FALSE, customerLoanPayResponse.getCompleteLoanPaid());
    }

    @Test
    void shouldNotPayCustomerLoan_AlreadyPaid() {
        customerLoanEntity.setPaid(Boolean.TRUE);

        BigDecimal amount = new BigDecimal("100.00");
        CustomerLoanPayRequest customerLoanPayRequest = new CustomerLoanPayRequest();
        customerLoanPayRequest.setAmount(amount);
        when(customerLoanJpaRepository.findByCustomerIdAndCustomerLoanId(CUSTOMER_ID, CUSTOMER_LOAN_ID)).thenReturn(Optional.of(customerLoanEntity));

        LoanApiBusinessException loanApiBusinessException = assertThrows(LoanApiBusinessException.class, () -> customerLoanService.payCustomerLoan(CUSTOMER_ID, CUSTOMER_LOAN_ID, customerLoanPayRequest));
        assertNotNull(loanApiBusinessException);
        assertEquals("errors.customerLoanIsAlreadyPaid", loanApiBusinessException.getCode());
    }

    @Test
    void shouldNotPayCustomerLoan_NoAvailableInstallmentsToBePaid() {
        LocalDateTime now = LocalDateTime.now();
        customerLoanEntity.getInstallments().getFirst().setDueDate(now.plusMonths(4).with(TemporalAdjusters.firstDayOfMonth()));

        BigDecimal amount = new BigDecimal("100.00");
        CustomerLoanPayRequest customerLoanPayRequest = new CustomerLoanPayRequest();
        customerLoanPayRequest.setAmount(amount);
        when(customerLoanJpaRepository.findByCustomerIdAndCustomerLoanId(CUSTOMER_ID, CUSTOMER_LOAN_ID)).thenReturn(Optional.of(customerLoanEntity));

        LoanApiBusinessException loanApiBusinessException = assertThrows(LoanApiBusinessException.class, () -> customerLoanService.payCustomerLoan(CUSTOMER_ID, CUSTOMER_LOAN_ID, customerLoanPayRequest));
        assertNotNull(loanApiBusinessException);
        assertEquals("errors.noAvailableInstallmentsToBePaid", loanApiBusinessException.getCode());
    }

    @Test
    void shouldNotPayCustomerLoan_NotEnoughAmountToPay() {
        BigDecimal amount = new BigDecimal("10.00");
        CustomerLoanPayRequest customerLoanPayRequest = new CustomerLoanPayRequest();
        customerLoanPayRequest.setAmount(amount);
        when(customerLoanJpaRepository.findByCustomerIdAndCustomerLoanId(CUSTOMER_ID, CUSTOMER_LOAN_ID)).thenReturn(Optional.of(customerLoanEntity));

        LoanApiBusinessException loanApiBusinessException = assertThrows(LoanApiBusinessException.class, () -> customerLoanService.payCustomerLoan(CUSTOMER_ID, CUSTOMER_LOAN_ID, customerLoanPayRequest));
        assertNotNull(loanApiBusinessException);
        assertEquals("errors.insufficientAmountToPay", loanApiBusinessException.getCode());
    }

}
