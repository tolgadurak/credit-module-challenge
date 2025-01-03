package io.github.tolgadurak.creditmodulechallenge.loanapi.rest;

import io.github.tolgadurak.creditmodulechallenge.loanapi.facade.CustomerLoanFacade;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.headers.LoanApiHeaders;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanCreateRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanFilterRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanPayRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanCreateRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanInstallmentQueryRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanPayResultRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanQueryRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.PagedRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer-loans")
@RequiredArgsConstructor
@Tag(name = "Customer Loan Controller", description = "Endpoints for customer loan operations")
public class CustomerLoanController {

    private final CustomerLoanFacade customerLoanFacade;

    @PostMapping
    @Operation(summary = "Create Loan For Customer", description = "Used to create new customer loans.")
    public ResponseEntity<CustomerLoanCreateRestResponse> createLoanForCustomer(@RequestHeader(LoanApiHeaders.CUSTOMER_ID) String customerId,
                                                                                @RequestBody @Valid CustomerLoanCreateRestRequest customerLoanCreateRestRequest) {
        CustomerLoanCreateRestResponse restResponse = customerLoanFacade.createCustomerLoan(customerId, customerLoanCreateRestRequest);
        return new ResponseEntity<>(restResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "List Customer Loans", description = "Used to list customer loans.")
    public ResponseEntity<PagedRestResponse<CustomerLoanQueryRestResponse>> listCustomerLoans(@RequestHeader(LoanApiHeaders.CUSTOMER_ID) String customerId,
                                                                                              CustomerLoanFilterRestRequest customerLoanFilterRestRequest) {
        PagedRestResponse<CustomerLoanQueryRestResponse> restResponse = customerLoanFacade.queryCustomerLoan(customerId, customerLoanFilterRestRequest);
        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/installments")
    @Operation(summary = "List Customer Loan Installments", description = "Used to list installments of a customer loan.")
    public ResponseEntity<List<CustomerLoanInstallmentQueryRestResponse>> listCustomerLoanInstallments(@RequestHeader(LoanApiHeaders.CUSTOMER_ID) String customerId,
                                                                                           @RequestHeader(LoanApiHeaders.CUSTOMER_LOAN_ID) String customerLoanId) {
        List<CustomerLoanInstallmentQueryRestResponse> restResponse = customerLoanFacade.queryCustomerLoanInstallments(customerId, customerLoanId);
        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/pay")
    @Operation(summary = "Pay Customer Loan", description = "Used to pay a customer loan.")
    public ResponseEntity<CustomerLoanPayResultRestResponse> payCustomerLoan(@RequestHeader(LoanApiHeaders.CUSTOMER_ID) String customerId,
                                                                     @RequestHeader(LoanApiHeaders.CUSTOMER_LOAN_ID) String customerLoanId,
                                                                     @RequestBody CustomerLoanPayRestRequest customerLoanPayRestRequest) {
        CustomerLoanPayResultRestResponse restResponse = customerLoanFacade.payCustomerLoan(customerId, customerLoanId, customerLoanPayRestRequest);
        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }

}
