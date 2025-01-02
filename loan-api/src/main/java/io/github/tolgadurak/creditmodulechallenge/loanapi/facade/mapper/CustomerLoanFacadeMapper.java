package io.github.tolgadurak.creditmodulechallenge.loanapi.facade.mapper;

import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanFilterRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.request.CustomerLoanPayRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanInstallmentQueryResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanPayResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.CustomerLoanQueryResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.response.PagedResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanCreateRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanFilterRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanPayRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanInstallmentQueryRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanPayResultRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanQueryRestResponse;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.PagedRestResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerLoanFacadeMapper {

    CustomerLoanCreateRequest toModel(CustomerLoanCreateRestRequest customerLoanCreateRestRequest);

    CustomerLoanFilterRequest toModel(CustomerLoanFilterRestRequest customerLoanFilterRestRequest);

    CustomerLoanPayRequest toModel(CustomerLoanPayRestRequest customerLoanPayRestRequest);

    CustomerLoanInstallmentQueryRestResponse toRestResponse(CustomerLoanInstallmentQueryResponse customerLoanInstallmentQueryResponse);

    CustomerLoanQueryRestResponse toRestResponse(CustomerLoanQueryResponse customerLoanQueryResponse);

    PagedRestResponse<CustomerLoanQueryRestResponse> toRestResponse(PagedResponse<CustomerLoanQueryResponse> pagedResponse);

    CustomerLoanPayResultRestResponse toRestResponse(CustomerLoanPayResponse customerLoanPayResponse);

}
