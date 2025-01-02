package io.github.tolgadurak.creditmodulechallenge.loanapi.facade.mapper;

import io.github.tolgadurak.creditmodulechallenge.loanapi.model.CustomerLoanCreateRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.CustomerLoanPayRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.model.CustomerLoanPayResult;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanCreateRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.request.CustomerLoanPayRestRequest;
import io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response.CustomerLoanPayResultRestResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerLoanFacadeMapper {

    CustomerLoanCreateRequest toModel(CustomerLoanCreateRestRequest customerLoanCreateRestRequest);

    CustomerLoanPayRequest toModel(final CustomerLoanPayRestRequest customerLoanPayRestRequest);

    CustomerLoanPayResultRestResponse toRestResponse(final CustomerLoanPayResult customerLoanPayResult);

}
