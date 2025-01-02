package io.github.tolgadurak.creditmodulechallenge.loanapi.rest;

import io.github.tolgadurak.creditmodulechallenge.loanapi.facade.CustomerLoanFacade;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.headers.LoanApiHeaders;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanCreateRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanFilterRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanPayRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanPayResultRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanQueryRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.PagedRestResponse;
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

@RestController
@RequestMapping("/customer-loans")
@RequiredArgsConstructor
public class CustomerLoanController {

    private final CustomerLoanFacade customerLoanFacade;

    @PostMapping
    public ResponseEntity<Void> createLoanForCustomer(@RequestHeader(LoanApiHeaders.CUSTOMER_ID) String customerId,
                                                      @RequestBody @Valid CustomerLoanCreateRestRequest customerLoanCreateRestRequest) {
        customerLoanFacade.createCustomerLoan(customerId, customerLoanCreateRestRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PagedRestResponse<CustomerLoanQueryRestResponse>> listCustomerLoans(@RequestHeader(LoanApiHeaders.CUSTOMER_ID) String customerId,
                                                                                              CustomerLoanFilterRestRequest customerLoanFilterRestRequest) {
        PagedRestResponse<CustomerLoanQueryRestResponse> pagedRestResponse = customerLoanFacade.queryCustomerLoan(customerId, customerLoanFilterRestRequest);
        return new ResponseEntity<>(pagedRestResponse, HttpStatus.OK);
    }

    @GetMapping("/installments")
    public void listInstallments(@RequestHeader(LoanApiHeaders.CUSTOMER_ID) String customerId,
                                 @RequestHeader(LoanApiHeaders.LOAN_ID) String loanId) {

    }

    @PostMapping("/pay")
    public ResponseEntity<CustomerLoanPayResultRestResponse> payLoan(@RequestHeader(LoanApiHeaders.CUSTOMER_ID) String customerId,
                                                                     @RequestHeader(LoanApiHeaders.LOAN_ID) String loanId,
                                                                     @RequestBody CustomerLoanPayRestRequest customerLoanPayRestRequest) {
        CustomerLoanPayResultRestResponse customerLoanPayResultRestResponse = customerLoanFacade.payCustomerLoan(customerId, loanId, customerLoanPayRestRequest);
        return new ResponseEntity<>(customerLoanPayResultRestResponse, HttpStatus.OK);
    }

}
