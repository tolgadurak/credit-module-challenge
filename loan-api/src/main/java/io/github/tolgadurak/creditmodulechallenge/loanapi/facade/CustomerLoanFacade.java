package io.github.tolgadurak.creditmodulechallenge.loanapi.facade;

import io.github.tolgadurak.creditmodulechallenge.loanapi.facade.mapper.CustomerLoanFacadeMapper;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanFilterRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanPayRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanCreateResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanInstallmentQueryResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanPayResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanQueryResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.PagedResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanCreateRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanFilterRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanPayRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanCreateRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanInstallmentQueryRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanPayResultRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanQueryRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.PagedRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.service.CustomerLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerLoanFacade {

    private final CustomerLoanFacadeMapper customerLoanFacadeMapper;
    private final CustomerLoanService customerLoanService;

    public CustomerLoanCreateRestResponse createCustomerLoan(String customerId, CustomerLoanCreateRestRequest customerLoanCreateRestRequest) {
        CustomerLoanCreateRequest customerLoanCreateRequest = customerLoanFacadeMapper.toModel(customerLoanCreateRestRequest);
        CustomerLoanCreateResponse customerLoanCreateResponse = customerLoanService.createCustomerLoan(customerId, customerLoanCreateRequest);
        return customerLoanFacadeMapper.toRestResponse(customerLoanCreateResponse);
    }

    public PagedRestResponse<CustomerLoanQueryRestResponse> queryCustomerLoan(String customerId, CustomerLoanFilterRestRequest customerLoanFilterRestRequest) {
        CustomerLoanFilterRequest customerLoanFilterRequest = customerLoanFacadeMapper.toModel(customerLoanFilterRestRequest);
        PagedResponse<CustomerLoanQueryResponse> pagedResponse = customerLoanService.queryCustomerLoan(customerId, customerLoanFilterRequest);
        return customerLoanFacadeMapper.toRestResponse(pagedResponse);
    }

    public List<CustomerLoanInstallmentQueryRestResponse> queryCustomerLoanInstallments(String customerId, String customerLoanId) {
        List<CustomerLoanInstallmentQueryResponse> listResponse = customerLoanService.queryCustomerLoanInstallments(customerId, customerLoanId);
        return customerLoanFacadeMapper.toCustomerLoanInstallmentQueryRestResponseList(listResponse);
    }

    public CustomerLoanPayResultRestResponse payCustomerLoan(String customerId, String customerLoanId, CustomerLoanPayRestRequest customerLoanPayRestRequest) {
        CustomerLoanPayRequest customerLoanPayRequest = customerLoanFacadeMapper.toModel(customerLoanPayRestRequest);
        CustomerLoanPayResponse customerLoanPayResponse = customerLoanService.payCustomerLoan(customerId, customerLoanId, customerLoanPayRequest);
        return customerLoanFacadeMapper.toRestResponse(customerLoanPayResponse);
    }

}
