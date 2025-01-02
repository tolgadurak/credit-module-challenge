package io.github.tolgadurak.creditmodulechallenge.loanapi.facade;

import io.github.tolgadurak.creditmodulechallenge.loanapi.facade.mapper.CustomerLoanFacadeMapper;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.CustomerLoanCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.CustomerLoanPayRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.CustomerLoanPayResult;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanCreateRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanPayRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanPayResultRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.service.CustomerLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerLoanFacade {

    private final CustomerLoanFacadeMapper customerLoanFacadeMapper;
    private final CustomerLoanService customerLoanService;

    public void createCustomerLoan(String customerId, CustomerLoanCreateRestRequest customerLoanCreateRestRequest) {
        CustomerLoanCreateRequest customerLoanCreateRequest = customerLoanFacadeMapper.toModel(customerLoanCreateRestRequest);
        customerLoanService.createCustomerLoan(customerId, customerLoanCreateRequest);
    }

    public CustomerLoanPayResultRestResponse payCustomerLoan(String customerId, String loanId, CustomerLoanPayRestRequest customerLoanPayRestRequest) {
        CustomerLoanPayRequest customerLoanPayRequest = customerLoanFacadeMapper.toModel(customerLoanPayRestRequest);
        CustomerLoanPayResult customerLoanPayResult = customerLoanService.payCustomerLoan(customerId, loanId, customerLoanPayRequest);
        return customerLoanFacadeMapper.toRestResponse(customerLoanPayResult);
    }

}
