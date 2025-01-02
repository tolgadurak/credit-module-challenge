package io.github.tolgadurak.creditmodulechallenge.loanapi.rest;

import io.github.tolgadurak.creditmodulechallenge.loanapi.facade.CustomerLoanFacade;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.headers.LoanApiHeaders;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanCreateRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanPayRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanPayResultRestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
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
    public void listLoans(@RequestHeader(LoanApiHeaders.CUSTOMER_ID) String customerId) {

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
